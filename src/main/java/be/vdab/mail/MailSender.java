package be.vdab.mail;

import be.vdab.entities.Filiaal;

public interface MailSender
{
	//void nieuwFiliaalMail(Filiaal filiaal);
	void nieuwFiliaalMail(Filiaal filiaal, String url);
	void aantalFilialenMail(long aantal);
}
