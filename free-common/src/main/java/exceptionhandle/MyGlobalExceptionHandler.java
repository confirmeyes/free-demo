package exceptionhandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import utils.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * @author WIN10 .
 * @create 2020-05-15-16:15 .
 * @description .
 */

@ControllerAdvice
public class MyGlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);

    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAuthorizationException(HttpServletRequest request, AccessDeniedException e) {
        if (ServletUtil.isAjaxRequest(request)) {
            return AjaxResult.unauth(e.getMessage());
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error/unauth");
            return modelAndView;
        }
    }


    @ExceptionHandler(value = {ArithmeticException.class, NullPointerException.class})
    public ModelAndView handlerException(Exception exception) {
        System.out.println("global-------exception1");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("exce", exception);
        return mv;
    }

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handlerException2(Exception exception) {
        System.out.println("global-------exception2");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("exce", exception);
        return mv;
    }
}
