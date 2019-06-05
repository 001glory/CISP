package cn.judgchen.cisp.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
 
    @Value("${file.path}")
    private String filePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //指向外部目录
        registry.addResourceHandler("/usr/img//**").addResourceLocations("file:"+filePath);
        registry.addResourceHandler("/img//**").addResourceLocations("file:"+filePath);
        super.addResourceHandlers(registry);
    }
}