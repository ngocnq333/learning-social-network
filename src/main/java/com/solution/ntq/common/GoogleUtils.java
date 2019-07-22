package com.solution.ntq.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.model.User;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@AllArgsConstructor
public class GoogleUtils {
    private Environment env;

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = env.getProperty("google.link.get.token");
        return  Request.Post(link)
                .bodyForm(Form.form()
                        .add("code", code)
                        .add("client_id", env.getProperty("google.app.id"))
                        .add("client_secret", env.getProperty("google.app.secret"))
                        .add("redirect_uri", env.getProperty("google.redirect.uri"))
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();

    }

    public String getAccessToken(String response) throws ClientProtocolException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public String getRefreshToken(String response) throws ClientProtocolException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("refresh_token");
        return node.textValue();
    }

    /** String getId Access Token form response */

    public String getIdAccessToken(String response) throws ClientProtocolException,IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("id_token");
        return node.textValue();
    }

    public User getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = env.getProperty("google.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(response, User.class);
        return user;
    }


}
