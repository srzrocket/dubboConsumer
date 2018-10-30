package com.dubbo.consumer.http;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/dubboAPI")
public class DubboController implements ApplicationContextAware
{
    private final static Logger logger = LoggerFactory.getLogger(DubboController.class);

    @Autowired
    private HttpProviderConf httpProviderConf;

    //缓存作用的map
    private final Map<String, Class<?>> cacheMap = new HashMap<String, Class<?>>();

    protected ApplicationContext applicationContext;
    
    private static Map<String, Object> interfaceMapRef = null;

    public synchronized void getInterfaceMapRef()
    {
        if (null == interfaceMapRef)
        {
            interfaceMapRef = applicationContext.getBeansWithAnnotation(Service.class);
        }
        
    }
    @ResponseBody
    @RequestMapping(value = "/{service}/{method}",method = RequestMethod.POST)
    public String api(HttpRequest httpRequest, HttpServletRequest request,
                      @PathVariable String service,
                      @PathVariable String method) {
        logger.debug("ip:{}-httpRequest:{}",getIP(request), JSON.toJSONString(httpRequest));

        String invoke = invoke(httpRequest, service, method);
        logger.debug("callback :"+invoke) ;
        return invoke ;

    }


    private String invoke(HttpRequest httpRequest,String service,String method){
        httpRequest.setService(service);
        httpRequest.setMethod(method);
        HttpResponse response = new HttpResponse() ;
        logger.debug("input param:"+JSON.toJSONString(httpRequest));
        getInterfaceMapRef();
        try {
            Object bean = interfaceMapRef.get(service);
            if (bean == null){
                logger.error("bean is not correct,bean="+bean);
                response.setCode("1");
                response.setSuccess(false);
                response.setDescription("bean is not correct,bean="+bean);
                return JSON.toJSONString(response) ;
            }
            Method[] methods = bean.getClass().getMethods();
            Method targetMethod = null ;
            for (Method m : methods) {
                if (m.getName().equals(method)){
                    targetMethod = m ;
                    break ;
                }
            }
            if (targetMethod == null){
                logger.error("method is not correct,method="+method);
                response.setCode("2");
                response.setSuccess(false);
                response.setDescription("method is not correct,method="+method);
                return JSON.toJSONString(response) ;
            }
            Object result = null ;
            Class<?>[] parameterTypes = targetMethod.getParameterTypes();
            if (parameterTypes.length == 0){
                //没有参数
                result = targetMethod.invoke(bean);
            }else if (parameterTypes.length == 1){
                Object json = JSON.parseObject(httpRequest.getParam(), parameterTypes[0]);
                result = targetMethod.invoke(bean,json) ;
            }else {
                logger.error("Can only have one parameter");
                response.setSuccess(false);
                response.setCode("2");
                response.setDescription("Can only have one parameter");
            }
            return JSON.toJSONString(result) ;

        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException",e);
            response.setSuccess(false);
            response.setCode("2");
            response.setDescription("InvocationTargetException");
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException",e);
            response.setSuccess(false);
            response.setCode("2");
            response.setDescription("IllegalAccessException");
        }
        return JSON.toJSONString(response) ;
    }

    /**
     * 获取IP
     * @param request
     * @return
     */
    private String getIP(HttpServletRequest request) {
        if (request == null)
            return null;
        String s = request.getHeader("X-Forwarded-For");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {

            s = request.getHeader("Proxy-Client-IP");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {

            s = request.getHeader("WL-Proxy-Client-IP");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
            s = request.getHeader("HTTP_CLIENT_IP");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {

            s = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {

            s = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(s) || "0:0:0:0:0:0:0:1".equals(s))
            try {
                s = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException unknownhostexception) {
                return "";
            }
        return s;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
