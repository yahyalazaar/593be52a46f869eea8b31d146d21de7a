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

import model.Charges;
import model.ChargesFixe;
import model.ChargesTemporaire;
import services.ChargesFixeHome;
import services.ChargesHome;
import services.ChargesTemporaireHome;
import services.ProprieteHome;

@Named
@ViewScoped
public class ChargesFormMB implements Serializable {

	private Integer id;
	private Charges Charges;
	private Integer idSelectedCharges;
	private Integer idprop;
	@Inject
	ChargesHome Chargeshome;

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (has(id)) {
			Charges = Chargeshome.findById(id);
			
			if(new ChargesFixeHome().findById(id)!=null) {
				idSelectedCharges=1;
				
			}else {
				idSelectedCharges=2;
			}
			idprop= Charges.getPropriete().getIdPropriete();
		} else {
			Charges = new Charges();
			idSelectedCharges=1;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Charges getCharges() {
		return Charges;
	}

	public void setCharges(Charges Charges) {
		this.Charges = Charges;
	}

	public Integer getidSelectedCharges() {
		return idSelectedCharges;
	}

	public void setidSelectedCharges(Integer idSelectedCharges) {
		this.idSelectedCharges = idSelectedCharges;
	}

	public void save() {
		String msg;
		if (Charges.getIdCharges() == null) {
			Charges.setPropriete(new ProprieteHome().findById(idprop));
			Chargeshome.insert(Charges);
			if(idSelectedCharges==1) {
				new ChargesFixeHome().attachDirty(new ChargesFixe(Charges));
			}else {
				new ChargesTemporaireHome().attachDirty(new ChargesTemporaire(Charges));
			}
			msg = "Charges <b>" + Charges.getNomCharges() + "</b> created successfully";
		} else {
			Chargeshome.update(Charges);
			msg = "Charges <b>" + Charges.getNomCharges() + "</b> updated successfully";
		}

		addDetailMessage(msg);
	}

	public void clear() {
		Charges = new Charges();
	}

	public boolean isNew() {
		return Charges == null || Charges.getIdCharges() == null;
	}

	public Integer getIdprop() {
		return idprop;
	}

	public void setIdprop(Integer idprop) {
		this.idprop = idprop;
	}

}
