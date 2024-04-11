package com.alten.back.api.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    OpenAPI defineOpenApi() {
    	
       Server server = new Server();
       server.setUrl("http://localhost:3000");
       server.setDescription("Development");

       Contact myContact = new Contact();
       myContact.setName("Alten Contact");
       myContact.setEmail("alten@contact.com");

       Info information = new Info()
               .title("Product Management System API")
               .version("1.0")
               .description("This API exposes endpoints to manage products.")
               .contact(myContact);
       return new OpenAPI().info(information).servers(List.of(server));
   }
}
