package com.solution.ntq.service.impl;


import com.solution.ntq.common.constant.Level;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.base.*;
import com.solution.ntq.repository.base.ClazzRepository;
import com.solution.ntq.repository.base.ContentRepository;
import com.solution.ntq.repository.base.TokenRepository;
import com.solution.ntq.repository.base.UserRepository;
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
        if (!isValidContentRequest(contentRequest)) {
            throw new InvalidRequestException("Invalid Request !");
        }
            Content content;
            Token token = tokenRepository.findTokenByIdToken(idToken);
            String userId = token.getUser().getId();
            content = ConvertObject.mapper().convertValue(contentRequest, Content.class);
            Clazz clazz = clazzRepository.findClazzById(contentRequest.getClassId());
            content.setClazz(clazz);
            content.setApprove(false);
            content.setTimePost(new Date());
            content.setDone(false);
            content.setAuthorId(userId);
            content.setThumbnail(clazz.getThumbnail());
            content.setAvatar(token.getUser().getPicture());
            contentRepository.save(content);
    }

    @Override
    public void updateContent(ContentRequest contentRequest, String idToken) {
        if (!isValidContentRequest(contentRequest)) {
            throw new InvalidRequestException("Invalid Request !");
        }
            Content content = new Content();
            content.setId(contentRequest.getId());
            Content contentOrigin=contentRepository.findContentById(content.getId());
            Clazz clazz = clazzRepository.findClazzById(contentRequest.getClassId());
            content = ConvertObject.mapper().convertValue(contentRequest, Content.class);
            content.setTimePost(new Date());
            content.setClazz(clazz);
            content.setAuthorId(contentOrigin.getAuthorId());
            content.setThumbnail(clazz.getThumbnail());
            content.setAvatar(contentOrigin.getAvatar());
            contentRepository.save(content);
        }


    @Override
    public List<Content> getPendingItemByClassId(int classId) {
        return contentRepository.findAllByClazzIdAndIsApproveFalse(classId);
    }

    @Override
    public ContentResponse getContentById(int contentId) {

        Content content = contentRepository.findContentById(contentId);
        return getContentResponseMapContent(content);

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
        contentResponse = ConvertObject.mapper().convertValue(content, ContentResponse.class);
        contentResponse.setClazzId(content.getClazz().getId());
        contentResponse.setAuthorName(userRepository.findById(content.getAuthorId()).getName());
        return contentResponse;
    }

    @Override
    public void deleteContentById(int idContent) {
        contentRepository.deleteById(idContent);
    }

    @Override
    public boolean exitContent(int idContent) {
        return contentRepository.existsById(idContent);
    }


    @Override
    public List<ContentResponse> getContentsResponseSorted(int classId) {
        List<Content> contentList;

        contentList = contentRepository.findContentByIdClazz(classId);
            updateStatusContents(contentList);

        return getListContentResponse(contentList);
    }

    private boolean isValidContentRequest(ContentRequest contentRequest) {

        if (contentRequest.getStartDate().before(new Date())) {
            throw new InvalidRequestException("Start date must after today !");
        }
        if (contentRequest.getEndDate().before(contentRequest.getStartDate())) {
            throw new InvalidRequestException("End date must after start date !");
        }

        return !(!contentRequest.getLevel().equalsIgnoreCase(Level.BEGINNER.value()) && !contentRequest.getLevel().equalsIgnoreCase(Level.INTERMEDISE.value())
                && !contentRequest.getLevel().equalsIgnoreCase(Level.EXPERT.value()));
    }
}
