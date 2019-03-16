package com.eon.client;

import org.apache.log4j.Logger;

import java.util.Map;

public class CognitoClient {
    private static final Logger LOG = Logger.getLogger(CognitoClient.class);
    private static final String GRANT_TYPE = "authorization_code";

    private HttpClientImpl httpClient;

    public CognitoClient() {
        this.httpClient = new HttpClientImpl(System.getenv("COGNITO_URL"));
    }

    public String getToken(Map<String, Object> payload) throws Exception {
        payload.put("grant_type", GRANT_TYPE);
        payload.put("client_id", System.getenv("COGNITO_CLIENT_ID"));
        payload.put("client_secret", System.getenv("COGNITO_CLIENT_SECRET"));
        payload.put("redirect_uri", System.getenv("REDIRECT_URI"));
        LOG.info("-- payload to cognito: " + payload);
        return this.httpClient.sendPost(payload);
    }

}
