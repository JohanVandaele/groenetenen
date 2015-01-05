package be.vdab.restclients;

import java.math.BigDecimal;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class YahooKoersenClient implements KoersenClient
{
	private final URI yahooURL;
	private final RestTemplate restTemplate;

	@Autowired
	YahooKoersenClient(@Value("${yahooKoersenURL}") URI yahooURL,RestTemplate restTemplate)
	{
		this.yahooURL = yahooURL;
		this.restTemplate = restTemplate;
	}

	@Override
	public BigDecimal getDollarKoers()
	{
		Query query = restTemplate.getForObject(yahooURL, Query.class);
		return query.results.rate.rate;
	}
}
