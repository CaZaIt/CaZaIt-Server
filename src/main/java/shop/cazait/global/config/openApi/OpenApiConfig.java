package shop.cazait.global.config.openApi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("https://cazait.shop");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8082");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(Type.HTTP).scheme("bearer").bearerFormat("JWT");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
                .security(Arrays.asList(securityRequirement))
                .info(getInfo())
                .servers(Arrays.asList(devServer, localServer));

    }

    private Info getInfo() {
        return new Info()
                .title("CaZaIt REST API")
                .description("CaZaIt API DOCS");
    }


}
