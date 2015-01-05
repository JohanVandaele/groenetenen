package be.vdab.aop;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
class Performance
{
	private final static Logger logger = Logger.getLogger(Performance.class.getName());

	// Je definieert een method als around advice met @Around met een pointcut expressie.
	// Elke keer je een method oproept die bij deze pointcut expressie hoort, voert Spring de
	// method schrijfPerformance (zie 2) uit, niet de opgeroepen method zelf.
	//@Around("execution(* be.vdab.services.*.*(..))")
	@Around("be.vdab.aop.PointcutExpressions.services()")
	// Je geeft de method die het advice voorstelt een ProceedingJoinPoint parameter van het
	// type ProceedingJoinPoint. ProceedingJoinPoint erft van JoinPoint en bevat extra
	// functionaliteit die je nodig hebt in around advice.
	// Een around advice method moet als returntype Object hebben.
	// De method werpt alle fouten, die het join point eventueel werpt,
	// verder naar de code die oorspronkelijk het join point opgeroepen heeft.
	Object schrijfPerformance(ProceedingJoinPoint joinPoint) throws Throwable
	{
		// Voor je het join point uitvoert, onthoud je het resultaat van System.nanoTime(). Dit geeft
		// je een getal dat elke nanoseconde (miljardste van een seconde) met één verhoogt.
		long voor = System.nanoTime();

		try
		{
			// Bij around advice is het jouw verantwoordelijkheid om het join point al of niet uit te
			// voeren. Je voert het join point zelf uit met de ProceedingJoinPoint method proceed.
			// Je doet deze oproep in een try blok gezien ze een fout kan werpen.
			// De returnwaarde van de proceed method bevat de returnwaarde van het join point zelf.
			// Je geeft die door aan de code die het join point oorspronkelijk heeft opgeroepen.
			return joinPoint.proceed(); 
		}
		// Het finally blok wordt altijd opgeroepen (ook bij de return in het try blok).
		finally
		{
			// Je berekent de duurtijd van het uitvoeren van het join point.
			long duurtijd = System.nanoTime() - voor;
			logger.info(joinPoint.getSignature().toLongString() +" duurde " + duurtijd + " nanoseconden");
		}
	}
}
