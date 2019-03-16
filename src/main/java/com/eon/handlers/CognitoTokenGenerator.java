package com.eon.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.eon.client.CognitoClient;
import com.eon.integration.response.ApiGatewayResponse;
import com.eon.integration.response.Response;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CognitoTokenGenerator implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final Logger LOG = Logger.getLogger(CognitoTokenGenerator.class);
    private CognitoClient cognito = new CognitoClient();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        Response responseBody;
        try {
            responseBody = new Response(cognito.getToken(input));
        } catch (Exception e) {
            LOG.error(e.getStackTrace());
            return ApiGatewayResponse.builder().setStatusCode(400).setObjectBody(new Response("{\"error\":"+e.getMessage()+"\"")).build();
        }

        return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(responseBody).setHeaders(getDefaultHeaders()).build();
    }

    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
