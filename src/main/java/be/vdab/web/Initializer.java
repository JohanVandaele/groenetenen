package be.vdab.web;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import be.vdab.aop.CreateAOPBeans;
import be.vdab.commons.CreateCommonBeans;
import be.vdab.dao.CreateDAOBeans;
import be.vdab.datasource.CreateDataSourceBean;
import be.vdab.mail.CreateMailBeans;
import be.vdab.restclients.CreateRestClientBeans;
import be.vdab.restservices.CreateRestControllerBeans;
import be.vdab.security.CreateSecurityFilter;
import be.vdab.services.CreateServiceBeans;

public class Initializer
extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		//return null;
		//return new Class<?>[] {CreateDAOBeans.class, CreateServiceBeans.class, CreateDataSourceBean.class};
		//return new Class<?>[] {CreateDataSourceBean.class, CreateDAOBeans.class, CreateServiceBeans.class, CreateRestClientBeans.class };
		//return new Class<?>[] {CreateDataSourceBean.class, CreateDAOBeans.class, CreateServiceBeans.class,CreateRestClientBeans.class,CreateMailBeans.class };
		//return new Class<?>[] {CreateDataSourceBean.class, CreateDAOBeans.class,CreateServiceBeans.class, CreateRestClientBeans.class, CreateMailBeans.class,CreateCommonBeans.class };
		//return new Class<?>[] {CreateDataSourceBean.class, CreateDAOBeans.class,CreateServiceBeans.class, CreateRestClientBeans.class, CreateMailBeans.class,CreateCommonBeans.class, CreateSecurityFilter.class };
		return new Class<?>[] {CreateDataSourceBean.class, CreateDAOBeans.class,CreateServiceBeans.class, CreateRestClientBeans.class, CreateMailBeans.class,CreateCommonBeans.class, CreateSecurityFilter.class, CreateAOPBeans.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		//return new Class<?>[] { CreateControllerBeans.class };
		return new Class<?>[] 	{ CreateControllerBeans.class, CreateRestControllerBeans.class };
	}
	
	@Override
	public void onStartup(ServletContext context) throws ServletException
	{
		context.setAttribute("contextPath", context.getContextPath());
		super.onStartup(context);
	}
	
	@Override
	protected Filter[] getServletFilters()
	{
		CharacterEncodingFilter utf8Filter = new CharacterEncodingFilter();
		utf8Filter.setEncoding("UTF-8");
		//return new Filter[] { utf8Filter };
		
		// De filter opent een EntityManager zodra een browser request binnenkomt.
		// De filter sluit die EntityManager pas als die browser request helemaal verwerkt is.
		//return new Filter[] {utf8Filter, new OpenEntityManagerInViewFilter()};
		
		// REST: Filter "HiddenHttpMethodFilter"
		//return new Filter[] { utf8Filter, new OpenEntityManagerInViewFilter(),new HiddenHttpMethodFilter()};
		// HiddenHttpMethodFilter() Niet meer nodig bij gebruik security 
		return new Filter[] { utf8Filter, new OpenEntityManagerInViewFilter() };
	}
	
	// 34.8 Een OPTIONS request
	@Override
	protected void customizeRegistration(Dynamic registration)
	{
		// Dynamic uit package javax.servlet.ServletRegistration
		registration.setInitParameter("dispatchOptionsRequest", "true");
	}	
}
