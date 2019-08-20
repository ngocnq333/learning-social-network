package com.solution.ntq.service.base;
import com.solution.ntq.controller.request.ContentRequest;
import com.solution.ntq.controller.response.ContentResponse;

import java.util.List;

/**
 * @author Duc Anh
 */
public interface ContentService {

    void addContent(ContentRequest content, String userId);

    ContentResponse getContentById(int contentId);

    void updateContent(ContentRequest content,String userId);

    void deleteContentById(int idContent);

    List<ContentResponse> getContentsResponseSorted(int classId,String title);

    void approveContent(int contentId);
}
