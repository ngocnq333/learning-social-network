package com.solution.ntq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.common.utils.GoogleUtils;
import com.solution.ntq.controller.request.EventRequest;
import com.solution.ntq.repository.base.*;
import com.solution.ntq.repository.entities.*;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.validator.Validator;

import com.solution.ntq.controller.request.EventGroupRequest;
import com.solution.ntq.controller.response.EventResponse;
import com.solution.ntq.common.constant.Status;
import com.solution.ntq.repository.base.EventRepository;
import com.solution.ntq.repository.base.EventMemberRepository;

import com.solution.ntq.repository.entities.Event;
import com.solution.ntq.controller.request.JoinEventRequest;
import com.solution.ntq.repository.base.JoinEventRepository;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.repository.entities.JoinEvent;
import com.solution.ntq.repository.entities.User;
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
    private JoinEventRepository joinEventRepository;
    private static final int MILLISECONDS_OF_DAY = 86400000;
    private static final int ONE_HOUR = 3600000;
    private static final int ONE_MINUTE = 60000;
    private static final long ONE_SECOND = 1000;
    private static final int MIN_DURATION = 25 * ONE_MINUTE;
    private static final int MAX_DURATION = MILLISECONDS_OF_DAY * 2;

    private Event convertRequestToEvent(EventRequest eventRequest, String idToken) throws GeneralSecurityException, IOException, IllegalAccessException {
        validateRequest(eventRequest, idToken);
        User user = userRepository.findUserByTokenIdToken(idToken);
        Clazz clazz = clazzRepository.findClazzById(eventRequest.getClassId());

        ObjectMapper mapper = ConvertObject.mapper();
        Event event = mapper.convertValue(eventRequest, Event.class);
        event.setAuthor(user.getName());
        event.setClazz(clazz);
        event.setContent(getContentByContentId(eventRequest));
        return event;
    }

    private void validateRequest(EventRequest eventRequest, String idToken) throws GeneralSecurityException, IOException, IllegalAccessException {
        String userExecuteId = GoogleUtils.getUserIdByIdToken(idToken);
        Clazz clazz = clazzRepository.findClazzById(eventRequest.getClassId());
        if (clazz == null) {
            throw new InvalidRequestException("ClassId  illegal !");
        }
        checkUserIsCaptain(userExecuteId, clazz.getId());
        if (eventRequest.getStartDate().before(new java.util.Date())) {
            throw new InvalidRequestException("Start Date illegal !");
        }
        if (urlIsInvalid(eventRequest.getDocument())) {
            throw new InvalidRequestException("Document Link Illegal !");
        }
        if (durationEventRequestInvalid(eventRequest)) {
            throw new InvalidRequestException(" Event have duration illegal !");
        }
        if (checkEventRequestTimeInValid(eventRequest)) {
            throw new InvalidRequestException("Have a duplicate event in class !");
        }
    }

    private boolean durationEventRequestInvalid(EventRequest eventRequest) {
        long durationToMillisecond = getTotalMillisecondOfEvent(0, eventRequest.getDuration());
        return ((MIN_DURATION > durationToMillisecond) || (durationToMillisecond > MAX_DURATION));
    }

    private Content getContentByContentId(EventRequest eventRequest) {
        Content content = contentRepository.findContentById(eventRequest.getContentId());
        if (eventRequest.getContentId() > 0 && content == null) {
            throw new InvalidRequestException("ContentId  illegal !");
        }
        return content;
    }

    private boolean checkEventRequestTimeInValid(EventRequest eventRequest) {
        java.sql.Date dateBeforeTwoDay = new java.sql.Date(eventRequest.getStartDate().getTime() - MILLISECONDS_OF_DAY * 2);
        java.sql.Date dateAfterTwoDay = new java.sql.Date(eventRequest.getStartDate().getTime() + MILLISECONDS_OF_DAY * 2);
        List<Event> duplicateEvents = eventRepository.getEventByClazzIdAndStartDate(eventRequest.getClassId(), dateBeforeTwoDay, dateAfterTwoDay);
        if (duplicateEvents.isEmpty()) {
            return false;
        }
        for (Event event : duplicateEvents) {
            long eventMillisecond = event.getStartDate().getTime();
            long requestMillisecond = eventRequest.getStartDate().getTime();
            long currentEvent = getTotalMillisecondOfEvent(eventMillisecond, event.getDuration());
            long requestEvent = getTotalMillisecondOfEvent(requestMillisecond, eventRequest.getDuration());
            //   start date < ( new events start date + duration) > (start date + duration) of all events created
            if ((requestEvent >= event.getStartDate().getTime()) && (requestEvent <= currentEvent)) {
                return true;
            }
        }
        return false;
    }

    private long getTotalMillisecondOfEvent(long startDate, float duration) {
        long durationToMillisecond = 0;
        if (duration != 0) {
            durationToMillisecond = (long) (duration * ONE_HOUR);
        }
        long total = startDate + durationToMillisecond;
        //rounding 1 second
        return total - (total % ONE_SECOND);
    }

    @Override
    public void addEvent(EventRequest eventRequest, String idToken) throws
            GeneralSecurityException, IOException, IllegalAccessException {
        Event event = convertRequestToEvent(eventRequest, idToken);
        eventRepository.save(event);
    }

    private boolean urlIsInvalid(String url) {
        url = "http://" + url;
        UrlValidator validator = UrlValidator.getInstance();
        return !validator.isValid(url);
    }

    private EventMemberRepository eventMemberRepository;

    public EventResponse findByEventId(int eventId, String idToken) throws
            GeneralSecurityException, IOException {
        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new InvalidRequestException("Not find this event!");
        }
        EventResponse eventResponse = eventMapper(event);
        String userId = GoogleUtils.getUserIdByIdToken(idToken);

        JoinEvent joinEvent = eventMemberRepository.findByUserIdAndEventId(userId, eventId);
        eventResponse.setStatus(getStatus(joinEvent));
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
    public void saveJoinForUser(JoinEventRequest joinEventRequest) {
        try {
            JoinEvent joinEvent = joinEventMapper(joinEventRequest);
            User user = new User();
            user.setId(joinEventRequest.getUserId());
            Event event = new Event();
            event.setId(joinEventRequest.getEventId());
            joinEvent.setUser(user);
            joinEvent.setEvent(event);
            save(joinEvent);
        } catch (InvalidRequestException ex) {
            throw new InvalidRequestException(ex.getMessage());
        }
    }

    private void save(JoinEvent joinEvent) {
        joinEventRepository.save(joinEvent);
    }

    private JoinEvent joinEventMapper(JoinEventRequest joinEventRequest) {
        return ConvertObject.mapper().convertValue(joinEventRequest, JoinEvent.class);
    }

    @Override
    public void deleteEvent(int eventId, String idToken) throws IllegalAccessException {
        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new IllegalAccessException("Event Id illegal");
        }
        User user = userRepository.findUserByTokenIdToken(idToken);
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
            idToken) throws IllegalAccessException {
        String userId = userRepository.findUserByTokenIdToken(idToken).getId();
        int classId = eventRepository.findById(eventId).getId();
        checkUserIsCaptain(userId, classId);
        saveAttendance(eventGroupRequests);
    }

    private void saveAttendance(List<EventGroupRequest> eventGroupRequests) {
        eventGroupRequests.forEach(eventGroupRequest -> {
                    int idAttendanceNew = eventGroupRequest.getId();
                    JoinEvent joinEvent = joinEventRepository.getJoinEventsById(idAttendanceNew);
                    if (joinEvent == null) {
                        throw new InvalidRequestException("user or content invalid ");
                    }
                    joinEvent.setAttendance(eventGroupRequest.isAttendance());
                    joinEventRepository.save(joinEvent);
                }
        );
    }
}
