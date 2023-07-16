package shop.cazait.global.config.encrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.objenesis.strategy.BaseInstantiatorStrategy;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.error.exception.BaseException;

import javax.persistence.Basic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import static shop.cazait.global.error.status.ErrorStatus.INVALID_JWT;
import static shop.cazait.global.error.status.ErrorStatus.INVALID_REQUEST;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("request.pathinfo = " + request.getPathInfo());
        System.out.println("request = " + request);
        System.out.println("request.getServletPath() = " + request.getServletPath());
        System.out.println("handler = " + handler);
//        if(BasicErrorController.class == handlerMethod.getBeanType()){
//            return true;
//        }
       
        boolean check=checkAnnotation(handler, NoAuth.class);
        log.info(String.valueOf(check));
        if(check) return true;

        String accessToken = request.getHeader("Authorization");
        log.info("AccessToken in interceptor prehandle = "+accessToken);

//        if(jwtService.isValidToken(accessToken)) {
//            if(verifyTokenUserId(request)){
//                return true;
//            }else{
//                return false;
//            }
//        }
//        else {
//            return false;
//        }
        if(jwtService.isValidToken(accessToken)){
            return true;
        }else{
            return false;
        }
    }


    private boolean checkAnnotation(Object handler,Class cls){
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        System.out.println("handlerMethod.getBean() = " + handlerMethod.getBean());
        System.out.println("handlerMethod = " + handlerMethod);
        System.out.println("handlerMethod.getMethodAnnotation(cls) = " + handlerMethod.getMethodAnnotation(cls));
        if(handlerMethod.getMethodAnnotation(cls)!=null){ //해당 어노테이션이 존재하면 true.
            return true;
        }
        return false;
    }

    /**유효한 토큰을 발급 받았지만 토큰 userid, masterid와 다른 userid, masterid에 접근하는 것을 방지**/
    public boolean verifyTokenUserId(HttpServletRequest request) throws UserException {
        //헤더 토큰에 있는 userid 추출
        String jwtFromHeader = jwtService.getJwtFromHeader();
        UUID userIdxInToken = jwtService.getUserIdx(jwtFromHeader);

        //pathvariable인 userid추출
        final Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Optional<String> masterIdInPathVariable = Optional.ofNullable(pathVariables.get("masterId"));
        Optional<String>  userIdInPathVariable= Optional.ofNullable(pathVariables.get("userId"));

        //1. userid 존재시
        if(userIdInPathVariable.isPresent()){
            String stringUserId = userIdInPathVariable.get();
            UUID userId = UUID.fromString(stringUserId);
            if(userId.equals(userIdxInToken)){
                return true;
            }else {
                throw new UserException(INVALID_REQUEST);
            }
        } else if (masterIdInPathVariable.isPresent()) {
            String stringMasterId = masterIdInPathVariable.get().toString();
            UUID masterId = UUID.fromString(stringMasterId);
            if(masterId.equals(userIdxInToken)){
                return true;
            }else{
                throw new UserException(INVALID_REQUEST);
            }
        }
        return true;
    }
}



