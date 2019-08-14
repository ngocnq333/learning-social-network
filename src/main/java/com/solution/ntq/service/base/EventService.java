package com.solution.ntq.service.base;


import com.solution.ntq.controller.request.JoinEventRequest;

import com.solution.ntq.controller.response.EventResponse;

import java.util.List;

import com.solution.ntq.controller.response.EventResponse;

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
    List<EventResponse> getGroupEvent(int classId, long startDate, long endDate);
    void saveJoinForUser(JoinEventRequest joinEventRequest);
    EventResponse findByEventId(int id,String idToken) throws GeneralSecurityException, IOException;
}
