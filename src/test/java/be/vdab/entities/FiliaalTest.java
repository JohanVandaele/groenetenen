package be.vdab.entities;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import be.vdab.valueobjects.Adres;

public class FiliaalTest
{
//	private Filiaal filiaal;
	private Filiaal filiaal = new Filiaal("FiliaalTest",false,new BigDecimal(100),new Date(), new Adres("Vlamingstraat","8",8500,"Wevelgem"));

	@Before
	public void before()
	{
		//Filiaal filiaal = new Filiaal("FiliaalTest",false,new BigDecimal(100),new Date(), new Adres("Vlamingstraat","8",8500,"Wevelgem"));
	}
	
	@Test
	public void get()
	{
		assertEquals("FiliaalTest", filiaal.getNaam());
		assertEquals(new BigDecimal(100), filiaal.getWaardeGebouw());
	}
}

