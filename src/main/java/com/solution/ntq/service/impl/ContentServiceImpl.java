package com.solution.ntq.service.impl;


import com.solution.ntq.common.constant.Level;
import com.solution.ntq.common.constant.Status;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.base.ClazzMemberRepository;
import com.solution.ntq.repository.base.ClazzRepository;
import com.solution.ntq.repository.base.ContentRepository;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ContentServiceImpl implements ContentService {
    private ContentRepository contentRepository;
    private ClazzRepository clazzRepository;
    private UserRepository userRepository;
    private ClazzMemberRepository clazzMemberRepository;

    @Override
    public void addContent(ContentRequest contentRequest, String userId) {
        if (!isValidContentRequest(contentRequest)) {
            throw new InvalidRequestException("Invalid Request !");
        }
            Content content;
            content = ConvertObject.mapper().convertValue(contentRequest, Content.class);
            Clazz clazz = clazzRepository.findClazzById(contentRequest.getClazzId());
            content.setClazz(clazz);
            String captainId = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(contentRequest.getClazzId()).getUser().getId();
            if (userRepository.findById(userId).getId().equals(captainId)) {
                content.setApprove(true);
            }
            content.setTimePost(new Date());
            content.setDone(false);
            content.setAuthorId(userId);
            content.setThumbnail(clazz.getThumbnail());
            content.setAvatar(userRepository.findById(userId).getPicture());
            contentRepository.save(content);
    }

    @Override
    public void updateContent(ContentRequest contentRequest, String userId) {
        if (!isValidContentRequest(contentRequest)) {
            throw new InvalidRequestException("Invalid Request !");
        }
            ClazzMember captainOfClazz=clazzMemberRepository.findByClazzIdAndIsCaptainTrue(contentRequest.getClazzId());
            Content content = new Content();
            content.setId(contentRequest.getId());
            Content contentOrigin=contentRepository.findContentById(content.getId());
        if(!captainOfClazz.getUser().getId().equals(userId) && !contentOrigin.getAuthorId().equals(userId)) {
            throw new InvalidRequestException("Don't have permission !");
        }
            Clazz clazz = clazzRepository.findClazzById(contentRequest.getClazzId());
            content = ConvertObject.mapper().convertValue(contentRequest, Content.class);
            content.setTimePost(new Date());
            content.setClazz(clazz);
            content.setApprove(contentOrigin.isApprove());
            content.setAuthorId(contentOrigin.getAuthorId());
            content.setThumbnail(clazz.getThumbnail());
            content.setAvatar(contentOrigin.getAvatar());
            contentRepository.save(content);
        }


    @Override
    public List<Content> getPendingItemByClazzId(int clazzId) {
        return contentRepository.findAllByClazzIdAndIsApproveFalse(clazzId);
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
        return contentList.stream().map(this::getContentResponseMapContent).collect(Collectors.toList());
    }

    private ContentResponse getContentResponseMapContent(Content content) {
        ContentResponse contentResponse;
        contentResponse = ConvertObject.mapper().convertValue(content, ContentResponse.class);
        User author = userRepository.findById(content.getAuthorId());
        contentResponse.setClazzId(content.getClazz().getId());
        contentResponse.setAuthorName(author.getName());
        contentResponse.setAvatar(author.getPicture());
        contentResponse.setThumbnail(content.getClazz().getThumbnail());
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

    /**
     *
     * @param clazzId
     * @param status
     * @return  all content have status of Clazz
     */
    @Override
    public List<ContentResponse> getContentsResponseSorted(int clazzId, String status) {
        List<Content> contentList;
        if (status.equals(Status.APPROVE.value())) {
            contentList = contentRepository.findContentNotApproveByIdClazz(clazzId);
            updateStatusContents(contentList);
            return getListContentResponse(contentList);
        }
        contentList = contentRepository.findContentApproveByIdClazz(clazzId);
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

    /**
     * @
     * @param contentId
     * approve content to Clazz
     */
    @Override
    public void approveContent(int contentId) {
        Content content = contentRepository.findContentById(contentId);
        content.setApprove(true);
        contentRepository.save(content);
    }
}
