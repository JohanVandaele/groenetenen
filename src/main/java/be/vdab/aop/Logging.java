package be.vdab.aop;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
class Logging
{
	private final static Logger logger =Logger.getLogger(Logging.class.getName());
	
	// Je definieert een method als after throwing advice met @AfterThrowing.
	// Je geeft een parameter pointcut mee met een pointcut expressie.
	// De voorbeeldexpressie duidt methods uit de packages be.vdab.services en
	// org.springframework.transaction aan. Als zo'n method een exception werpt,
	// voert Spring na die method de method schrijfException (zie 4) uit.
	// Je geeft een parameter throwing mee als je de exception van het join point wil weten.
	// Je tikt daarbij de naam van een Throwable parameter in de method schrijfException.
	// Spring vult die parameter met de exception van het join point.
	//@AfterThrowing(pointcut = "execution(* be.vdab.services.*.*(..)) " +"|| execution(* org.springframework.transaction.*.*(..))",throwing = "ex")
	@AfterThrowing(pointcut = "be.vdab.aop.PointcutExpressions.servicesEnTransacties()",throwing = "ex")
	// Je geeft de method een extra Throwable parameter.
	// Spring vult die parameter in met de exception van het join point.
	void schrijfException(JoinPoint joinPoint, Throwable ex)
	{ 
		StringBuilder builder = new StringBuilder("\nTijdstip\t").append(new Date());
		Authentication authentication =	SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null)
		{
			builder.append("\nGebruiker\t").append(authentication.getName());
		}
		
		builder.append("\nMethod\t\t").append(joinPoint.getSignature().toLongString());
	
		int parameterVolgnummer = 0;
		
		for (Object object : joinPoint.getArgs())
		{
			builder.append("\nParameter ").append(++parameterVolgnummer).append('\t').append(object);
		}
	
		logger.log(Level.SEVERE, builder.toString(), ex);
	}
}
