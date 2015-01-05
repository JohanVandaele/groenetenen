package be.vdab.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.services.EuroService;

@Controller
@RequestMapping("euro")
class EuroController
{
	private final EuroService euroService;
	
	@Autowired
	EuroController(EuroService euroService)
	{
		this.euroService = euroService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{euro}/naardollar")
	ModelAndView naarDollar(@PathVariable BigDecimal euro)
	{
		return new ModelAndView("euro/naardollar", "euro", euro).addObject("dollar", euroService.naarDollar(euro));
	}
}
