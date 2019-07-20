package cn.rongcapital.djob.util;

import java.nio.charset.Charset;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public class HttpUtil {
	private static final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
	
    public static String postJson(String url, String query) {
        String result = null;
        try {
            result = asyncHttpClient.preparePost(url)
                    .addHeader("content-type", "application/json")
                    .setBody(query.getBytes("UTF-8")).execute(new AsyncCompletionHandler<String>() {
                        @Override
                        public String onCompleted(Response response) throws Exception {
                            return response.getResponseBody(Charset.forName("UTF-8"));
                        }

                        @Override
                        public void onThrowable(Throwable t) {
                        }
                    }).get();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(),e);
        }
        return result;
    }
}
