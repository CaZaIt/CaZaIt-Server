package shop.cazait.global.config.encrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        boolean check=checkAnnotation(handler, NoAuth.class);
        System.out.println(check);
        if(check) return true;

        String accessToken = request.getHeader("X-ACCESS-TOKEN");
        log.info("accesstoken in interceptor prehandle: "+accessToken);

        if(jwtService.isValidAccessToken(accessToken))
            return true;
        else
            return false;
    }

    private boolean checkAnnotation(Object handler,Class cls){

        HandlerMethod handlerMethod=(HandlerMethod) handler;
        log.info("NoAuth : "+handlerMethod.getMethodAnnotation(cls));
        if(handlerMethod.getMethodAnnotation(cls)!=null){ //해당 어노테이션이 존재하면 true.
            return true;
        }
        return false;
    }
}



