package com.eon.client;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HttpClientImpl {
    private static final Logger LOG = Logger.getLogger(HttpClientImpl.class);
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private String url;

    public String sendPost(Map<String, Object> params) throws Exception {
        HttpPost httppost = new HttpPost(this.url);
        httppost.setHeader("ContentType", "application/x-www-form-urlencoded");
        httppost.setEntity(new UrlEncodedFormEntity(getBodyFormat(params)));

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = httpclient.execute(httppost);

        final StatusLine statusLine = httpResponse.getStatusLine();
        LOG.info("-- response code" + statusLine.getStatusCode() + " and " + statusLine.getReasonPhrase());

        String responseJSON = EntityUtils.toString(httpResponse.getEntity(), UTF8_CHARSET);
        LOG.info("-- responseJSON" + responseJSON);
        return responseJSON;
    }

    private List<NameValuePair> getBodyFormat(Map<String, Object> params) {
        return params.entrySet().stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), (String) entry.getValue()))
                .collect(Collectors.toList());
    }

}
