package com.npde.example;

import com.npde.example.config.CrossDomainFilter;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: hxy
 * @description: SpringBoot启动类
 * @date: 2017/10/24 11:55
 */
@SpringBootApplication
@MapperScan({"com.npde.example.mapper","com.npde.example.model"})
public class MyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MyApplication.class);
		application.setBannerMode(Banner.Mode.CONSOLE);
		application.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(MyApplication.class);
	}


	/**
	 * 支持跨域
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean addCrossDomainFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CrossDomainFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}


	@Bean
	public FilterRegistrationBean addEncodingFilter() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(encodingFilter);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
