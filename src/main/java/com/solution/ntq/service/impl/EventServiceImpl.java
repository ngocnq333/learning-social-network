package com.solution.ntq.service.impl;

import com.solution.ntq.repository.EventRepository;
import com.solution.ntq.service.base.EventService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


/**
 * @author Ngoc Ngo Quy
 * created at 7/08/2019
 * @version 1.01
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
}
