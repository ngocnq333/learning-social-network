package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.EventRequest;

import com.solution.ntq.controller.request.JoinEventRequest;

import com.solution.ntq.controller.request.EventGroupRequest;
import com.solution.ntq.controller.response.EventResponse;

import java.text.ParseException;
import java.util.List;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Declare event service
 *
 * @author Ngoc Ngo Quy
 * @version 1.01
 * @since at 7/08/2019
 */

public interface EventService {
    void addEvent(EventRequest event, String userId) throws IllegalAccessException, ParseException;
    List<EventResponse> getGroupEvent(int classId, long startDate, long endDate);
    void memberJoinEvent(String userId,int eventId);
    void memberNotJoinEvent(String userId,int eventId);

    void takeAttendanceEvents(List<EventGroupRequest> eventGroupRequests, int eventId, String userId) throws IllegalAccessException;

    EventResponse findByEventId(int id,String userId) ;
    void deleteEvent(int eventId, String userId) throws IllegalAccessException;

    void updateEvent(String userId, EventRequest eventRequest, int eventId);
}
