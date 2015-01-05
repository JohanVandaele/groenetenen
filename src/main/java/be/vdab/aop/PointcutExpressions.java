package be.vdab.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

// Je tikt @Aspect vóór de class die de gecentraliseerde pointcuts bevatten
@Aspect
class PointcutExpressions
{
	// Je definieert een gecentraliseerde pointcut expressie met @Pointcut voor een method.
	@Pointcut("execution(* be.vdab.services.*.*(..))")
	// De methodnaam na @Pointcut is de naam die je associeert met de pointcut expressie.
	// Je associeert be.vdab.aop.PointcutExpressies.services()
	// met de pointcut expressie execution(* be.vdab.services.*.*(..)).
	// De method heeft geen parameters en geeft void terug.
	void services() {}

	@Pointcut("execution(* be.vdab.services.*.*(..)) " +"|| execution(* org.springframework.transaction.*.*(..))")
	// Je associeert een pointcut expressie met de naam be.vdab.aop.PointcutExpressions.servicesEnTransacties()
	void servicesEnTransacties() {}
}
