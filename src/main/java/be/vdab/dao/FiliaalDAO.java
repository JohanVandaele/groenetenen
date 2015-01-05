// -----------------
// Met JpaRepository
// -----------------
package be.vdab.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import be.vdab.entities.Filiaal;

public interface FiliaalDAO extends JpaRepository<Filiaal, Long>
{
	public List<Filiaal> findByAdresPostcodeBetweenOrderByNaamAsc(int van, int tot);
	
	Iterable<Filiaal> findMetHoogsteWaardeGebouw();
	BigDecimal findGemiddeldeWaardeGebouwInGemeente(@Param("gemeente") String gemeente);
	public List<Filiaal> findByWaardeGebouwNot(BigDecimal waarde);	// 30. Afschrijven
}

//------------------	----------------------------------------
// Verwijderde method	Overeenkomstige method uit JpaRepository
//------------------	----------------------------------------
// create				Save
// read					findOne
// update				Save
// delete				delete
// findAll				findAll
// findAantalFilialen	count
//------------------	----------------------------------------

// Niet meer nodig (29.6)
// --------------------
// Zonder JpaRepository
// --------------------
//package be.vdab.dao;
//
//import be.vdab.entities.Filiaal;
//import be.vdab.valueobjects.PostcodeReeks;
//
//public interface FiliaalDAO
//{
//	void create(Filiaal filiaal);
//	Filiaal read(long id);
//	void update(Filiaal filiaal);
//	void delete(long id);
//	Iterable<Filiaal> findAll();
//	long findAantalFilialen();
//	
//	// Niet nodig voor JPA (29.5)
//	//long findAantalWerknemers(long id);	// het aantal werknemers van een filiaal:
//	
//	Iterable<Filiaal> findByPostcodeReeks(PostcodeReeks reeks);
//}
