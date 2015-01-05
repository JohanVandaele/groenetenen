package be.vdab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
class IndexController
{
	// -------
	// Private
	// -------
	private static final String VIEW = "index";
	private final Voorkeur voorkeur;

	// -----------
	// Constructor
	// -----------
	@Autowired
	IndexController(Voorkeur voorkeur)
	{
		this.voorkeur = voorkeur;
	}

	// ------------------------
	// Methods : RequestMapping
	// ------------------------
//	@RequestMapping(method = RequestMethod.GET)
//	String index()
//	{
//		return VIEW;
//	}

	@RequestMapping(method = RequestMethod.GET)
	ModelAndView index()
	{
		return new ModelAndView(VIEW, "foto", voorkeur.getFoto());
	}

	@RequestMapping(method = RequestMethod.GET, params = "foto")
	String kleurKeuze(String foto)
	{
		voorkeur.setFoto(foto);	// Je onthoudt de gekozen foto in de session scope bean.
		return "redirect:/";
	}	
}
