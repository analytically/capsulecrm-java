package uk.co.coen.capsulecrm.client.utils;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;

public class ThrowOnHttpFailure extends AsyncCompletionHandler<Response> {
    @Override
    public Response onCompleted(Response response) throws Exception {
        if (response.getStatusCode() < 200 || response.getStatusCode() > 299)
            throw new IllegalStateException("Response is not OK: " + response.getStatusCode() + " " + response.getStatusText() + " " + response.getResponseBody("UTF-8"));

        return response;
    }
}