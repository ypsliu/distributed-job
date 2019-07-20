import cn.rongcapital.djob.client.JobClient;
import cn.rongcapital.djob.dto.Key;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws IOException {
        JobClient jobClient = new JobClient();
        jobClient.start();

        HttpClient httpclient = HttpClientBuilder.create().build();
        URI server = null;
        try {
            server = new URI("http", null, "172.20.3.146", 8080, "/trigger", null, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(server);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        Key key = new Key("testName2", "testGroup");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cron", "0/5 * * * * ?");
        jsonObject.put("executor", "test.TestExecutor");
        jsonObject.put("key", key);

        StringEntity entity = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_FORM_URLENCODED);

        httpPost.setEntity(entity);
        httpclient.execute(httpPost);
    }
}
