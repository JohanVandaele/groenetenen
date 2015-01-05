package be.vdab.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class CreateTestDataSourceBean
{
	@Bean
	DataSource dataSource()
	{
		//return new DriverManagerDataSource("jdbc:mysql://localhost/groenetenen","cursist", "cursist");
		
		// 31. TESTEN MET EEN IN-MEMORY DATABASE
		return new DriverManagerDataSource("jdbc:hsqldb:mem:groenetenen","sa", "");
	}
}
