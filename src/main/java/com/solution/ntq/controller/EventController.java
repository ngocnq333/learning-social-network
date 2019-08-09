package com.solution.ntq.controller;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.response.EventResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author Ngoc Ngo Quy
 * @since  at 7/08/2019
 * @version 1.01
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private EventService eventService;

    @GetMapping()
    public ResponseEntity<Response<List<EventResponse>>> getContentById(@NotNull @Min(0) @RequestParam(value = "classId", defaultValue = "0") int classId,
                                                                        @RequestParam(value = "startDate", defaultValue = "0") long startDate,
                                                                        @RequestParam(value = "endDate", defaultValue = "0") long endDate) {
        Response<List<EventResponse>> response = new Response<>();
        try {
            List<EventResponse> groupEvent = eventService.getGroupEvent(classId, startDate, endDate);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(groupEvent);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException ex) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
