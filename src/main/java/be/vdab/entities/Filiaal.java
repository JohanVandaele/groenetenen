package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import be.vdab.valueobjects.Adres;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * Een <strong>filiaal</strong>
 * @author Johan Vandaele
 *
 */
@XmlRootElement
@Entity
@Table(name = "filialen")
public class Filiaal implements Serializable
{
	// -------
	// Private
	// -------
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@SafeHtml	// Hack vermijden
	@NotBlank
	@Length(min = 1, max = 50)
	private String naam;
	
	private boolean hoofdFiliaal;
	
	@NumberFormat(style = Style.NUMBER)
	
	@NotNull
	@Min(0)
	@Digits(integer = 10, fraction = 2)
	private BigDecimal waardeGebouw;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "S-")
	@NotNull
	private Date inGebruikName;
	
	@Embedded
	@Valid
	private Adres adres;

	// ---
	// JPA
	// ---
//	@OneToMany(mappedBy = "filiaal")		//  ??? Werkt niet bij uitproberen website (29.5, 19.6) ???
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "filiaal")
	// Wanneer Spring een Filiaal object omzet naar XML of naar JSON slaat Spring de
	// werknemer data over. Je beperkt zo de omvang van de response data.
	@XmlTransient @JsonIgnore
	private Set<Werknemer> werknemers;
	public Set<Werknemer> getWerknemers()
	{
		return Collections.unmodifiableSet(werknemers);
	}
	
	// -----------
	// Constructor
	// -----------
	/**
	* Maakt een Filiaal object
	*/
	public Filiaal() {}
	
	/**
	* Maakt een Filiaal object
	* @param naam De naam van het filiaal
	* @param hoofdFiliaal Indicatie voor hoofdgebouw/bijgebouw 
	* @param waardeGebouw De waarde van het gebouw
	* @param inGebruikName Datum van ingebruikname
	* @param adres Het adres van het filiaal
	*/
	public Filiaal(String naam,boolean hoofdFiliaal,BigDecimal waardeGebouw,Date inGebruikName, Adres adres)
	{
		this.naam = naam;
		this.hoofdFiliaal = hoofdFiliaal;
		this.waardeGebouw = waardeGebouw;
		this.inGebruikName = inGebruikName;
		this.adres = adres;
	}
	
	/**
	* Maakt een Filiaal object
	* @param id Identificatie van het filiaal
	* @param naam De naam van het filiaal
	* @param hoofdFiliaal Indicatie voor hoofdgebouw/bijgebouw 
	* @param waardeGebouw De waarde van het gebouw
	* @param inGebruikName Datum van ingebruikname
	* @param adres Het adres van het filiaal
	*/
	public Filiaal(long id, String naam,boolean hoofdFiliaal,BigDecimal waardeGebouw,Date inGebruikName, Adres adres)
	{
		this(naam, hoofdFiliaal, waardeGebouw,inGebruikName, adres);
		this.id = id;
	}

	// ----------
	// Properties
	// ----------
	/**
	* Geeft de id terug
	* @return de id
	*/	
	public long getId() {
		return id;
	}

	/**
	* toekenning van de id
	* @param de id
	*/	
	public void setId(long id) {
		this.id = id;
	}

	/**
	* Geeft de naam terug
	* @return de naam
	*/	
	public String getNaam() {
		return naam;
	}

	/**
	* toekenning van de naam
	* @param de naam
	*/	
	public void setNaam(String naam) {
		this.naam = naam;
	}

	/**
	* Geeft indicatie terug voor hoofdfiliaal
	* @return hoofdfiliaal
	*/	
	public boolean isHoofdFiliaal() {
		return hoofdFiliaal;
	}

	/**
	* Toekenning voor hoofdfiliaal
	* @param hoofdfiliaal
	*/	
	public void setHoofdFiliaal(boolean hoofdFiliaal) {
		this.hoofdFiliaal = hoofdFiliaal;
	}

	/**
	* Geeft de waarde van het gebouw terug
	* @return waarde van het gebouw
	*/	
	public BigDecimal getWaardeGebouw() {
		return waardeGebouw;
	}

	/**
	* toekenning van de waarde van het gebouw
	* @param de waarde van het gebouw
	*/	
	public void setWaardeGebouw(BigDecimal waardeGebouw) {
		this.waardeGebouw = waardeGebouw;
	}

	/**
	* Geeft de datum van ingebruikname terug
	* @return de datum van ingebruikname
	*/	
	public Date getInGebruikName() {
		return inGebruikName;
	}

	/**
	* Toekenning van de datum van ingebruikname
	* @param datum van ingebruikname
	*/	
	public void setInGebruikName(Date inGebruikName) {
		this.inGebruikName = inGebruikName;
	}

	/**
	* Geeft het adres terug
	* @return het adres
	*/	
	public Adres getAdres() {
		return adres;
	}

	/**
	* Toekenning van het adres
	* @param het adres
	*/	
	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	/**
	* Geeft de Serialversionuid terug
	* @return Serialversionuid
	*/	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// -------
	// Methods
	// -------
	/**
	* Zet de waarde van het gebouw op 0
	*/
	public void afschrijven()
	{
		this.waardeGebouw = BigDecimal.ZERO;
	}
}
