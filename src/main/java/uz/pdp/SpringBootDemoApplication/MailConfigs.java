package uz.pdp.SpringBootDemoApplication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "mailing")
public class MailConfigs {
//    @Value("${mailing.server}")
    private String server;
//    @Value("${mailing.user}")
    private String user;
//    @Value("${mailing.password}")
    private String password;
//    @Value("${mailing.enabletls}")
    private Boolean enabletls;

    @Override
    public String toString() {
        return "MailConfigs{" +
                "server='" + server + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", enabletls=" + enabletls +
                '}';
    }
}
