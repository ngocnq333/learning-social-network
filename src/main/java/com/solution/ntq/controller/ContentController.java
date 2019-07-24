package com.solution.ntq.controller;

import com.solution.ntq.model.Content;
import com.solution.ntq.repository.IContentRepository;
import com.solution.ntq.service.IClazzService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ContentController {
    IClazzService clazzService;
    IContentRepository iContentRepository;

    @GetMapping(path="/api/v1/class/{class_id}/add-content")
    public void getClassFollowingByUser(@PathVariable("class_id") int classId) {

        Content content= new Content();
        content.setClazz(clazzService.getClassById(classId));
        content.setAuthorId("a");
        iContentRepository.save(content);
    }
}
