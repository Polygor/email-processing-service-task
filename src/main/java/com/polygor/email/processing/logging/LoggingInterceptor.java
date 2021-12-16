package com.polygor.email.processing.logging;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final Log LOG = LogFactory.getLog(LoggingInterceptor.class);


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
    {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Request uri: ").append(request.getRequestURI()).append("\t");
        logMessage.append("Request method: ").append(request.getMethod()).append("\t");
        logMessage.append("Response status: ").append(response.getStatus()).append("\t");
        logMessage.append("Remote address: ").append(request.getRemoteAddr()).append("\t");

        if (e != null)
        {
            LoggingInterceptor.LOG.error(logMessage.toString(), e);
        }
        else
        {
            LoggingInterceptor.LOG.info(logMessage.toString());
        }
    }

}
