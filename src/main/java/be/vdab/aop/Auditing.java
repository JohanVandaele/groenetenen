package be.vdab.aop;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// Je definieert een class als aspect met @Aspect.
@Aspect 
// Een aspect moet zelf ook een Spring bean zijn.
@Component 
@Order(1)
class Auditing
{
	// Je zal met deze Logger auditing informatie schrijven naar de webserver logbestanden
	private final static Logger logger = Logger.getLogger(Auditing.class.getName());

	// Je definieert een method als before advice met @Before met een pointcut expressie.
	// De voorbeeldexpressie duidt methods uit de package be.vdab.services aan.
	// Als je zo'n method oproept, voert Spring eerst de method schrijfAudit (zie 5) uit,
	// daarna de opgeroepen method zelf.
//	@Before("execution(* be.vdab.services.*.*(..))")
//	// Je geeft de method die het advice voorstelt een parameter van het type JoinPoint.
//	// Deze parameter verwijst naar het join point waarvóór Spring het advice uitvoert
//	// en geeft interessante informatie over dit join point.
//	void schrijfAudit(JoinPoint joinPoint)
	// Je definieert een method als after returning advice met @AfterReturning.
	// Je geeft een parameter pointcut mee met een pointcut expressie.
	// De voorbeeldexpressie duidt methods uit de package be.vdab.services aan.
	// Als je zo'n method oproept, voert Spring na die method de method schrijfAudit (zie 3)
	// uit als de opgeroepen method geen exception werpt.
	// Je geeft een parameter returning mee als je de returnwaarde van het join point wil
	// weten. Je tikt daarbij de naam van een Object parameter in de method schrijfAudit.
	// Spring vult die parameter met de returnwaarde van het join point.
	//@AfterReturning(pointcut = "execution(* be.vdab.services.*.*(..))",returning = "returnValue")
	@AfterReturning(pointcut = "be.vdab.aop.PointcutExpressions.services()",returning = "returnValue")
	// Je geeft de method een extra Object parameter.
	// Spring vult die parameter in met de returnwaarde van het join point.
	void schrijfAudit(JoinPoint joinPoint, Object returnValue)
	{ 
		// Je bouwt de auditing informatie op in een StringBuilder object.
		// Je voegt hier het tijdstip toe waarop een DAO method werd uitgevoerd.
		StringBuilder builder = new StringBuilder("\nTijdstip\t").append(new Date());
		
		// Je haalt met SecurityContextHolder.getContext().getAuthentication() een
		// Authentication object op. Dit bevat informatie over de ingelogde gebruiker.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Als een service method uitgevoerd werd door een niet-ingelogde gebruiker,
		// bevat de variabele authentication de waarde null
		if (authentication != null)
		{
			builder.append("\nGebruiker\t").append(authentication.getName());
		}
		
		// De method getSignature van een JoinPoint geeft je de method declaratie
		// van het join point als een Signature object.
		// De method toLongString geeft je de package-, interface- én methodnaam.
		builder.append("\nMethod\t\t").append(joinPoint.getSignature().toLongString());
		
		int parameterVolgnummer = 0;
		
		// getArgs geeft je een Object array met de parameterwaarden van het join point.
		for (Object object : joinPoint.getArgs())
		{
			builder.append("\nParameter ").append(++parameterVolgnummer).append('\t').append(object);
		}
		
		// Voor @AfterReturning
		// Als het returntype van het join point void is, bevat returnValue null.
		if (returnValue != null)
		{
			builder.append("\nReturn\t\t");
			
			// Als de returnwaarde een Collection (List, Set, Map) is
			if (returnValue instanceof Collection)
			{
				// toon je enkel het aantal elementen in de verzameling,om de omvang van de auditing output te beperken.
				builder.append(((Collection<?>) returnValue).size()).append(" objects");
			}
			else
			{
				// Anders toon je de returnwaarde.
				builder.append(returnValue.toString());
			}
		}

		logger.info(builder.toString());
	}
}
