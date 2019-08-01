package com.solution.ntq.service.impl;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.validator.Validator;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.ClazzRepository;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.TokenRepository;
import com.solution.ntq.repository.UserRepository;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (Validator.isValidContentRequest(contentRequest)) {
            throw new InvalidRequestException("Invalid Request !");
        } else {
            Content content;
            ObjectMapper mapper = new ObjectMapper();
            Token token = tokenRepository.findTokenByIdToken(idToken);
            String userId = token.getUser().getId();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            content = mapper.convertValue(contentRequest, Content.class);
            Clazz clazz = clazzRepository.findClazzById(contentRequest.getClassId());
            content.setClazz(clazz);
            content.setApprove(false);
            content.setTimePost(new Date());
            content.setDone(false);
            content.setAuthorId(userId);
            content.setThumbnail(clazz.getThumbnail());
            content.setAvatar("https://lh6.googleusercontent.com/-nMY8qLCt46E/AAAAAAAAAAI/AAAAAAAAABI/4YHZ7M15Uks/photo.jpg");
            contentRepository.save(content);
        }
    }

    @Override
    public void updateContent(ContentRequest contentRequest, String idToken) {
        if (Validator.isValidContentRequest(contentRequest)) {
            throw new InvalidRequestException("Invalid Request !");
        } else {
            Content content = new Content();
            ObjectMapper mapper = new ObjectMapper();
            Token token = tokenRepository.findTokenByIdToken(idToken);
            String userId = token.getUser().getId();

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            content.setId(contentRequest.getId());
            Clazz clazz = clazzRepository.findClazzById(contentRequest.getClassId());

            content = mapper.convertValue(contentRequest, Content.class);
            content.setTimePost(new Date());
            content.setClazz(clazz);
            content.setAuthorId(userId);
            content.setThumbnail(clazz.getThumbnail());
            content.setAvatar("https://lh6.googleusercontent.com/-nMY8qLCt46E/AAAAAAAAAAI/AAAAAAAAABI/4YHZ7M15Uks/photo.jpg");
            contentRepository.save(content);
        }

        }


    @Override
    public List<Content> getPendingItemByClassId(int classId) {
        return contentRepository.findAllByClazzIdAndIsApproveFalse(classId);
    }

    /**
     *
     * @param contentId
     * @return
     */
    @Override
    public ContentResponse getContentById(int contentId) {

            Content content = contentRepository.findContentById(contentId);
            return getContentResponseMapContent(content);

    }


    @Override
    public List<ContentResponse> findContentByClassId(int classId) {
        List<Content> listContent = contentRepository.findContentByIdClazz(classId);
        updateStatusContents(listContent);

        return getListContentResponse(listContent);
    }


    private void updateStatusContents(List<Content> contentList) {
        Date currentTime = new Date();
        for (Content content : contentList) {
            if (!content.getEndDate().after(currentTime)) {
                content.setDone(true);
                contentRepository.save(content);
            }
        }
    }

    private List<ContentResponse> getListContentResponse(List<Content> contentList) {
        List<ContentResponse> listContentResponse = new ArrayList<>();
        for (Content content : contentList) {
            listContentResponse.add(getContentResponseMapContent(content));
        }

        return listContentResponse;
    }

    private ContentResponse getContentResponseMapContent(Content content) {
        ContentResponse contentResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        contentResponse = objectMapper.convertValue(content, ContentResponse.class);
        contentResponse.setClazzId(content.getClazz().getId());
        contentResponse.setAuthorName(userRepository.findById(content.getAuthorId()).getName());
        return contentResponse;
    }

    @Override
    public void deleteContentById(int idContent) {
        contentRepository.deleteById(idContent);
    }



}
