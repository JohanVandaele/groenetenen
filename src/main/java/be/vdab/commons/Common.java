package be.vdab.commons;

import org.springframework.hateoas.EntityLinks;

public interface Common
{
	public void setEntityLinks(EntityLinks entityLinks);
	public EntityLinks getEntityLinks();
}
