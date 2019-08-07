package com.solution.ntq.service.impl;

import com.solution.ntq.repository.EventRepository;
import com.solution.ntq.service.base.EventService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * @author Ngoc Ngo Quy
 * created at 7/08/2019
 * @version 1.01
 */

@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
}
