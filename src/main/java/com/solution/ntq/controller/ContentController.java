package com.solution.ntq.controller;

import com.solution.ntq.model.Content;
import com.solution.ntq.repository.base.IContentRepository;
import com.solution.ntq.service.base.IClazzService;
import com.solution.ntq.service.base.IContentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
/**
 * @author Duc Anh
 */
@RequestMapping("/api/v1")
public class ContentController {
    IContentService iContentService;

    @PutMapping ("/api/v1/class/{class_id}/add-content")
    public void addContentForClass(@PathVariable("class_id") int classId, @RequestBody Content content) {
        iContentService.addContent(content);



    }
}
