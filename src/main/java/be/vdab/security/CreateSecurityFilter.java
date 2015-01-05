package be.vdab.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
// @EnableWebMvcSecurity integreert Spring security en Spring MVC
@EnableWebMvcSecurity
// Je erft van de class WebSecurityConfigurerAdapter. Deze maakt de servlet filter
// van Spring security en maakt ook Spring beans die horen bij Spring security.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CreateSecurityFilter extends WebSecurityConfigurerAdapter
{
	private static final String MANAGER = "manager";
	private static final String HELPDESKMEDEWERKER = "helpdeskmedewerker";
	private static final String MAGAZIJNIER = "magazijnier";

	private static final String USERS_BY_USERNAME =
			"select naam as username, paswoord as password, actief as enabled" +
			" from gebruikers where naam = ?";
	private static final String AUTHORITIES_BY_USERNAME =
			"select gebruikers.naam as username, rollen.naam as authorities" +
			" from gebruikers inner join gebruikersrollen" +
			" on gebruikers.id = gebruikersrollen.gebruikerid" +
			" inner join rollen" +
			" on rollen.id = gebruikersrollen.rolid" +
			" where gebruikers.naam= ?";
	
	@Autowired
	private DataSource dataSource;

	// Je overridet de method die de authenticatie van Spring security configureert.
	// Je injecteert de AuthenticationManagerBuilder bean die Spring security standaard
	// maakt. Je configureert binnen de method de authenticatie met deze bean.
	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
//		// Je houdt de informatie over de principals bij in het interne geheugen.
//		// Je leest ze verder in de cursus uit een database.
//		auth.inMemoryAuthentication()
//				// Je maakt een principal met de naam joe, het paswoord theboss en de authority manager.
//				.withUser("joe").password("theboss").authorities(MANAGER)
//				.and()
//				.withUser("averell").password("hungry").authorities(HELPDESKMEDEWERKER, MAGAZIJNIER);
		
		// Je geeft met de method jdbcAuthentication() aan dat de informatie over principals
		// en authorization zich in een database bevindt. Je geeft met de method dataSource
		// de DataSource bean aan die connecties maakt naar deze database.
		auth.jdbcAuthentication().dataSource(dataSource)	//;
				.usersByUsernameQuery(USERS_BY_USERNAME)
				.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME)	//;
				// Je definieert met de method passwordEncoder het encryptie algoritme.
				.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	// Je overridet de method die de web aspecten van Spring security configureert.
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		// Je geeft aan Spring security geen beveiliging moet doen op URL’s die passen bij het patroon
		// /images/**. ** betekent dat het patroon ook subfolders van /images bevat.
		web.ignoring()
				.antMatchers("/images/**")
				.antMatchers("/styles/**")
				.antMatchers("/scripts/**");
	}
	
	// Je overridet de method die de HTTP beveiliging van Spring security configureert.
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// De gebruiker zal zich authenticeren door zijn gebruikersnaam en paswoord te tikken in een HTML form.
		http	
				// CSRF (Cross-site request forgery) : Hacker techniek.
				//.csrf().disable()
				.formLogin() 
				.loginPage("/login")
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.and()
				.authorizeRequests()
				// Je definieert autorisatie: enkel ingelogde gebruikers met de authority manager
				// kunnen de URL /filialen/toevoegen en URL’s die passen bij het patroon
				// /filialen/*/wijzigen aanspreken.
				.antMatchers("/filialen/toevoegen", "/filialen/*/wijzigen").hasAuthority(MANAGER)
				// Enkel ingelogde gebruikers met de authority manager
				// kunnen een POST request versturen naar de URL /filialen
				.antMatchers(HttpMethod.POST, "/filialen").hasAuthority(MANAGER)
				.antMatchers(HttpMethod.PUT, "/filialen/*").hasAuthority(MANAGER)
				.antMatchers(HttpMethod.DELETE, "/filialen/*").hasAuthority(MANAGER)
				// Enkel ingelogde gebruikers met de authority magazijnier of helpdeskmedewerker
				// kunnen de URL /filialen/toevoegen aanspreken.
				.antMatchers("/werknemers").hasAnyAuthority(MAGAZIJNIER, HELPDESKMEDEWERKER)	//;
				// Je geeft alle (ook anonieme) gebruikers toegang tot de welkompagina en de loginpagina.
				.antMatchers("/", "/login").permitAll()
				// Voor alle andere URL’s moet de gebruiker minstens ingelogd zijn.
				.antMatchers("/**").authenticated()	//;
				.and()
				// Een eigen 403 (forbidden) pagina
				.exceptionHandling().accessDeniedPage("/WEB-INF/JSP/forbidden.jsp");
	}
}
