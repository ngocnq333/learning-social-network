package com.solution.ntq.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.ClazzRepository;
import com.solution.ntq.repository.TokenRepository;
import com.solution.ntq.repository.UserRepository;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ContentServiceImpl implements ContentService {
private ContentRepository contentRepository;
private ClazzRepository clazzRepository;
private UserRepository userRepository;
    private TokenRepository tokenRepository;

    @Override
    public void addContent(ContentRequest contentRequest, String idToken) {
        Content content;
        ObjectMapper mapper = new ObjectMapper();
        Token token = tokenRepository.findTokenByIdToken(idToken);
        String userId = token.getUser().getId();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        content=mapper.convertValue(contentRequest,Content.class);
        content.setClazz(clazzRepository.findClazzById(contentRequest.getClassId()));
        content.setApprove(false);
        content.setTimePost(new Date());
        content.setDone(false);
        content.setAuthorId(userId);
        content.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/PHP-logo.svg/1280px-PHP-logo.svg.png");
        contentRepository.save(content);
    }

    @Override
    public ContentResponse getContentById(int contentId) {

        return getContentResponseMapContent(contentRepository.findContentById(contentId));
    }
    private ContentResponse getContentResponseMapContent(Content content) {
        ContentResponse contentResponse ;
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        contentResponse = objectMapper.convertValue(content, ContentResponse.class);
        contentResponse.setClazzId(content.getClazz().getId());
        contentResponse.setAuthorName(userRepository.findById(content.getAuthorId()).getName());
        return contentResponse;
    }

    @Override
    public void updateContent(ContentRequest contentRequest) {
        Content content;
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        content=mapper.convertValue(contentRequest,Content.class);

        content.setClazz(clazzRepository.findClazzById(contentRequest.getClassId()));
        contentRepository.save(content);

    }

    @Override
    public List<Content> getPendingItemByClassId(int classId) {
        return contentRepository.findAllByClazzIdAndIsApproveFalse(classId);
    public ContentResponse getContentById(int contentId) {

        return getContentResponseMapContent(contentRepository.findContentById(contentId));
    }
    private ContentResponse getContentResponseMapContent(Content content) {
        ContentResponse contentResponse ;
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        contentResponse = objectMapper.convertValue(content, ContentResponse.class);
        contentResponse.setClazzId(content.getClazz().getId());
        contentResponse.setAuthorName(userRepository.findById(content.getAuthorId()).getName());
        return contentResponse;
    }


    @Override

    public void updateContent(ContentRequest contentRequest) {
        Content content;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        content=mapper.convertValue(contentRequest,Content.class);
        content.setClazz(clazzRepository.findClazzById(contentRequest.getClassId()));
        contentRepository.save(content);

    }

    @Override
    public List<Content> getPendingItemByClassId(int classId) {
        return contentRepository.findAllByClazzIdAndIsApproveFalse(classId);
    }

}
