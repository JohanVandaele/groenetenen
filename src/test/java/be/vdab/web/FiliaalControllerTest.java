package be.vdab.web;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import be.vdab.entities.Filiaal;
import be.vdab.services.FiliaalService;
import be.vdab.valueobjects.Adres;

public class FiliaalControllerTest
{
	private FiliaalController filiaalController;
	private Iterable<Filiaal> filialen;
	private FiliaalService filiaalService;
	private Filiaal filiaal;
	
	@Before
	public void setUp()
	{
		// Mockito geeft een object waarvan de class FiliaalService implementeert
		filiaalService = Mockito.mock(FiliaalService.class);
		filiaalController = new FiliaalController(filiaalService);

		// ---------------------
		// Tests for "findAll()"
		// ---------------------
		filialen = Collections.emptyList();
		// De dummy method findAll geeft standaard null terug.
		// Je laat hier deze method de lege lijst in de variabele filialen teruggeven
		Mockito.when(filiaalService.findAll()).thenReturn(filialen);

		// ------------------------
		// Tests for "/filiaal.jsp"
		// ------------------------
		filiaal = new Filiaal("naam1", true, BigDecimal.ONE, new Date(),new Adres("straat1","huisnr1", 1, "gemeente1"));
		// Als je op filiaalService de method read oproept met een parameter 1,
		// moet die dummy het Filiaal object uit de variabele filiaal teruggeven.
		// Als je de method oproept met een ander getal, geeft de method null terug.
		// Dit is het standaard gedrag van een Mockito dummy.
		Mockito.when(filiaalService.read(1)).thenReturn(filiaal);
		//Mockito.when(filiaalService.read(666)).thenReturn(null);		// ???14.1???
	}

	// ---------------------
	// Tests for "findAll()"
	// ---------------------
	@Test
	public void findAllActiveertJuisteView()
	{
		// Je test of een controller method de juiste view activeert
		Assert.assertEquals("filialen/filialen",filiaalController.findAll().getViewName());
	}
	
	@Test
	public void findAllMaakRequestAttribuutFilialen()
	{
		// Je test of de ModelAndView een attribuut met de naam filialen bevat en of de inhoud
		// gelijk is aan de return waarde van de FiliaalService dummy method findAll
		Assert.assertSame(filialen,filiaalController.findAll().getModelMap().get("filialen"));
	}

	// ------------------------
	// Tests for "/filiaal.jsp"
	// ------------------------
	@Test
	public void readActiveertJuisteView()
	{
		// Het filiaal in het request attribuut filiaal moet hetzelfde zijn als het filiaal dat de method
		// read van de FiliaalService dummy teruggeeft bij een method oproep read(1L)
		//Assert.assertEquals("filialen/filiaal", filiaalController.read(1L).getViewName());
		Assert.assertEquals("filialen/filiaal", filiaalController.read(filiaal).getViewName());
	}

	@Test
	public void readMetBestaandeIDGeeftFiliaalTerug()
	{
		//Assert.assertSame(filiaal,filiaalController.read(1).getModelMap().get("filiaal"));
		Assert.assertSame(filiaal,filiaalController.read(filiaal).getModelMap().get("filiaal"));
	}
	
//	@Test
//	public void readMetOnbestaandeIDGeeftNullTerug()
//	{
//		// De method read van de FiliaalService dummy geeft null terug als je een andere
//		// waarde dan 1 meegeeft. Het request attribuut filiaal moet dan ook null bevatten.
//		Assert.assertNull(filiaalController.read(666).getModelMap().get("filiaal"));	// ???14.1???
//	}
}
