package be.vdab.services;

import be.vdab.entities.Filiaal;
import be.vdab.valueobjects.PostcodeReeks;

public interface FiliaalService
{
	void create(Filiaal filiaal);
	Filiaal read(long id);
	void update(Filiaal filiaal);
	void delete(long id);
	Iterable<Filiaal> findAll();
	long findAantalFilialen();
	Iterable<Filiaal> findByPostcodeReeks(PostcodeReeks reeks);
	
	public Iterable<Filiaal> findNietAfgeschreven();		// Afschrijven
	public void afschrijven(Filiaal filiaal);				// Afschrijven
	public void afschrijven(Iterable<Filiaal> filialen);	// Afschrijven meervoudige selectie (30.6)
	
	public void aantalFilialenMail();
}
