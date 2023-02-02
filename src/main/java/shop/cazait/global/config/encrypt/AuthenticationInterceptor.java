package shop.cazait.global.config.encrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.error.exception.BaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static shop.cazait.global.error.status.ErrorStatus.INVALID_JWT;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UserException, BaseException {
        boolean check=checkAnnotation(handler, NoAuth.class);
        log.info(String.valueOf(check));
        if(check) return true;

        String accessToken = request.getHeader("X-ACCESS-TOKEN");
        log.info("accesstoken in interceptor prehandle: "+accessToken);

        if(jwtService.isValidAccessToken(accessToken))
            return true;
        else{
            throw new UserException(INVALID_JWT);
        }
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



