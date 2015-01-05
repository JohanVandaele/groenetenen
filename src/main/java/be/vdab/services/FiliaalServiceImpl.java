package be.vdab.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.commons.Common;
import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.mail.MailSender;
import be.vdab.valueobjects.PostcodeReeks;

@Service // met deze annotation maak je een Spring bean van deze class
@Transactional(readOnly = true)
class FiliaalServiceImpl implements FiliaalService
{
	// -------
	// Private
	// -------
	private final FiliaalDAO filiaalDAO;
	private final MailSender mailSender;
	private final Common common;

	// -----------
	// Constructor
	// -----------
//	@Autowired
//	FiliaalServiceImpl(FiliaalDAO filiaalDAO)
//	{
//		// Spring injecteert de parameter filiaalDAO
//		// met de bean die de interface FiliaalDAO implementeert
//		this.filiaalDAO = filiaalDAO;
//	}

//	@Autowired
//	FiliaalServiceImpl(FiliaalDAO filiaalDAO, MailSender mailSender)
//	{
//		this.filiaalDAO = filiaalDAO;
//		this.mailSender = mailSender;
//	}	

	@Autowired
	FiliaalServiceImpl(FiliaalDAO filiaalDAO,MailSender mailSender,Common common)
	{
		this.filiaalDAO = filiaalDAO;
		this.mailSender = mailSender;
		this.common = common;
	}	
	
	// -------
	// Methods
	// -------
	@Override
	@Transactional(readOnly = false)
	public void create(Filiaal filiaal)
	{
		// Zonder JpaRepository
		//filiaalDAO.create(filiaal);
		
		// Met JpaRepository
		filiaal.setId(filiaalDAO.save(filiaal).getId());
		//mailSender.nieuwFiliaalMail(filiaal);
		mailSender.nieuwFiliaalMail(filiaal,common.getEntityLinks().linkForSingleResource(Filiaal.class, filiaal.getId()).toString());
	}
	
	@Override
	public Filiaal read(long id)
	{
		// Zonder JpaRepository
		//return filiaalDAO.read(id);

		// Met JpaRepository
		return filiaalDAO.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Filiaal filiaal)
	{
		// Zonder JpaRepository
		//filiaalDAO.update(filiaal);

		// Met JpaRepository
		filiaalDAO.save(filiaal);
	}
	
//	// JDBC
//	@Override
//	@Transactional(readOnly = false)
//	public void delete(long id)
//	{
//		if (filiaalDAO.findAantalWerknemers(id) != 0)
//		{
//			throw new FiliaalHeeftNogWerknemersException();
//		}
//	
//		filiaalDAO.delete(id);
//	}
	
	// JPA	// ??? Werkt niet bij uitproberen website (29.5, 29.6) ???
	@Override
	@Transactional(readOnly = false)
	public void delete(long id)
	{
		// Zonder JpaRepository
		//Filiaal filiaal = filiaalDAO.read(id);

		// Met JpaRepository
		Filiaal filiaal = filiaalDAO.findOne(id);
		
		if (filiaal != null)
		{
			if ( ! filiaal.getWerknemers().isEmpty())
			{
				throw new FiliaalHeeftNogWerknemersException();
			}
			
			filiaalDAO.delete(id);
		}
	}
	
	@Override
	public Iterable<Filiaal> findAll()
	{
		// Zonder JpaRepository
		//return filiaalDAO.findAll();

		// Met JpaRepository
		return filiaalDAO.findAll(new Sort("naam"));
	}
	
	@Override
	public long findAantalFilialen()
	{
		// Zonder JpaRepository
		//return filiaalDAO.findAantalFilialen();

		// Met JpaRepository
		return filiaalDAO.count();
	}
	
	@Override
	@PreAuthorize("hasAuthority('manager')")
	public Iterable<Filiaal> findByPostcodeReeks(PostcodeReeks reeks)
	{
		// Zonder JpaRepository
		//return filiaalDAO.findByPostcodeReeks(reeks);

		// Met JpaRepository
		return filiaalDAO.findByAdresPostcodeBetweenOrderByNaamAsc(reeks.getVanpostcode(),reeks.getTotpostcode());
	}
	
	@Override
	public Iterable<Filiaal> findNietAfgeschreven()
	{
		return filiaalDAO.findByWaardeGebouwNot(BigDecimal.ZERO);
	}
	
	// Met enkelvoudige selectie
	@Override
	@Transactional(readOnly = false)
	public void afschrijven(Filiaal filiaal)
	{
		filiaal.afschrijven();
		filiaalDAO.save(filiaal);
	}
	
	// Met meervoudige selectie
	@Override
	@Transactional(readOnly = false)
	public void afschrijven(Iterable<Filiaal> filialen)
	{
		for (Filiaal filiaal : filialen)
		{
			filiaal.afschrijven();
			filiaalDAO.save(filiaal);
		}
	}
	
	@Override
	@Scheduled(/*cron = "0 0 1 * *"*/ fixedRate=60000) // om de minuut om te testen
	public void aantalFilialenMail()
	{
		mailSender.aantalFilialenMail(filiaalDAO.count());
	}
}
