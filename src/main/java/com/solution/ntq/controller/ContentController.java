package com.solution.ntq.controller;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.ContentService;
import com.solution.ntq.service.validator.ContentValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response<ContentRequest>> addContentForClass(@RequestHeader("id_token") String idToken,@RequestBody ContentRequest contentRequest, BindingResult bindingResult) {

        Response<ContentRequest> response = new Response<>();

        if (!ContentValidator.isValidContentRequest(bindingResult, contentRequest, response)) {
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        contentService.addContent(contentRequest,idToken);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Response<ContentRequest>> updateContentForClass(@RequestHeader("id_token") String idToken,@RequestBody ContentRequest contentRequest, BindingResult bindingResult) {

        Response<ContentRequest> response = new Response<>();

        if (!ContentValidator.isValidContentRequest(bindingResult, contentRequest, response)) {
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        contentService.updateContent(contentRequest,idToken);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("/{contentId}")
    public ResponseEntity<Response<ContentResponse>> getContentById(@PathVariable("contentId") int contentId) {
        Response<ContentResponse> response = new Response<>();
        response.setCodeStatus(ResponseCode.OK.value());
        response.setData(contentService.getContentById(contentId));
        return new ResponseEntity<>(response,HttpStatus.OK);


    }
    @GetMapping
    public ResponseEntity<Response> getListContent(@RequestParam("classId") int clazzId){
        Response<List<ContentResponse>> response = new Response<>();
        response.setCodeStatus(HttpStatus.OK.value());
        List<ContentResponse> contentResponseList = contentService.findContentByClassId(clazzId);
        response.setData(contentResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @DeleteMapping("/{contentId}")
    public  ResponseEntity<Response> deleteContentById(@PathVariable("contentId") int contentId) {
        Response<Response> response = new Response<>();
        contentService.deleteContentById(contentId);
        response.setCodeStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
