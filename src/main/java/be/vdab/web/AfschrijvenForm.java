package be.vdab.web;

import java.util.List;

import javax.validation.constraints.NotNull;

import be.vdab.entities.Filiaal;

class AfschrijvenForm
{
	// -------
	// Private
	// -------
//	// Met enkelvoudige selectie
//	@NotNull
//	private Filiaal filiaal;
	
	// Met meervoudige selectie
	@NotNull
	private List<Filiaal> filialen;

	// ----------
	// Properties
	// ----------
//	public Filiaal getFiliaal() {
//		return filiaal;
//	}
//
//	public void setFiliaal(Filiaal filiaal) {
//		this.filiaal = filiaal;
//	}

	public List<Filiaal> getFilialen() {
		return filialen;
	}

	public void setFilialen(List<Filiaal> filialen) {
		this.filialen = filialen;
	}
}
