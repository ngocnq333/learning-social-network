package com.solution.ntq.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.solution.ntq.common.GoogleUtils;
import com.solution.ntq.service.IFilterService;
import com.solution.ntq.service.IGoogleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@AllArgsConstructor
public class FiterService implements IFilterService {
    private static final String CLIENT_ID = "477273415363-lk76nci51a18rnv1sd9e7pjq8qkajgm8.apps.googleusercontent.com";
    private static HttpTransport httpTransport = new NetHttpTransport();
    private static JsonFactory jacksonFactory = new JacksonFactory();

    private IGoogleService iGoogleService;
    @Autowired
    GoogleUtils googleUtils;

    public boolean filter(HttpServletRequest request) throws GeneralSecurityException, IOException {
        String idTokenString = request.getHeader("id_token");
        /*check time out at there*/
        if (idTokenString == null) {
            return false;
        }
        /* String id_Token = iGoogleService.getIdTokenActive();*/
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jacksonFactory)
                .setAudience(Collections.singletonList("80724656105-fg2ndheoujm7c7dd4ob1i9mq3ebdbjhb.apps.googleusercontent.com"))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Print user identifier
            String userId = payload.getSubject();

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            /* String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");*/

            // Use or store profile information
            return true;
        } else {
            return false;
        }


    }
}
