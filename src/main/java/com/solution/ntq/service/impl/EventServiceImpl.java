package com.solution.ntq.service.impl;

import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.common.validator.Validator;

import com.solution.ntq.controller.request.EventGroupRequest;
import com.solution.ntq.controller.response.EventResponse;
import com.solution.ntq.common.constant.Status;
import com.solution.ntq.common.utils.GoogleUtils;

import com.solution.ntq.repository.base.EventRepository;
import com.solution.ntq.repository.base.EventMemberRepository;

import com.solution.ntq.repository.entities.Event;
import com.solution.ntq.controller.request.JoinEventRequest;
import com.solution.ntq.repository.base.JoinEventRepository;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.repository.entities.Event;
import com.solution.ntq.repository.entities.JoinEvent;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.controller.request.JoinEventRequest;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;


/**
 * @author Ngoc Ngo Quy
 * @since  at 7/08/2019
 * @version 1.01
 */

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private JoinEventRepository joinEventRepository;
    private ClazzService clazzService;
    private UserRepository userRepository;
    private EventMemberRepository eventMemberRepository;

    @Override
    public EventResponse findByEventId(int eventId,String idToken) throws GeneralSecurityException, IOException {
        Event event= eventRepository.findById(eventId);
        if(event ==null) {
            throw new InvalidRequestException("Not find this event!");
        }
        EventResponse eventResponse= eventMapper(event);
        String userId=GoogleUtils.getUserIdByIdToken(idToken);

        JoinEvent joinEvent=eventMemberRepository.findByUserIdAndEventId(userId,eventId);
        eventResponse.setStatus(getStatus(joinEvent));
        return eventResponse;
    }
    private String getStatus(JoinEvent joinEvent) {
        if(joinEvent==null) {
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
    public void takeAttendanceEvents(List<EventGroupRequest> eventGroupRequests, int eventId, String idToken) {
            String userId = userRepository.findUserByTokenIdToken(idToken).getId();
            int classId = eventRepository.findById(eventId).getId();
            if (!clazzService.isCaptainClazz(userId, classId)) {
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
                        joinEvent.setAttendance(eventGroupRequest.isAttendance());
                        joinEventRepository.save(joinEvent);
                }
        );
    }
}
