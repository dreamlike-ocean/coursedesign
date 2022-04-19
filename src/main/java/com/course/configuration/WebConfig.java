package com.course.configuration;

import com.course.pojo.LoginUser;
import com.course.utils.AuthenticationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final ThreadLocal<LoginUser> USER_CONTEXT = new ThreadLocal<>();
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/**");

    }

    private static class UserInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (!(handler instanceof HandlerMethod)) return true;

            Method method = ((HandlerMethod) handler).getMethod();
            if (method.getAnnotation(Skip.class) != null) {
                return true;
            }
            LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.SESSION_KEY);
            if (user == null){
                throw new AuthenticationException();
            }
            USER_CONTEXT.set(user);
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            USER_CONTEXT.remove();
        }
    }


}
