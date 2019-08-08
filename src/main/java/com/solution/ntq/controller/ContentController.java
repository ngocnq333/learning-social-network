package com.solution.ntq.controller;

import com.solution.ntq.common.constant.Constant;
import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
/**
 * @author Duc Anh
 */
@CrossOrigin
@RequestMapping("/api/v1/contents")
public class ContentController {
    private ContentService contentService;



    @PutMapping
    public ResponseEntity<Response<ContentRequest>> addContentForClass(@RequestHeader("id_token") String idToken, @Valid @RequestBody ContentRequest contentRequest) {

        Response<ContentRequest> response = new Response<>();
        try {
            contentService.addContent(contentRequest, idToken);
            response.setData(contentRequest);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setMessage(e.getMessage());
            response.setData(contentRequest);
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {

            response.setData(contentRequest);
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Response<ContentRequest>> updateContentForClass(@RequestHeader("id_token") String idToken, @Valid @RequestBody ContentRequest contentRequest) {

        Response<ContentRequest> response = new Response<>();
        try {
            contentService.updateContent(contentRequest, idToken);
            response.setData(contentRequest);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setData(contentRequest);
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setData(contentRequest);
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<Response<ContentResponse>> getContentById(@PathVariable("contentId") int contentId) {
        Response<ContentResponse> response = new Response<>();
        response.setCodeStatus(ResponseCode.OK.value());
        response.setData(contentService.getContentById(contentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Response> getListContentsSorted(@RequestParam(value = "classId", defaultValue = Constant.CLASS_ID_DEFAULT) int clazzId,
                                                          @RequestParam(value = "sorted", defaultValue = "false") boolean sorted,
                                                          @RequestParam(value = "title", defaultValue = "") String title) {
        try {
            Response<List<ContentResponse>> response = new Response<>();
            List<ContentResponse> contentsSortedGroup = contentService.getContentsResponseSorted(clazzId, sorted, title);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(contentsSortedGroup);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Response> deleteContentById(@PathVariable("contentId") int contentId) {
        Response<Response> response = new Response<>();
        contentService.deleteContentById(contentId);
        response.setCodeStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
