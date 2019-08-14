package com.solution.ntq.controller;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.response.AttendanceEventResponse;
import com.solution.ntq.controller.response.EventResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.EventService;
import com.solution.ntq.service.base.JoinEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.solution.ntq.controller.request.JoinEventRequest;



/**
 * @author Ngoc Ngo Quy
 * @version 1.01
 * @since at 7/08/2019
 */
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<Response> getEventDetail(@PathVariable("eventId") int eventId, @RequestHeader("id_token") String idToken) {
        Response<EventResponse> response = new Response<>();
        try {
            EventResponse eventResponse = eventService.findByEventId(eventId, idToken);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(eventResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    private JoinEventService joinEventService;

    @GetMapping()
    public ResponseEntity<Response<List<EventResponse>>> getEventGroup(@NotNull @Min(0) @RequestParam(value = "classId", defaultValue = "0") int classId,
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



    @PutMapping(value = "/eventId/joint")
    public ResponseEntity<Response<String>> addUserJoinEvent(@RequestBody JoinEventRequest joinEventRequest) {
        Response<String> response = new Response<>();
        try {
            eventService.saveJoinForUser(joinEventRequest);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException ex) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{eventId}/attendances")
    public ResponseEntity<Response<List<AttendanceEventResponse>>> getListAttendanceEvent(@PathVariable("eventId") int eventId,
                                                                                          @RequestParam(value = "classId", defaultValue = "") int classId,
                                                                                          @RequestParam(value = "userId", defaultValue = "") String userId) {
        Response<List<AttendanceEventResponse>> response = new Response<>();
        try {
            List<AttendanceEventResponse> attendanceResponseListEvent = joinEventService.getListJointEvent(eventId, classId, userId);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(attendanceResponseListEvent);
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
