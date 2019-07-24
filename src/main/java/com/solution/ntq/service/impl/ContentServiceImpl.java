package com.solution.ntq.service.impl;

import com.solution.ntq.model.Content;
import com.solution.ntq.repository.base.IContentRepository;
import com.solution.ntq.service.base.IContentService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContentServiceImpl implements IContentService {
IContentRepository iContentRepository;

    @Override
    public void addContent(Content content) {
        iContentRepository.save(content);
    }
}
