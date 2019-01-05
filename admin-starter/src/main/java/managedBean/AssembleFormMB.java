/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import static com.github.adminfaces.template.util.Assert.has;
import static utils.Utils.addDetailMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import androidNotificationModel.EntityMessage;
import androidNotificationModel.FcmClient;
import androidNotificationModel.FcmResponse;
import androidNotificationModel.SendNotification;
import model.Assemble;
import services.AssembleHome;
import services.ProprieteHome;

@Named
@ViewScoped
public class AssembleFormMB implements Serializable {

	private Integer id;
	private Assemble Assemble;
	@Inject
	AssembleHome Assemblehome;

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (has(id)) {
			Assemble = Assemblehome.findById(id);

		} else {
			Assemble = new Assemble();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Assemble getAssemble() {
		return Assemble;
	}

	public void setAssemble(Assemble Assemble) {
		this.Assemble = Assemble;
	}

	public void save() {
		String msg;

		if (Assemble.getIdAssemble() == null) {
			Assemble.setPropriete(new ProprieteHome().findById(3));
			Assemblehome.insert(Assemble);

			try {
				new SendNotification().sendNotification("nouveau assemble", "vote stp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			msg = "Assemble created successfully";
		} else {
			Assemblehome.update(Assemble);
			try {
				new SendNotification().sendNotification("update assemble", "vote stp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg = "Assemble updated successfully";
		}
		addDetailMessage(msg);
	}

	public void clear() {
		Assemble = new Assemble();
	}

	public boolean isNew() {
		return Assemble == null;
	}

}
