package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.EventRequest;
import com.solution.ntq.controller.request.EventGroupRequest;
import com.solution.ntq.controller.response.EventResponse;
import java.util.List;


/**
 * Declare event service
 *
 * @author Ngoc Ngo Quy
 * @version 1.01
 * @since at 7/08/2019
 */

public interface EventService {
    void addEvent(EventRequest event, String userId) ;
    List<EventResponse> getGroupEvent(int clazzId, long startDate, long endDate);
    void joinEvent(String userId,int eventId);
    void notJoinEvent(String userId,int eventId);

    void takeAttendanceEvents(List<EventGroupRequest> eventGroupRequests, int eventId, String userId) throws IllegalAccessException;

    EventResponse findByEventId(int id,String userId) ;
    void deleteEvent(int eventId, String userId) throws IllegalAccessException;

    void updateEvent(String userId, EventRequest eventRequest, int eventId);
}
