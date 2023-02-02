package shop.cazait.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

@Component
public class SwaggerUrlConfig implements WebMvcOpenApiTransformationFilter {


    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {

        OpenAPI openApi = context.getSpecification();

        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("https://cazait.shop");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8082");

        openApi.setServers(Arrays.asList(devServer, localServer ));
        return openApi;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return delimiter.equals(DocumentationType.OAS_30);
    }
}
