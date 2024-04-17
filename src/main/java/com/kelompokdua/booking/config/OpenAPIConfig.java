package com.kelompokdua.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OpenAPIConfig {

    @Value("${kelompok2.openapi.dev-url}")
    private String devUrl;


    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("your-email@example.com");
        contact.setName("Group 2");
        contact.setUrl("https://www.example.com");

        License exampleLicense = new License().name("Example License").url("https://example.com");

        Map<String, Object> extensions = new HashMap<>();
        extensions.put("x-created-by", "Kevin Dwi Rizky, Wahyu Pratama, M Taufiq Firmansyah, Alifia Kireina K, Abdian Ramdhnie");

        Info info = new Info()
                .title("ENIGMA BOOKING MANAGEMENT")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage ENIGMA booking by \n KELOMPOK 2 \n 1.Kevin Dwi Rizky \n 2.Romano Willy Smith \n 3.M Taufiq Firmansyah \n 4.Alifia Kireina K \n 5.Abdian Ramdhnie \n")
                .termsOfService("https://www.example.com/terms")
                .license(exampleLicense)
                .extensions(extensions);

        return new OpenAPI().info(info).addServersItem(devServer);
    }
}
