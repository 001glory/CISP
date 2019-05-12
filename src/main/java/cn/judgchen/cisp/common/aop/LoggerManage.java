package cn.judgchen.cisp.common.aop;

import java.lang.annotation.*;

/**
 * @Description: 日志注解
 * @author judgchen
 * @version 1.0
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {

	 String description();
}
