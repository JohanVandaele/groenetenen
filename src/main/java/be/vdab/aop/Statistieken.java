package be.vdab.aop;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Statistieken
{
	private final static Logger logger = Logger.getLogger(Statistieken.class.getName());
	// De key van deze map is een String met de signatuur van een join point.
	// De value is hoeveel keer dit join point werd opgeroepen.
	private final ConcurrentHashMap<String, AtomicInteger> statistieken = new ConcurrentHashMap<>();

	// Je definieert een method als after finally advice met @After met een pointcut expressie.
	// Elke keer je een method oproept die bij deze pointcut expressie hoort,
	// voert Spring na de opgeroepen method de method statistiekenBijwerken uit,
	// ongeacht of de opgeroepen method al of niet een exception werpt.
	//@After("execution(* be.vdab.services.*.*(..))")
	@After("be.vdab.aop.PointcutExpressions.services()")
	void statistiekBijwerken(JoinPoint joinPoint)
	{
		String joinPointSignatuur = joinPoint.getSignature().toLongString();
		// Als de join point signatuur nog niet voorkomt in de Map, voeg je een entry toe met als key
		// de join point signatuur en als value 1. Als de join point signatuur wel voorkwam in de Map,
		// krijg je de bijbehorende value.
		AtomicInteger vorigGevondenAantal =	statistieken.putIfAbsent(joinPointSignatuur, new AtomicInteger(1));
		// Als de join point signatuur al voorkwam in de Map, verhoog je de bijbehorende value.
		int aantalOproepen = vorigGevondenAantal == null ? 1 :vorigGevondenAantal.incrementAndGet();
		logger.info(joinPointSignatuur + " werd " + aantalOproepen + " opgeroepen");
	}
}
