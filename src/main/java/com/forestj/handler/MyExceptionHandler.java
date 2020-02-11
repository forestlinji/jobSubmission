package com.forestj.handler;

import com.forestj.pojo.ResponseJson;
import com.forestj.pojo.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseJson error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        ResponseJson result = new ResponseJson(ResultCode.ERROR);
        return result;
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseJson AuthorizationError(HttpServletRequest request, HttpServletResponse response, AuthorizationException e) {
        return new ResponseJson(ResultCode.UNAUTHENTICATED);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseJson FileError(MaxUploadSizeExceededException e){
        return new ResponseJson(ResultCode.BIGFILE);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseJson errorMethod(HttpServletRequest request, HttpServletResponse response, Exception e){
        return new ResponseJson(ResultCode.ERRORMETHOD);
    }

}
