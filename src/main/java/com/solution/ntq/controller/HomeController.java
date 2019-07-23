package com.solution.ntq.controller;

import com.solution.ntq.model.User;
import com.solution.ntq.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class HomeController {
    private IUserService iUserService;
    /**
     * Get an user detail
     */
    @GetMapping(path = "/api/v1/users/")
    public ResponseEntity<User> getUserDetail(HttpServletRequest request) {
        String fakeIdToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg0ZjI5NGM0NTE2MDA4OGQwNzlmZWU2ODEzOGY1MjEzM2QzZTIyOGMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODA3MjQ2NTYxMDUtZmcybmRoZW91am03YzdkZDRvYjFpOW1xM2ViZGJqaGIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4MDcyNDY1NjEwNS1mZzJuZGhlb3VqbTdjN2RkNG9iMWk5bXEzZWJkYmpoYi5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwOTkyNTg2ODQ2NzkzOTk1NzI4MSIsImhkIjoibnRxLXNvbHV0aW9uLmNvbS52biIsImVtYWlsIjoibmFtLnRydW9uZy5pbnRlcm5AbnRxLXNvbHV0aW9uLmNvbS52biIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoibUx6cFVnYzA2em9LNkQyQ3JpRjJldyIsImlhdCI6MTU2Mzg0NjM3MiwiZXhwIjoxNTYzODQ5OTcyfQ.edXP2FlnaTWB806K2eg34vnDrKRnup2NC301KuHyRkdiyePcSCQiNiLfLE77wA55CnFI8842CYIUHVtkWqIyMN1f-v3TwxvE_UhED_JMztd-zIOtqgLTnH7uSqwpOi2AIpAO-pv6mm1zO4ALLkzWnRRFcdmrSyfufCt9DSdgNyRkg0rGpiv41rjBU6g7EJHHyoDBYy1JqPhLkcZPShuqf1acTQm-HjEmcHpf2vjoQEf4GIRtCQ0iH5wZ2cLQXiPPcPmju1ijCaAVnc-n6sNiI1TZbzhZ4o2Wz-r6q04NHKlwGwdzC79tHZ42SgFbgvlUAlG5tMJPADhPHOqrgYabvg";
        String idToken = request.getHeader("id_token");
        String id = request.getParameter("id");
        if (fakeIdToken.equals(idToken) && id.equals("109925868467939957281")){
            User user = iUserService.getUserById(id);
            return new ResponseEntity<> (user, HttpStatus.OK);
        } else {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }

    }
}
