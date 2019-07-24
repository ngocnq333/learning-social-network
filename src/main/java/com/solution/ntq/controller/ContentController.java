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


@RestController
@AllArgsConstructor
/**
 * @author Duc Anh
 */
@RequestMapping("/api/v1/contents")
public class ContentController {
    private ContentService contentService;


    @PutMapping
    public ResponseEntity<Response<ContentRequest>> addContentForClass(@RequestBody ContentRequest contentRequest, BindingResult bindingResult) {

        Response<ContentRequest> response = new Response<>();

        if (!ContentValidator.isValidContentRequest(bindingResult, contentRequest, response)) {
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Response<ContentRequest>> updateContentForClass(@RequestBody ContentRequest contentRequest, BindingResult bindingResult) {
        Response<ContentRequest> response = new Response<>();

        if (!ContentValidator.isValidContentRequest(bindingResult, contentRequest, response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<Response<ContentResponse>> getContentById(@PathVariable("contentId") int contentId) {
        Response<ContentResponse> response = new Response<>();
        response.setCodeStatus(ResponseCode.OK.value());
        response.setData(contentService.getContentById(contentId));
        return new ResponseEntity<>(response,HttpStatus.OK);


    }

}
