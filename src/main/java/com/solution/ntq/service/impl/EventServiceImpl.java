package com.solution.ntq.service.impl;

import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.common.validator.Validator;

import com.solution.ntq.controller.response.EventResponse;
import com.solution.ntq.repository.EventRepository;
import com.solution.ntq.repository.entities.Event;
import com.solution.ntq.service.base.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ngoc Ngo Quy
 * @since  at 7/08/2019
 * @version 1.01
 */

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;


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
}
