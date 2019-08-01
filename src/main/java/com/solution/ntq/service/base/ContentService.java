package com.solution.ntq.service.base;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.entities.Content;

import java.util.List;

/**
 * @author Duc Anh
 */
public interface ContentService {

    boolean addContent(ContentRequest content, String idToken);

    ContentResponse getContentById(int contentId);

    boolean updateContent(ContentRequest content,String tokenId);

    List<Content> getPendingItemByClassId(int classId);

    List<ContentResponse> findContentByClassId(int classId);
    void deleteContentById(int idContent);

}
