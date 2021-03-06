package be.vdab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.services.WerknemerService;

@Controller
@RequestMapping("/werknemers")
class WerknemerController
{
	private static final String WERKNEMERS_VIEW = "werknemers/werknemers";
	
	private final WerknemerService werknemerService;
	
//	@RequestMapping(method = RequestMethod.GET)
//	String findAll()
//	{
//		return WERKNEMERS_VIEW;
//	}

//	// Not pageable
//	@RequestMapping(method = RequestMethod.GET)
//	ModelAndView findAll()
//	{
//		return new ModelAndView(WERKNEMERS_VIEW,"werknemers", werknemerService.findAll());
//	}	

	// Pagable
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView findAll(Pageable pageable)
	{
		return new ModelAndView(WERKNEMERS_VIEW,"page", werknemerService.findAll(pageable));
	}
	
	@Autowired
	WerknemerController(WerknemerService werknemerService)
	{
		this.werknemerService = werknemerService;
	}
}
