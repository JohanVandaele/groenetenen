package be.vdab.restservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceSupport;

import be.vdab.entities.Filiaal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

// Bij de class staat @XmlRootElement. JAXB kan dan een object v.d. class naar XML omzetten
@XmlRootElement
// JAXB doet die conversie standaard met getters en setters.
// Met deze annotation heeft JAXB geen getters of setters nodig.
@XmlAccessorType(XmlAccessType.FIELD)
// Jackson converteert standaard een object naar JSON met getters en setters.
// Met deze annotation heeft Jackson geen getters of setters nodig.
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
// Je erft van de class ResorceSupport en je hebt zo een verzameling Link objecten.
class FiliaalResource extends ResourceSupport
{
	// Je leest de variabele filiaal nergens in je code. Je vermijdt hier de warning die de compiler daarbij kan geven.
	@SuppressWarnings("unused")
	// Je neemt een Filiaal object op. De XML voorstelling hiervan wordt dan een onderdeel
	// van de XML voorstelling van een FiliaalResource.
	private Filiaal filiaal;

	FiliaalResource() {} // JAXB heeft een default constructor nodig
	
	FiliaalResource(Filiaal filiaal, EntityLinks entityLinks)
	{
		this.filiaal = filiaal;
		this.add(entityLinks.linkToSingleResource(
		Filiaal.class, filiaal.getId()));
		// Je voegt aan de verzameling Link objecten een link toe naar het filiaal zelf.
		// De XML voorstelling van een FiliaalResource object zal ook deze link bevatten.
		this.add(entityLinks.linkForSingleResource(Filiaal.class, filiaal.getId())
				// Je voegt aan de verzameling Link objecten een link toe naar de werknemers van het filiaal.
				// De XML voorstelling van een FiliaalResource object zal ook deze link bevatten.
				// Je bouwt de link op als de link naar het filiaal zelf, een slash en het woord werknemers.
				// Je vult het rel attribuut van de link met het woord werknemers
				.slash("werknemers").withRel("werknemers"));
	}
}
