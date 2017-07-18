package com.application.config.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.application.interceptor.ControllerAndActionNameInterceptor;


@Configuration
public class AdapterConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ControllerAndActionNameInterceptor());
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
//    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
      registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
//      registry.addResourceHandler("/resources/icons/**").addResourceLocations("classpath:/resources/icons");
//      registry.addResourceHandler("/resources/css/**").addResourceLocations("classpath:/resources/css");
      
    }
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
