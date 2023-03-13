package shop.cazait.global.config.encrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.objenesis.strategy.BaseInstantiatorStrategy;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.HandlerMapping;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.error.exception.BaseException;

import javax.persistence.Basic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static shop.cazait.global.error.status.ErrorStatus.INVALID_JWT;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UserException{
        System.out.println("request.pathinfo = " + request.getPathInfo());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
//        if(BasicErrorController.class == handlerMethod.getBeanType()){
//            return true;
//        }
       
        boolean check=checkAnnotation(handler, NoAuth.class);
        log.info(String.valueOf(check));
        if(check) return true;

        String accessToken = request.getHeader("Authorization");
        log.info("AccessToken in interceptor prehandle = "+accessToken);

        if(jwtService.isValidToken(accessToken)){
            return true;
        }
        else {
            return false;
        }
//        final Map<String, String> pathVariables = (Map<String, String>) request
//                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        Long userIdx = Long.valueOf(pathVariables.get("userIdx"));
//
//                if(jwtService.isValidToken(accessToken)){
//            if(jwtService.isValidAccessTokenId(userIdx)) {
//                return true;
//            }
//            return false;
//        }
//        return false;

    }


    private boolean checkAnnotation(Object handler,Class cls){
       
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        System.out.println("handlerMethod.getMethodAnnotation(cls) = " + handlerMethod.getMethodAnnotation(cls));
        if(handlerMethod.getMethodAnnotation(cls)!=null){ //해당 어노테이션이 존재하면 true.
            return true;
        }
        return false;
    }
}



