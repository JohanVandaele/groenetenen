package be.vdab.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import be.vdab.enums.Gazontype;

public class Offerte implements Serializable
{
	// -------
	// Private
	// -------
	// Je definieert een validation group als een lege interface.
	// De naam van de interface wordt de naam van de validation group
	public interface Stap1 {}
	public interface Stap2 {}

	private static final long serialVersionUID = 1L;

	@NotBlank(groups = Stap1.class)
	@SafeHtml(groups = Stap1.class)
	private String voornaam;
	
	@NotBlank(groups = Stap1.class)
	@SafeHtml(groups = Stap1.class)
	private String familienaam;

	@NotBlank(groups = Stap1.class)
	@Email(groups = Stap1.class)
	private String emailAdres;
	
	@NotNull(groups = Stap2.class)
	@Min(value = 1, groups = Stap2.class)
	private Integer oppervlakte;
	
	// Array
	//private String[] telefoonNrs = new String[2];
	// List
	private List<String> telefoonNrs = new ArrayList<>();
	// Map
	private Map<Gazontype, Boolean> gazontypes = new LinkedHashMap<>();
	
	// -----------
	// Constructor
	// -----------
	public Offerte()
	{
		telefoonNrs.add(""); // vak voor een eerste te tikken telefoonnummer

		for (Gazontype gazontype: Gazontype.values())
		{
			gazontypes.put(gazontype, false);
		}
	}
	
	// ----------
	// Properties
	// ----------
	// Map
	public Map<Gazontype, Boolean> getGazontypes() {
		return gazontypes;
	}

	public void setGazontypes(Map<Gazontype, Boolean> gazontypes) {
		this.gazontypes = gazontypes;
	}

	// Array
//	public String[] getTelefoonNrs() {
//		return telefoonNrs;
//	}
//	public void setTelefoonNrs(String[] telefoonNrs) {
//		this.telefoonNrs = telefoonNrs;
//	}

	// List
	public List<String> getTelefoonNrs() {
		return telefoonNrs;
	}
	public void setTelefoonNrs(List<String> telefoonNrs) {
		this.telefoonNrs = telefoonNrs;
	}
	
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}
	public String getEmailAdres() {
		return emailAdres;
	}
	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}
	public Integer getOppervlakte() {
		return oppervlakte;
	}
	public void setOppervlakte(Integer oppervlakte) {
		this.oppervlakte = oppervlakte;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// -------
	// Methods
	// -------
	public void nogEenTelefoonNr()
	{
		telefoonNrs.add("");
	}
}