package be.vdab.web;

//import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.services.FiliaalService;
import be.vdab.valueobjects.PostcodeReeks;

@Controller
//@RequestMapping("/filialen")
@RequestMapping(value = "/filialen", produces = MediaType.TEXT_HTML_VALUE)	// REST - Browsers: met HTML-formaat - Andere: FiliaalRestController
class FiliaalController
{
	// -------
	// Private
	// -------
	private static final String FILIALEN_VIEW = "filialen/filialen";
	private static final String TOEVOEGEN_VIEW = "filialen/toevoegen";

	private static final String REDIRECT_URL_NA_TOEVOEGEN = "redirect:/filialen";
//	private static final Logger logger =Logger.getLogger(OfferteController.class.getName()); //???10.8???

	private final FiliaalService filiaalService;

	private static final String FILIAAL_VIEW = "filialen/filiaal";

	// Delete
	private static final String REDIRECT_URL_FILIAAL_NIET_GEVONDEN ="redirect:/filialen";
	private static final String REDIRECT_URL_NA_VERWIJDEREN ="redirect:/filialen/{id}/verwijderd";
	private static final String REDIRECT_URL_HEEFT_NOG_WERKNEMERS ="redirect:/filialen/{id}";
	private static final String VERWIJDERD_VIEW = "filialen/verwijderd";
	
	private static final String PER_POSTCODE_VIEW = "filialen/perpostcode";
	
	private static final String WIJZIGEN_VIEW = "filialen/wijzigen";
	private static final String REDIRECT_URL_NA_WIJZIGEN = "redirect:/filialen";
	
	private static final String AFSCHRIJVEN_VIEW = "filialen/afschrijven";
	private static final String REDIRECT_NA_AFSCHRIJVEN = "redirect:/";
	
	// -----------
	// Constructor
	// -----------
	@Autowired
	FiliaalController(FiliaalService filiaalService)
	{
		// Spring injecteert de parameter filiaalService met de bean die de interface
		// FiliaalService implementeert: FiliaalServiceImpl
		this.filiaalService = filiaalService;
	}

	// -------
	// Methods
	// -------
//	@RequestMapping(method = RequestMethod.GET)
//	String findAll()
//	{
//		return FILIALEN_VIEW;
//	}

	// findAll
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView findAll()
	{
		//return new ModelAndView(FILIALEN_VIEW, "filialen", filiaalService.findAll());
		return new ModelAndView(FILIALEN_VIEW, "filialen"
								,filiaalService.findAll())
								.addObject("aantalFilialen", filiaalService.findAantalFilialen());
	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	ModelAndView findAll()
//	{
//		return new ModelAndView(FILIALEN_VIEW
//								,			"filialen"	,filiaalService.findAll())
//								.addObject(	"werknemers",werknemerService.findAll());
//	}	
	
	// CreateForm
//	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
//	String createForm()
//	{
//		return TOEVOEGEN_VIEW;
//	}

	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
	ModelAndView createForm()
	{
		return new ModelAndView(TOEVOEGEN_VIEW,"filiaal", new Filiaal());	// "filialen/toevoegen"
	}
	
	// Create
//	// importeer Logger uit de package java.util.logging
//	@RequestMapping(method = RequestMethod.POST)
//	String create()
//	{
//		// later voeg je een record toe aan de database
//		logger.info("filiaal record toevoegen aan database");
//		return REDIRECT_URL_NA_TOEVOEGEN;
//	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Filiaal filiaal, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			return TOEVOEGEN_VIEW;			// "filialen/toevoegen"
		}
		
		filiaalService.create(filiaal);
		return REDIRECT_URL_NA_TOEVOEGEN;	// "redirect:/filialen"
	}
	
	// Read
//	// With Id in query-string
//	@RequestMapping(method = RequestMethod.GET, params="id")
//	ModelAndView read(long id)
//	{
//		return new ModelAndView(FILIAAL_VIEW).addObject(filiaalService.read(id));	// RequestAttribuut=filiaal
//	}	
	
	// With Id in clean URL
//	@RequestMapping(value="{id}", method = RequestMethod.GET)
//	ModelAndView read(@PathVariable long id)
//	{
//		return new ModelAndView(FILIAAL_VIEW).addObject(filiaalService.read(id));	// RequestAttribuut=filiaal
//	}

	@RequestMapping(value = "{filiaal}", method = RequestMethod.GET)
	ModelAndView read(@PathVariable Filiaal filiaal)
	{
		return new ModelAndView(FILIAAL_VIEW).addObject(filiaal);
	}
	
	// Delete
//	@RequestMapping(value="{id}/verwijderen", method = RequestMethod.POST)
//	String delete(@PathVariable long id, RedirectAttributes redirectAttributes)
//	{
//		Filiaal filiaal = filiaalService.read(id);
//		
//		if (filiaal == null)
//		{
//			return REDIRECT_URL_FILIAAL_NIET_GEVONDEN;	// "redirect:/filialen"
//		}
//		
//		try
//		{
//			filiaalService.delete(id);
//			redirectAttributes.addAttribute("id", id).addAttribute("naam", filiaal.getNaam());
//			return REDIRECT_URL_NA_VERWIJDEREN;			// "redirect:/filialen/{id}/verwijderd"
//		}
//		catch (FiliaalHeeftNogWerknemersException ex)
//		{
//			redirectAttributes.addAttribute("id", id).addAttribute("fout", "Filiaal heeft nog werknemers");
//			return REDIRECT_URL_HEEFT_NOG_WERKNEMERS;	// "redirect:/filialen/{id}"
//		}
//	}	

	//@RequestMapping(value = "{filiaal}/verwijderen", method = RequestMethod.POST)
	@RequestMapping(value = "{filiaal}", method = RequestMethod.DELETE)	// REST
	String delete(@PathVariable Filiaal filiaal, RedirectAttributes redirectAttributes)
	{
		if (filiaal == null)
		{
			return REDIRECT_URL_FILIAAL_NIET_GEVONDEN;	// "redirect:/filialen"
		}
		
		long id = filiaal.getId();
		
		try
		{
			filiaalService.delete(id);
			redirectAttributes.addAttribute("id", id).addAttribute("naam", filiaal.getNaam());
			return REDIRECT_URL_NA_VERWIJDEREN;			// "redirect:/filialen/{id}/verwijderd"
		}
		catch (FiliaalHeeftNogWerknemersException ex)
		{
			redirectAttributes.addAttribute("id", id).addAttribute("fout", "Filiaal heeft nog werknemers");
			return REDIRECT_URL_HEEFT_NOG_WERKNEMERS;	// "redirect:/filialen/{id}"
		}
	}	
	
	@RequestMapping(value = "{id}/verwijderd", method = RequestMethod.GET)
	ModelAndView deleted(String naam)
	{
		return new ModelAndView(VERWIJDERD_VIEW, "naam", naam);	// "filialen/verwijderd"
	}

	// 19.2 De HTML form tonen aan de gebruiker
	@RequestMapping(value = "perpostcode", method = RequestMethod.GET)
	ModelAndView findByPostcodeReeks()
	{
		PostcodeReeks reeks = new PostcodeReeks();	// Class voor de HTML-Form
		reeks.setVanpostcode(1000);
		reeks.setTotpostcode(9999);	//???19.2???
		return new ModelAndView(PER_POSTCODE_VIEW).addObject(reeks);	// "filialen/perpostcode"
	}

//	@RequestMapping(method=RequestMethod.GET, params={"vanpostcode", "totpostcode"})
//	ModelAndView findByPostcodeReeks(PostcodeReeks reeks)
//	{
//		return new ModelAndView(PER_POSTCODE_VIEW,"filialen", filiaalService.findByPostcodeReeks(reeks))
//				.addObject(reeks);		// "filialen/perpostcode"
//	}

//	@RequestMapping(method=RequestMethod.GET, params={"vanpostcode", "totpostcode"})
//	ModelAndView findByPostcodeReeks(@ModelAttribute PostcodeReeks reeks)
//	{
//		return new ModelAndView(PER_POSTCODE_VIEW,"filialen", filiaalService.findByPostcodeReeks(reeks));	// "filialen/perpostcode"
//	}

//	@RequestMapping(method=RequestMethod.GET, params={"vanpostcode", "totpostcode"})
//	ModelAndView findByPostcodeReeks(PostcodeReeks reeks)
//	{
//		return new ModelAndView(PER_POSTCODE_VIEW,"filialen", filiaalService.findByPostcodeReeks(reeks));	// "filialen/perpostcode"
//	}

	// Deze is overbodig: deze validaties gebeuren nu door bean validation.
//	// 19.7.1 Verplicht in te vullen invoervakken
//	@InitBinder("postcodeReeks")
//	void initBinderPostcodeReeks(DataBinder dataBinder)
//	{
//		dataBinder.setRequiredFields("vanpostcode", "totpostcode");
//	}
	
//	@RequestMapping(method=RequestMethod.GET, params={"vanpostcode","totpostcode"})
//	ModelAndView findByPostcodeReeks(@ModelAttribute PostcodeReeks reeks,BindingResult bindingResult)
//	{
//		ModelAndView modelAndView = new ModelAndView(PER_POSTCODE_VIEW);
//		
//		// Indien geen fouten bij de form-validatie
//		if ( ! bindingResult.hasErrors())
//		{
//			modelAndView.addObject("filialen",filiaalService.findByPostcodeReeks(reeks));
//		}
//		
//		return modelAndView;
//	}
	
	@RequestMapping(method=RequestMethod.GET, params={"vanpostcode","totpostcode"})
//	ModelAndView findByPostcodeReeks(@ModelAttribute PostcodeReeks reeks,BindingResult bindingResult)
	ModelAndView findByPostcodeReeks(@Valid PostcodeReeks reeks,BindingResult bindingResult)
	{
		ModelAndView modelAndView = new ModelAndView(PER_POSTCODE_VIEW);
		
		if (!bindingResult.hasErrors())
		{
			Iterable<Filiaal> filialen = filiaalService.findByPostcodeReeks(reeks);
			
			if (filialen.iterator().hasNext())
			{
				modelAndView.addObject("filialen", filialen);
			}
			else
			{
				// pertotpostcode.jsp : <form:errors element='div' cssClass='fout'/>
				bindingResult.reject("geenFilialen");
			}
		}

		return modelAndView;
	}
	
//	@RequestMapping(value ="{id}/wijzigen", method = RequestMethod.GET)
//	ModelAndView updateForm(@PathVariable long id)
//	{
//		Filiaal filiaal = filiaalService.read(id);
//
//		if (filiaal == null)
//		{
//			return new ModelAndView(REDIRECT_URL_FILIAAL_NIET_GEVONDEN);
//		}
//		
//		return new ModelAndView(WIJZIGEN_VIEW).addObject(filiaal);
//	}
	
	@RequestMapping(value ="{filiaal}/wijzigen", method = RequestMethod.GET)
	ModelAndView updateForm(@PathVariable Filiaal filiaal)
	{
		if (filiaal == null)
		{
			return new ModelAndView(REDIRECT_URL_FILIAAL_NIET_GEVONDEN);
		}
		
		return new ModelAndView(WIJZIGEN_VIEW).addObject(filiaal);
	}

	//@RequestMapping(value = "{id}", method = RequestMethod.POST)
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)	// REST
	String update(@Valid Filiaal filiaal, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			return WIJZIGEN_VIEW;
		}
		
		filiaalService.update(filiaal);
		return REDIRECT_URL_NA_WIJZIGEN;
	}
	
	@RequestMapping(value = "afschrijven", method = RequestMethod.GET)
	ModelAndView afschrijvenForm()
	{
		return new ModelAndView(AFSCHRIJVEN_VIEW, "filialen",filiaalService.findNietAfgeschreven()).addObject(new AfschrijvenForm());
	}
	
	@RequestMapping(value = "afschrijven", method = RequestMethod.POST)
	ModelAndView afschrijven(@Valid AfschrijvenForm afschrijvenForm,BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			return new ModelAndView(AFSCHRIJVEN_VIEW, "filialen",filiaalService.findNietAfgeschreven());
		}
		
		// Enkelvoudige selectie
		//filiaalService.afschrijven(afschrijvenForm.getFiliaal());
		
		// Meervoudige selectie
		filiaalService.afschrijven(afschrijvenForm.getFilialen());
		return new ModelAndView(REDIRECT_NA_AFSCHRIJVEN);
	}
}
