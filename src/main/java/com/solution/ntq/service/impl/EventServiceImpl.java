package com.solution.ntq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.common.constant.Constant;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.controller.request.EventRequest;
import com.solution.ntq.repository.base.*;
import com.solution.ntq.repository.entities.*;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.validator.Validator;
import com.solution.ntq.controller.request.EventGroupRequest;
import com.solution.ntq.controller.response.EventResponse;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.common.constant.Status;
import com.solution.ntq.repository.base.EventRepository;
import com.solution.ntq.repository.base.EventMemberRepository;
import com.solution.ntq.repository.entities.Event;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.controller.request.JoinEventRequest;
import com.solution.ntq.repository.base.JoinEventRepository;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.repository.entities.JoinEvent;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.EventService;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.sql.Date;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ngoc Ngo Quy
 * @version 1.01
 * @since at 7/08/2019
 */

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private ClazzRepository clazzRepository;
    private ClazzMemberRepository clazzMemberRepository;
    private ContentRepository contentRepository;
    private UserRepository userRepository;
    ClazzService clazzService;
    private JoinEventRepository joinEventRepository;
    private static final long MIN_DURATION = 25 * Constant.ONE_MINUTE;
    private static final long MAX_DURATION = Constant.MILLISECONDS_OF_DAY * 2;
    private static final int EVENT_ID_DEFAULT = 0;

    private Event convertRequestToEvent(EventRequest eventRequest, String userId)throws IllegalAccessException {
        validateRequest(eventRequest, userId);
        User user = userRepository.findById(userId);
        Clazz clazz = clazzRepository.findClazzById(eventRequest.getClassId());

        ObjectMapper mapper = ConvertObject.mapper();
        Event event = mapper.convertValue(eventRequest, Event.class);
        event.setAuthor(user.getName());
        event.setClazz(clazz);
        event.setContent(getContentByContentId(eventRequest));
        return event;
    }

    private void validateRequest(EventRequest eventRequest, String userId) throws IllegalAccessException {

        Clazz clazz = clazzRepository.findClazzById(eventRequest.getClassId());
        if (clazz == null) {
            throw new InvalidRequestException("ClassId  illegal !");
        }
        checkUserIsCaptain(userId, clazz.getId());
        if (eventRequest.getStartDate().before(new java.util.Date())) {
            throw new InvalidRequestException("Start Date illegal !");
        }
        if (urlIsInvalid(eventRequest.getDocument())) {
            throw new InvalidRequestException("Document Link Illegal !");
        }
        if (durationEventRequestInvalid(eventRequest)) {
            throw new InvalidRequestException(" Event have duration illegal !");
        }
        if (checkEventRequestTimeInValid(eventRequest, EVENT_ID_DEFAULT)) {
            throw new InvalidRequestException("Have a duplicate event in class !");
        }
    }

    private Content getContentByContentId(EventRequest eventRequest) {
        Content content = contentRepository.findContentById(eventRequest.getContentId());
        if (eventRequest.getContentId() > 0 && content == null) {
            throw new InvalidRequestException("ContentId  illegal !");
        }
        return content;
    }

    private boolean checkEventRequestTimeInValid(EventRequest eventRequest, int eventId) {
        java.sql.Date dateBeforeTwoDay = new java.sql.Date(eventRequest.getStartDate().getTime() - Constant.MILLISECONDS_OF_DAY * 2);
        java.sql.Date dateAfterTwoDay = new java.sql.Date(eventRequest.getStartDate().getTime() + Constant.MILLISECONDS_OF_DAY * 2);
        List<Event> duplicateEvents = eventRepository.getEventByClazzIdAndStartDateNotExistIgnore(eventRequest.getClassId(), dateBeforeTwoDay, dateAfterTwoDay, eventId);
        if (duplicateEvents.isEmpty()) {
            return false;
        }
        for (Event event : duplicateEvents) {
            long startEventMilli = event.getStartDate().getTime();
            long startRequestMilli = eventRequest.getStartDate().getTime();
            long endEventMilli = getTotalMillisecondOfEvent(startEventMilli, event.getDuration());
            long endRequestMilli = getTotalMillisecondOfEvent(startRequestMilli, eventRequest.getDuration());
            // start time of request < start time of old event OR start time  of request > end time of old event
            // end time of request < start time of old event OR end time  of request > end time of old event
            boolean condition1 = (startRequestMilli >= startEventMilli) && (startRequestMilli <= endEventMilli);
            boolean condition2 = (endRequestMilli >= startEventMilli) && (endRequestMilli <= endEventMilli);
            if (condition1 || condition2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addEvent(EventRequest eventRequest, String userId) throws IllegalAccessException {
        Event event = convertRequestToEvent(eventRequest, userId);
        eventRepository.save(event);
    }

    private boolean urlIsInvalid(String url) {
        url = "http://" + url;
        UrlValidator validator = UrlValidator.getInstance();
        return !validator.isValid(url);
    }

    private EventMemberRepository eventMemberRepository;

    public EventResponse findByEventId(int eventId, String userId) {
        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new InvalidRequestException("Not find this event!");
        }
        EventResponse eventResponse = eventMapper(event);
        JoinEvent joinEvent = eventMemberRepository.findByUserIdAndEventId(userId, eventId);
        eventResponse.setStatus(getStatus(joinEvent));
        eventResponse.setId(eventId);
        return eventResponse;
    }

    private String getStatus(JoinEvent joinEvent) {
        if (joinEvent == null) {
            return Status.UNKNOWN.value();
        }
        return joinEvent.isJoined() ? Status.JOINED.value() : Status.NOTJOIN.value();
    }

    @Override
    public List<EventResponse> getGroupEvent(int classId, long startDate, long endDate) {
        if (Validator.isScopeOutOfOneMonth(startDate, endDate)) {
            throw new InvalidRequestException("Invalid data request");
        }
        java.util.Date date = new java.util.Date();
        endDate = startDate == 0 ? date.getTime() : endDate;
        List<Event> groupEvent = eventRepository.getEventByClazzIdAndStartDate(classId, new Date(startDate), new Date(endDate));
        return convertEventToEventResponse(groupEvent);
    }


    private List<EventResponse> convertEventToEventResponse(List<Event> events) {
        List<EventResponse> eventResponses = new ArrayList<>();
        events.forEach(event -> eventResponses.add(eventMapper(event)));
        return eventResponses;
    }

    private EventResponse eventMapper(Event event) {
        return ConvertObject.mapper().convertValue(event, EventResponse.class);
    }

    @Transactional
    @Override
    public void memberJoinEvent(String userId,int eventId) {
        try {
            JoinEvent joinEvent = new JoinEvent();
            joinEvent.setJoined(true);
            setPropertyForJoinEvent( userId, eventId,joinEvent);
            save(joinEvent);
        } catch (InvalidRequestException ex) {
            throw new InvalidRequestException(ex.getMessage());
        }
    }
    @Transactional
    @Override
    public void memberNotJoinEvent(String userId,int eventId) {
        try {
            JoinEvent joinEvent = new JoinEvent();
            joinEvent.setJoined(false);
            setPropertyForJoinEvent( userId, eventId,joinEvent);
            save(joinEvent);
        } catch (InvalidRequestException ex) {
            throw new InvalidRequestException(ex.getMessage());
        }
    }
    private void setPropertyForJoinEvent(String userId,int eventId,JoinEvent joinEvent)
    {

        joinEvent.setUser(userRepository.findById(userId));
        joinEvent.setEvent(eventRepository.findById(eventId));
        joinEvent.setAttendance(false);
    }

    private void save(JoinEvent joinEvent) {
        joinEventRepository.save(joinEvent);
    }

    private JoinEvent joinEventMapper(JoinEventRequest joinEventRequest) {
        return ConvertObject.mapper().convertValue(joinEventRequest, JoinEvent.class);
    }

    @Override
    public void deleteEvent(int eventId, String userId) throws IllegalAccessException {
        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new IllegalAccessException("Event Id illegal");
        }
        User user = userRepository.findById(userId);
        checkUserIsCaptain(user.getId(), event.getClazz().getId());
        eventRepository.deleteById(eventId);
    }

    private void checkUserIsCaptain(String userId, int clazzId) throws IllegalAccessException {
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        if (!userId.equals(clazzMember.getUser().getId())) {
            throw new IllegalAccessException("User not is caption of class not enough permission");
        }
    }

    @Override
    public void takeAttendanceEvents(List<EventGroupRequest> eventGroupRequests, int eventId, String
            userId) throws IllegalAccessException {

        int classId = clazzRepository.findClazzByEventId(eventId).getId();
        if (!clazzMemberRepository.findByClazzIdAndIsCaptainTrue(classId).getUser().getId().equals(userId)) {
            throw new InvalidRequestException("dont have role");
        }
        saveAttendance(eventGroupRequests);

    }

    private void saveAttendance(List<EventGroupRequest> eventGroupRequests) {
        eventGroupRequests.forEach(eventGroupRequest -> {
       int idAttendanceNew = eventGroupRequest.getId();
                    JoinEvent joinEvent = joinEventRepository.getJoinEventsById(idAttendanceNew);
                    if (joinEvent == null) {
                        throw new InvalidRequestException("user or content invalid ");
                    }
                    joinEvent.setConfirm(true);
                    joinEvent.setAttendance(eventGroupRequest.isAttendance());
                    joinEventRepository.save(joinEvent);
                }
        );
    }
    /**
     * Update event
     */
    @Override
    public void updateEvent(String userId, EventRequest eventRequest, int eventId) {
        Event eventOld = eventRepository.findById(eventId);
        User captain = userRepository.findById(userId);

        if (captain == null) {
            throw new InvalidRequestException("Account does not exist!");
        }

        if (eventOld == null) {
            throw new InvalidRequestException("Invalid information!");
        }
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndUserId(eventOld.getClazz().getId(), captain.getId());

        if (clazzMember == null || !clazzMember.isCaptain()) {
            throw new InvalidRequestException("User is not captain!");
        }

        if (durationEventRequestInvalid(eventRequest)) {
            throw new InvalidRequestException(" Event have duration illegal !");
        }

        if (checkEventRequestTimeInValid(eventRequest, eventId)) {
            throw new InvalidRequestException("Have a duplicate event in class !");
        }
        Event event = getEventMapper(eventRequest, eventOld);
        eventRepository.save(event);
    }

    private Event getEventMapper(EventRequest eventRequest, Event eventOld) {
        Event eventNew;
        eventNew = ConvertObject.mapper().convertValue(eventRequest, Event.class);
        eventNew.setId(eventOld.getId());
        eventNew.setClazz(eventOld.getClazz());
        eventNew.setJoinEvents(eventOld.getJoinEvents());
        eventNew.setContent(eventOld.getContent());
        eventNew.setAuthor(eventOld.getAuthor());
        return eventNew;
    }

    private boolean durationEventRequestInvalid(EventRequest eventRequest) {
        long durationToMillisecond = getTotalMillisecondOfEvent(0, eventRequest.getDuration());
        return ((MIN_DURATION > durationToMillisecond) || (durationToMillisecond > MAX_DURATION));
    }


    private long getTotalMillisecondOfEvent(long startDate, float duration) {
        long durationToMillisecond = 0;
        if (duration != 0) {
            durationToMillisecond = (long) (duration * Constant.ONE_HOUR);
        }
        long total = startDate + durationToMillisecond;
        //rounding 1 secondK
        return total - (total % Constant.ONE_SECOND);
    }
}
