package be.vdab.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableSpringDataWebSupport	// 29.6.2 Create converter classes
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = CreateControllerBeans.class)
public class CreateControllerBeans extends WebMvcConfigurerAdapter
{
	@Bean
	InternalResourceViewResolver viewResolver()
	{
		InternalResourceViewResolver resolver=new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/JSP/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
	registry.addResourceHandler("/images/**").addResourceLocations("/images/");
	registry.addResourceHandler("/styles/**").addResourceLocations("/styles/");
	registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/info").setViewName("info");
		// Spring MVC geeft een request naar /login direct door aan login.jsp,
		// zonder tussenkomst van een Controller.
		registry.addViewController("/login").setViewName("login");
	}
	
	// 18. MEERTALIGE WEBSITES
	@Bean
	MessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource messageSource	= new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:teksten");
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}
	
	@Bean
	LocaleResolver localeResolver()
	{
		// 1. Geen "LocaleResolver" : taal bepaald door de browser
		//return new FixedLocaleResolver(new Locale("en","US"));	// 2. Taalkeuze ongeacht de browserinstelling
		//return new SessionLocaleResolver();	// 3. Taal en Land van de gebruiker
	
		// 5. Cookies
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieMaxAge(604800); // 604800=7 dagen
		return resolver;
	}

	// 4. Taal bepalen via JSP met locale parameter
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	@Bean
	LocalValidatorFactoryBean validatorFactory()
	{
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setValidationMessageSource(messageSource());
		return factory;
	}
	
	@Override
	// importeer Validator uit org.springframework.validation
	public Validator getValidator()
	{
		return new SpringValidatorAdapter(validatorFactory().getValidator());
	}
}
