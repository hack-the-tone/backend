package com.eon.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.eon.integration.response.ApiGatewayResponse;

import java.util.Map;

public class TimeEntryPostHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> stringObjectMap, Context context) {
        return null;
    }
}
