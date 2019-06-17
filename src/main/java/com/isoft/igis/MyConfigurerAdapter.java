package com.isoft.igis;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.isoft.igis.common.utils.SHPUtils;

@Configuration
public class MyConfigurerAdapter extends WebMvcConfigurerAdapter {


	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new
		// WXSLoginInterceptor()).addPathPatterns("/**");

	}

	/**
	 * 设置跨域放行
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT").maxAge(3600);
		SHPUtils.initGDAL();
	}
}
