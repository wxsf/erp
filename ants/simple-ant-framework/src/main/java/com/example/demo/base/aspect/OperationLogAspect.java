package com.example.demo.base.aspect;

import com.example.demo.base.annotation.OperationLogAnnotation;
import com.example.demo.base.model.OperationLog;
import com.example.demo.base.model.SecUser;
import com.example.demo.base.service.SystemOperationLog;
import com.example.demo.base.utils.SimpleErpSessionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ： xu gaoxiang
 */
@Aspect
@Component
@Order(-5)
public class OperationLogAspect {

    final SystemOperationLog systemOperationLog;

    public OperationLogAspect(SystemOperationLog systemOperationLog) {
        this.systemOperationLog = systemOperationLog;
    }

    @Pointcut("@annotation(com.example.demo.base.annotation.OperationLogAnnotation)")
    public void serviceAspect(){}

    @AfterReturning("serviceAspect()")
    public void recordLog(JoinPoint joinPoint) throws Throwable {
        OperationLog operationLog =getOperationLogMessage(joinPoint);
    }


    private OperationLog getOperationLogMessage(JoinPoint joinPoint) throws ClassNotFoundException {

        OperationLog operationLog = new OperationLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SecUser userInfo = SimpleErpSessionUtil.getUserInfo();
        String userName = userInfo.getUserName();
        String decp = getServiceMethodDescription(joinPoint);
        operationLog.setOperateType(decp);
        return operationLog;
    }

    public String getServiceMethodDescription(JoinPoint joinpoint) throws ClassNotFoundException {
        //获取连接点目标类名
        String className =joinpoint.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = joinpoint.getSignature().getName() ;
        //获取连接点参数
        Object[] args = joinpoint.getArgs() ;
        //根据连接点类的名字获取指定类
        Class<?> targetClass = joinpoint.getTarget().getClass();
        //拿到类里面的方法
        Method[] methods = targetClass.getMethods() ;

        String description = "" ;
        //遍历方法名，找到被调用的方法名
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class<?>[] clazzs = method.getParameterTypes() ;
                if (clazzs.length==args.length){
                    //获取注解的说明
                    description = method.getAnnotation(OperationLogAnnotation. class).action();
                    break;
                }
            }
        }
        return description ;
    }
}
