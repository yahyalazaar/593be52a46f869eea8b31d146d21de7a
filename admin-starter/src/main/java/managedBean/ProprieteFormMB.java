/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import static com.github.adminfaces.template.util.Assert.has;
import static utils.Utils.addDetailMessage;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import model.Propriete;
import model.Ville;
import services.ProprieteHome;
import services.VilleHome;

@Named
@ViewScoped
public class ProprieteFormMB implements Serializable {

	private Integer id;
	private Propriete Propriete;
	private Integer idSelectedVille;
	@Inject
	ProprieteHome Proprietehome;

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (has(id)) {
			Propriete = Proprietehome.findById(id);
			idSelectedVille = Propriete.getVille().getIdVille();
		} else {
			Propriete = new Propriete();
			//Propriete.setVille(new Ville());
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Propriete getPropriete() {
		return Propriete;
	}

	public void setPropriete(Propriete Propriete) {
		this.Propriete = Propriete;
	}

	public Integer getIdSelectedVille() {
		return idSelectedVille;
	}

	public void setIdSelectedVille(Integer idSelectedVille) {
		this.idSelectedVille = idSelectedVille;
	}

	public void save() {
		String msg;
		Propriete.setVille(new VilleHome().findById(idSelectedVille));
		if (Propriete.getIdPropriete() == null) {
			Proprietehome.insert(Propriete);
			msg = "Propriete <b>" + Propriete.getNomPropriete() + "</b> created successfully";
		} else {
			Proprietehome.update(Propriete);
			msg = "Propriete <b>" + Propriete.getNomPropriete() + "</b> updated successfully";
		}

		addDetailMessage(msg);
	}

	public void clear() {
		Propriete = new Propriete();
	}

	public boolean isNew() {
		return Propriete == null || Propriete.getIdPropriete() == null;
	}

}
