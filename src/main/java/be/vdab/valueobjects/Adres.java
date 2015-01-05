package be.vdab.valueobjects;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import be.vdab.constraints.Postcode;

public class Adres implements Serializable
{
	// -------
	// Private
	// -------
	private static final long serialVersionUID = 1L;
	
	@SafeHtml
	@NotBlank
	@Length(min = 1, max = 50)
	private String straat;
	
	@SafeHtml
	@NotBlank
	@Length(min = 1, max = 7)
	private String huisNr;
	
	@NotNull
	@Postcode
	private Integer postcode;
	
	@SafeHtml
	@NotBlank
	@Length(min = 1, max = 50)
	private String gemeente;

	// -----------
	// Constructor
	// -----------
	public Adres() {}
	
	public Adres(String straat,String huisNr,Integer postcode,String gemeente)
	{
		this.straat = straat;
		this.huisNr = huisNr;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}

	// ----------
	// Properties
	// ----------
	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getHuisNr() {
		return huisNr;
	}

	public void setHuisNr(String huisNr) {
		this.huisNr = huisNr;
	}

	public Integer getPostcode() {
		return postcode;
	}

	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}

	public String getGemeente() {
		return gemeente;
	}

	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
