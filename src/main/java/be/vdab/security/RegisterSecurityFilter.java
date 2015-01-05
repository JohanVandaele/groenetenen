package be.vdab.security;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;

// Je erft van de class AbstractSecurityWebApplicationInitializer. Deze bevat de
// nodige code om de Spring security filter te associëren met alle browser requests.
public class RegisterSecurityFilter extends AbstractSecurityWebApplicationInitializer
{
	@Override
	// Spring security voert deze method uit voor hij zijn filter met browser requests associeert.
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext)
	{
		// Je laat de HiddenHttpMethodFilter de browser request verwerken voor Spring security
		// die request verwerkt. Dit is belangrijk: HiddenHttpMethodFilter maakt van sommige
		// POST requests een UPDATE of DELETE request. Je hebt in de class CreateSecurityFilter
		// autorisatie gekoppeld aan requests van deze types UPDATE of DELETE.
		insertFilters(servletContext, new HiddenHttpMethodFilter());
	}
}
