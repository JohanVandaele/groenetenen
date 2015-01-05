package be.vdab.restservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.config.EnableEntityLinks;

import be.vdab.commons.Common;

@EnableEntityLinks
@Configuration
@ComponentScan(basePackageClasses = CreateRestControllerBeans.class)
public class CreateRestControllerBeans
{
	// Je maakt een method waarin Spring de EntityLinks en de Common bean injecteert
	@Autowired
	public void fillCommonWithEntityLinks(EntityLinks entityLinks, Common common)
	{
		// Je vult de Common bean met een reference naar de EntityLinks bean.
		common.setEntityLinks(entityLinks);
	}
}
