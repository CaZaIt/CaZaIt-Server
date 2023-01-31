package shop.cazait.global.config.encrypt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new AuthenticationInterceptor(jwtService));
    }
}

