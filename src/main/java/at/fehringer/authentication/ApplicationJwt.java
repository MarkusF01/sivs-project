package at.fehringer.authentication;

import at.fehringer.authentication.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ApplicationJwt {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationJwt.class, args);
    }

}

