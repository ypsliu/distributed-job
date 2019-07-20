package cn.rongcapital.djob.client.bean;

/**
 * Created by lilupeng on 10/05/2017
 *
 */
public class Property {

    private String redisPassword;
    private String redisHost;
    private String redisPort;
    private String redisTopic;
    private String clientId;

    /**
     *
     */
    public Property() {
    }

    /**
     *
     * @return
     */
    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisTopic() {
        return redisTopic;
    }

    public void setRedisTopic(String redisTopic) {
        this.redisTopic = redisTopic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
