package be.vdab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.dao.WerknemerDAO;
import be.vdab.entities.Werknemer;

@Service
@Transactional(readOnly = true)
class WerknemerServiceImpl implements WerknemerService
{
	private final WerknemerDAO werknemerDAO;
	
	@Autowired
	WerknemerServiceImpl(WerknemerDAO werknemerDAO)
	{
		this.werknemerDAO = werknemerDAO;
	}

//	// Not Pageable
//	@Override
//	public Iterable<Werknemer> findAll()
//	{
//		return werknemerDAO.findAll(new Sort("familienaam", "voornaam"));
//	}
	
	// Pagable
	@Override
	public Page<Werknemer> findAll(Pageable pageable)
	{
		return werknemerDAO.findAll(pageable);
	}
}
