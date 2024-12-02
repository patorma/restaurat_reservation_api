package com.edteam.restaurant_reservation.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;

//http://localhost:8080/api/v1/swagger-ui/index.html
@Configuration
public class OpenAPIConfig {

	//con @Value se accede a los valores de las variables de entorno
	// definidas en application.properties
	
	@Value("${edteam.openapi.dev-url}")
	private String devUrl;
	
	@Value("${edteam.openapi.prod-url}")
	private String prodUrl;
	
	//@Bean para que spring se entere de este objeto
	
	@Bean
	public OpenAPI myOpenAPI() {
		//aca se proyecta lo que se va a mostrar por Swagger
		//Definir el servidor de desarrollo 
    Server devServer = new Server();
    
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development enviroment");
    
    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production enviroment");
    
    //Información de contacto
    Contact contact = new Contact();
    contact.setEmail("pocontreras@ing.ucsc.cl");
    contact.setName("Patricio");
    contact.setUrl("https://github.com/patorma");
    
    //Licencia
    License mitLicense = new License().name("MIT Licesnse").
    		url("https://choosealicense.com/licenses/mit/");
   
    // Información general de la API
    Info info = new Info()
      .title("API de Reservaciones de Restaurante")
      .version("1.0")
      .contact(contact)
      .description("Esta API expone endpoints para gestionar reservaciones de restaurante.")
      .termsOfService("https://www.hampcode.com/terms")
      .license(mitLicense);
    
 // Configuración de seguridad JWT
 // Requerimiento de seguridad para utilizar en las operaciones
    SecurityScheme securityScheme = new SecurityScheme()
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT")
      .name("JWT Authentication");
    
    //se agrega la autorizacion como en postman
    Components components = new Components()
    	      .addSecuritySchemes("bearerAuth", securityScheme);
    // se devuelve los objetos que fueron definidos anteriormente
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
    return new OpenAPI()
    	      .info(info)
    	      .servers(List.of(devServer, prodServer))
    	      .addSecurityItem(securityRequirement)
    	      .components(components);
    
	}
}
