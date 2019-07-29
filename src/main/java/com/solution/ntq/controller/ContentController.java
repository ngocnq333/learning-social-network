package com.solution.ntq.controller;

import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.entities.Content;

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
@CrossOrigin
/**
 * @author Duc Anh
 */
@RequestMapping("/api/v1/contents")
public class ContentController {
    private ContentService contentService;


    @PutMapping
    public ResponseEntity<Response<ContentRequest>> addContentForClass(@RequestHeader("id_token") String idToken ,@RequestBody ContentRequest contentRequest, BindingResult bindingResult) {
        Response<ContentRequest> response = new Response<>();
        new ContentValidator().validate(contentRequest, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            response.setCodeStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        contentService.addContent(contentRequest,idToken);

        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(null);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Response<ContentRequest>> updateContentForClass(@RequestBody ContentRequest contentRequest, BindingResult bindingResult) {
        Response<ContentRequest> response = new Response<>();
        new ContentValidator().validate(contentRequest, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            response.setCodeStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        contentService.updateContent(contentRequest);
        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{content-id}")
    public ResponseEntity<Response<ContentResponse>> getContentById(@PathVariable("content-id") int contentId) {
        Response<ContentResponse> response = new Response<>();
        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(contentService.getContentById(contentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
