package com.course.controller;

import com.course.utils.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;

import static com.course.utils.ResponseEntity.NORMAL_ERROR_CODE;
import static com.course.utils.ResponseEntity.VALIDATION_ERROR_CODE;

@RestControllerAdvice
public class ControllerAdvice implements ResponseBodyAdvice {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handlerException(Throwable throwable, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(false,throwable.getMessage(),NORMAL_ERROR_CODE,null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerValidationException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(false,e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n")),VALIDATION_ERROR_CODE,null);
    }
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 无入侵式的统一转化变量
     * @param body 方法返回值，即需要写入到响应流中
     * @param returnType 对应方法的返回值
     * @param selectedContentType 当前content-type
     * @param selectedConverterType 当前转化器
     * @param request 当前请求
     * @param response 当前响应
     * @return 处理后真正写入响应流的对象
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //若controller返回值为String，其调用的converter是StringConverter，如果还是用这个统一返回则会发生类型转换错误
        if (body instanceof String){
            try {
                return objectMapper.writeValueAsString(new ResponseEntity<>(true,null,null,body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if(body instanceof ResponseEntity){
            return body;
        }
        return new ResponseEntity<>(true,null,null, returnType.getParameterType() == void.class ? "" : body);
    }
}
