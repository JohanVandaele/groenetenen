package be.vdab.web;

import java.io.Serializable;

//import org.apache.felix.ipojo.annotations.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
class VoorkeurImpl implements Voorkeur, Serializable
{
	// -------
	// Private
	// -------
	private static final long serialVersionUID = 1L;
	private String foto;
	
	// ----------
	// Properties
	// ----------
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
