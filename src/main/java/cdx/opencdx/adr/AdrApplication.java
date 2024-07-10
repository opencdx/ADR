package cdx.opencdx.adr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan(basePackages = {"cdx.opencdx"})
@SpringBootApplication
public class AdrApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdrApplication.class, args);
    }

}
