package com.solution.ntq.service.base;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;
import com.solution.ntq.repository.entities.Content;

import java.util.List;

/**
 * @author Duc Anh
 */
public interface ContentService {

    void addContent(ContentRequest content, String userId);

    ContentResponse getContentById(int contentId);

    void updateContent(ContentRequest content,String userId);

    List<Content> getPendingItemByClassId(int classId);

    void deleteContentById(int idContent);

    boolean exitContent(int idContent);

    List<ContentResponse> getContentsResponseSorted(int classId,String title);

    void approveContent(int contentId);
}
