package be.vdab.restservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceSupport;

import be.vdab.entities.Filiaal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class FilialenResource extends ResourceSupport
{
	// Standaard zou het XML element dat één filiaal voorstelt filialenIdNaam heten.
	// Met deze annotation wijzig je deze naam naar filiaal.
	@XmlElement(name="filiaal")
	// Standaard zou de JSON property die de verzameling filialen voorstelt filialenIdNaam
	// heten. Met deze annotation wijzig je deze naam naar filialen.
	@JsonProperty("filialen")
	private List<FiliaalIdNaam> filialenIdNaam = new ArrayList<>();

	FilialenResource() {} // JAXB heeft een default constructor nodig
	
	FilialenResource(Iterable<Filiaal> filialen, EntityLinks entityLinks)
	{
		for (Filiaal filiaal : filialen)
		{
			// Je voegt per filiaal een item toe met het id en de naam van het filiaal.
			this.filialenIdNaam.add(new FiliaalIdNaam(filiaal)); 
			this.add(entityLinks.linkToSingleResource(Filiaal.class, filiaal.getId())
			// Je voegt per filiaal een link met de URL van dat filiaal toe.
			// Je vult het rel attribuut van deze link met Filiaal: gevolgd door het filiaal id
			.withRel("Filiaal:" + filiaal.getId())); 
		}

		// Je voegt aan de response een link toe naar alle filialen.
		this.add(entityLinks.linkToCollectionResource(Filiaal.class));
	}
}
