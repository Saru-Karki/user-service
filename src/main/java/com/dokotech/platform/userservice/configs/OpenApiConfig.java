package com.dokotech.platform.userservice.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    @Bean
    public OpenAPI userServiceOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development Server");

        Server prodServer = new Server();
        prodServer.setUrl("https://platform.dokotech.com");
        prodServer.setDescription("Production Server");

        Contact contact = new Contact();
        contact.setEmail("support@dokotech.com");
        contact.setName("Doko Tech Support");
        contact.setUrl("https://www.dokotech.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("User Service API")
                .version("1.0.0")
                .contact(contact)
                .description("This API provides endpoints for managing users, roles, and permissions.\n\n" +
                        "**Authentication Options:**\n" +
                        "1. **JWT Login:** Use /api/auth/login to get a JWT token, then use the 'Authorize' button\n" +
                        "2. **OAuth2:** Redirect to /oauth2/authorization/{provider} (google, facebook, apple) for social login\n\n" +
                        "After OAuth2 login, you'll receive a JWT token in the redirect URL which can be used for API authentication.")
                .license(mitLicense);

        // OAuth2 Password Flow Security Scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new io.swagger.v3.oas.models.security.OAuthFlows()
                        .password(new io.swagger.v3.oas.models.security.OAuthFlow()
                                .tokenUrl("/oauth/token")
                                .refreshUrl("/api/auth/refresh")
                        )
                )
                .description("Enter your username and password. Leave client_id and client_secret empty (optional)");

        // Security Requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(SECURITY_SCHEME_NAME);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme));
    }
}

