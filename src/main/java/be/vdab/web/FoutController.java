package be.vdab.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Als in om het even welke Controller een exception optreedt, zoekt Spring in een class,
// voorzien van @ControllerAdvice, een method die de opgetreden exception verwerkt.
@ControllerAdvice
public class FoutController
{
	public static final String VIEW = "fout";

	// Je tikt @ExceptionHandler bij zo’n method. Je geeft als parameter het type exception mee
	// die de method verwerkt. Je vermeldt hier Exception bij de method foutPagina.
	// Deze method verwerkt dus exceptions van het type Exception en ook alle afgeleide types.
	// Als je verschillende types exceptions op een verschillende manier wil verwerken,
	// kan je meerdere methods tikken, elk voorzien van @ExceptionHandler
	// met een ander exception type als parameter.
	@ExceptionHandler(Exception.class)
	public String foutPagina()
	{
		// Je toont een eigen foutpagina als de exception optreedt.
		return VIEW;
	}
}
