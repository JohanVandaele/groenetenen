// Deze controller verwerkt niet-browser requests.

package be.vdab.restservices;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.exceptions.FiliaalNietGevondenException;
import be.vdab.services.FiliaalService;

// Je gebruikt @RestController bij een controller die niet-browser requests verwerkt.
@RestController
// De URL is dezelfde als bij FiliaalController, maar zonder produces=MediaType.TEXT_HTML_VALUE.
@RequestMapping("/filialen")
@ExposesResourceFor(Filiaal.class)	// HATEOAS
class FiliaalRestController
{
	private final EntityLinks entityLinks;
	private final FiliaalService filiaalService;

//	@Autowired
//	FiliaalRestController(FiliaalService filiaalService)
//	{
//		this.filiaalService = filiaalService;
//	}

	@Autowired
	FiliaalRestController(FiliaalService filiaalService, EntityLinks entityLinks)
	{
		this.filiaalService = filiaalService;
		this.entityLinks = entityLinks;
	}	
	
//	@RequestMapping(value = "{filiaal}", method = RequestMethod.GET)
//	Filiaal read(@PathVariable Filiaal filiaal)
//	{
//		if (filiaal == null)
//		{
//			throw new FiliaalNietGevondenException();
//		}
//		
//		return filiaal;
//	}
	
	@RequestMapping(value = "{filiaal}", method = RequestMethod.GET)
	FiliaalResource read(@PathVariable Filiaal filiaal)
	{
		if (filiaal == null)
		{
			throw new FiliaalNietGevondenException();
		}
		
		return new FiliaalResource(filiaal, entityLinks);
	}	
	
	// betekent dat de method exceptions afhandelt die je in
	// @RequestMethods werpt. De method handelt enkel exceptions van het type FiliaalNietGevondenException af.
	@ExceptionHandler(FiliaalNietGevondenException.class)
	// bevat de statuscode die je naar de browser wil sturen als een FiliaalNietGevondenException optreedt.
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void filiaalNietGevonden()
	{}

	@RequestMapping(value = "{filiaal}", method = RequestMethod.DELETE)
	// Het returntype van de method is void. Spring maakt dan een response met een lege body
	void delete(@PathVariable Filiaal filiaal)
	{
		if (filiaal == null)
		{
			// De method filiaalNietGevonden vertaalt deze exception naar een response met status code 404 (Not Found).
			throw new FiliaalNietGevondenException();
		}
		
		// Deze method kan een FiliaalHeeftNogWerknemersException werpen.
		filiaalService.delete(filiaal.getId());
	}
	
	// Je vertaalt die exception naar een response met status code 409 (Conflict) en een response body filiaal heeft nog werknemers
	@ExceptionHandler(FiliaalHeeftNogWerknemersException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String filiaalHeeftNogWerknemers()
	{
		return "filiaal heeft nog werknemers";
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	// Spring vertaalt met @RequestBody de request body naar een Filiaal object.
//	// Sprint valideert met @Valid dit Filiaal object ten opzichte van de validation annotations.
//	// Als er validatiefouten zijn, voert Spring de method create niet uit, maar werpt een MethodArgumentNotValidException.
//	void create(@RequestBody @Valid Filiaal filiaal)
//	{
//		// Je voegt het filiaal toe aan de database.
//		filiaalService.create(filiaal);
//	}

	@RequestMapping(method = RequestMethod.POST)
	// Als de method geen exception werpt, vult Spring de response status code met de waarde
	// uit @ResponseStatus. De constante CREATED bevat de waarde 201 (Created)
	@ResponseStatus(HttpStatus.CREATED)
	HttpHeaders create(@RequestBody @Valid Filiaal filiaal)
	{
		System.out.println("POST");
		filiaalService.create(filiaal);
		HttpHeaders headers = new HttpHeaders();
		// Je maakt een voorstelling van het XML element
		// <link rel='self' href='http://localhost:8080/groenetenen/filialen/11'/>
		// Het href attribuut bevat de URL van het toegevoegde filiaal (als de id 11 is)
		Link link =	entityLinks.linkToSingleResource(Filiaal.class, filiaal.getId());
		// Je maakt met het href attribuut een URI. Je vult de response header Location met die URI
		// Set ResponseHeader : Location=http://localhost:8080/gt/filialen/15
		headers.setLocation(URI.create(link.getHref()));
		return headers;
	}	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	// Er zijn validatiefouten opgetreden in de filiaal data.
	// Spring plaatst die fouten in de MethodArgumentNotValidException parameter.
	String filiaalMetVerkeerdeProperties(MethodArgumentNotValidException ex)
	{
		StringBuffer fouten = new StringBuffer();

		// getBindingResult().getFieldErrors() geeft een verzameling FieldError objecten:
		// de Filiaal properties met validatiefouten.
		for (FieldError error : ex.getBindingResult().getFieldErrors())
		{
			fouten.append(error.getField()).append(':').append(error.getDefaultMessage()).append('\n');
		}
		
		fouten.deleteCharAt(fouten.length() - 1);
		return fouten.toString();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	void update(@RequestBody @Valid Filiaal filiaal)
	{
		filiaalService.update(filiaal);
	}
	
	// 34.8 Een OPTIONS request
	// HttpHeaders stelt response headers voor. Als je dit type gebruikt als return waarde van
	// een @RequestMapping method, bevat de response een lege body en deze headers.
	@RequestMapping(value = "{filiaal}", method = RequestMethod.OPTIONS)
	HttpHeaders options(@PathVariable Filiaal filiaal)
	{
		if (filiaal == null)
		{
			// Het filiaal bestaat niet. De method filiaalNietGevonden vertaalt deze
			// FiliaalNietGevondenException naar een response met status code 404 (Not Found).
			throw new FiliaalNietGevondenException();
		}
		
		// Je maakt Set met de verzameling toegelaten HTTP methods.
		Set<HttpMethod> allowedMethods = new HashSet<>();
		
		allowedMethods.add(HttpMethod.GET);
		allowedMethods.add(HttpMethod.PUT);

		// Deze Set bevat ook de HTTP method DELETE als het filiaal geen werknemers heeft.
		if (filiaal.getWerknemers().isEmpty())
		{
			allowedMethods.add(HttpMethod.DELETE);
		}
		
		HttpHeaders headers=new HttpHeaders();
		// Je vult de header Allow in met de toegelaten HTTP methods.
		headers.setAllow(allowedMethods);
		return headers;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	FilialenResource findAll()
	{
		return new FilialenResource(filiaalService.findAll(), entityLinks);
	}
}
