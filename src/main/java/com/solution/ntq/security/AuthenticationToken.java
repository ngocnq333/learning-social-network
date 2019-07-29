package com.solution.ntq.security;/*
package com.solution.ntq.security;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.solution.GoogleLink;
import com.solution.ntq.model.Token;
import com.solution.ntq.repository.base.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;

import static com.solution.GoogleLink.MINUTE_TIMEOUT;
import static java.util.Collections.emptyList;

*/
/**
 * @author  Created by Ngo Quy Ngoc on 23/07/2019.
 *//*


@AllArgsConstructor
public class AuthenticationToken {
    private TokenRepository tokenRepository;
    private static HttpTransport httpTransport = new NetHttpTransport();
    private static JsonFactory jacksonFactory = new JacksonFactory();

    public static Authentication getAuthentication(HttpServletRequest request) {
        String idTokenString = request.getHeader("id_token");
        if (idTokenString != null) {
            // Check request from client have idTokenString
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jacksonFactory)
                    // create library of Google Token to decode idTokenString check request valid
                    .setAudience(Collections.singletonList(GoogleLink.CLIENT_ID))
                    .build();
            try{

                GoogleIdToken idToken = verifier.verify(idTokenString);
                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    // Get payload field JWT
                    String userId = payload.getSubject();
                    // Get profile information from payload
                  if(!checkTimeOut(userId)){

                      return userId != null ?
                              new UsernamePasswordAuthenticationToken(userId, null, emptyList()) :
                              null;
                  }
                }
            }
            catch (GeneralSecurityException | IOException ex){
                System.err.println(ex);
            }
        }
        return null;
    }
    */
/**
     *
     * @return state of userTime on server
     *//*

    private static boolean checkTimeOut(String userId){
        return isTimeOut(userId);
    }

    */
/**
     * @param userId userid
     * @return true if not time out
     *//*


    private boolean isTimeOut(String userId) {
        Token token = tokenRepository.findTokenByUserId(userId);
        Date currentTime = new Date();// get current time
        long diff = currentTime.getTime() - token.getTime().getTime();
        long diffMinutes = diff / (60 * 1000) % 60;// caculate diffirent minute between two time
        return (diffMinutes >= MINUTE_TIMEOUT);
    }
}*/
