package be.vdab.valueobjects;

import javax.validation.constraints.AssertTrue;

import be.vdab.constraints.Postcode;

public class PostcodeReeks
{
	// -------
	// Private
	// -------
//	private final static int MIN_POSTCODE = 1000;
//	private final static int MAX_POSTCODE = 9999;
	
//	private int vanpostcode;
//	private int totpostcode;

	//@NotNull @Range(min = MIN_POSTCODE, max = MAX_POSTCODE)
	@Postcode
	private Integer vanpostcode;
	
	//@NotNull @Range(min = MIN_POSTCODE, max = MAX_POSTCODE)
	@Postcode
	private Integer totpostcode;

	// ----------
	// Properties
	// ----------
	//public int getVanpostcode() {
	public Integer getVanpostcode() {
		return vanpostcode;
	}

	//public void setVanpostcode(int vanpostcode) {
	public void setVanpostcode(Integer vanpostcode) {
		//valideer(vanpostcode);
		this.vanpostcode = vanpostcode;
	}

	//public int getTotpostcode() {
	public Integer getTotpostcode() {
		return totpostcode;
	}

	//public void setTotpostcode(int totpostcode) {
	public void setTotpostcode(Integer totpostcode) {
		//valideer(totpostcode);
		this.totpostcode = totpostcode;
	}

	// -------
	// Methods
	// -------
	public boolean bevat(int postcode)
	{
		// bevat de reeks een bepaalde postcode ? (wordt gebuikt in de DAO layer)
		return postcode >= vanpostcode && postcode <= totpostcode;
	}
	
//	private void valideer(int postcode)
//	{
//		if (postcode < MIN_POSTCODE || postcode > MAX_POSTCODE)
//		{
//			throw new IllegalArgumentException();
//		}
//	}
	
	// <form:errors path='totpostcodeIsGroterOfGelijkAanVanpostcode'/>
	@AssertTrue
	public boolean isTotpostcodeIsGroterOfGelijkAanVanpostcode()	// ???20.10???
	{
		return vanpostcode != null && totpostcode != null && totpostcode >= vanpostcode;
	}	
}
