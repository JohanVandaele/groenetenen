package be.vdab.mail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import be.vdab.entities.Filiaal;

// Je maakt een bean van deze class
@Component
public class MailSenderImpl implements MailSender
{
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private final JavaMailSender sender;
	private final String webmaster;
	
	@Autowired
	public MailSenderImpl(JavaMailSender sender,@Value("${webmaster}") String webmaster)
	{
		this.sender = sender;
		this.webmaster = webmaster;
	}
	
	@Async
	@Override
//	public void nieuwFiliaalMail(Filiaal filiaal)
	public void nieuwFiliaalMail(Filiaal filiaal, String url)
	{
		try
		{
			// De class MimeMessage stelt een mail met HTML opmaak voor.
			MimeMessage message = sender.createMimeMessage();
			// Je vult de eigenschappen To, Subject en Text van een MimeMessage in met de helper class MimeMessageHelper.
			
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(webmaster);
			helper.setSubject("Nieuw filiaal");
			// Je doet opmaak met HTML tags. Je plaatst de tweede parameter op true.
			// Je geeft zo aan dat je HTML tags gebruikt in de tekst.
			//helper.setText(String.format("Filiaal <strong>%s</strong> is toegevoegd",filiaal.getNaam()), true);
			// Je maakt een hyperlink in de mail tekst die naar de filiaal URL + /wijzigen verwijst.
			helper.setText(String.format("Je kan het nieuwe filiaal <strong>%s</strong> "+"<a href='%s'>hier</a> nazien", filiaal.getNaam(), url + "/wijzigen"), true);
			// Je verstuurt de mail
			sender.send(message);
		}
		catch (MessagingException ex)
		{
			logger.log(Level.SEVERE, "kan mail nieuw filiaal niet versturen", ex);
		}
	}
	
	@Override
	public void aantalFilialenMail(long aantal)
	{
		try
		{
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(webmaster);
			helper.setSubject("Aantal filialen");
			helper.setText(String.format("Er zijn <strong>%d</strong> filialen.",aantal), true);
			sender.send(message);
		}
		catch (MessagingException ex)
		{
			logger.log(Level.SEVERE, "kan mail aantal filialen niet versturen", ex);
		}
	}	
}
