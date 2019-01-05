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

import model.User;
import model.UserCoproprietaire;
import services.ProprieteHome;
import services.UserCopHome;
import services.UserHome;

@Named
@ViewScoped
public class UserCopFormMB implements Serializable {

	private Integer id;
	private UserCoproprietaire UserCoproprietaire;
	private User User;
	private Integer idprop;

	@Inject
	UserCopHome UserCoproprietairehome;

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (has(id)) {
			User = new UserHome().findById(id);
			UserCoproprietaire = UserCoproprietairehome.findById(id);
			UserCoproprietaire.setUser(User);
			idprop = UserCoproprietaire.getPropriete().getIdPropriete();

		} else {
			UserCoproprietaire = new UserCoproprietaire();
			User = new User();
			UserCoproprietaire.setUser(User);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserCoproprietaire getUserCoproprietaire() {
		return UserCoproprietaire;
	}

	public void setUserCoproprietaire(UserCoproprietaire UserCoproprietaire) {
		this.UserCoproprietaire = UserCoproprietaire;
	}

	public Integer getIdprop() {
		return idprop;
	}

	public void setIdprop(Integer idprop) {
		this.idprop = idprop;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		this.User = user;
	}

	public void save() {
		String msg;

		if (User.getIdUser() == null) {
			UserCoproprietairehome.attachDirtyUser(User);
			UserCoproprietaire.setUser(User);
			UserCoproprietaire.setPropriete(new ProprieteHome().findById(idprop));
			UserCoproprietairehome.insert(UserCoproprietaire);

			msg = "UserCoproprietaire <b>" + User.getNomUser() + "</b> created successfully";
		} else {
			UserCoproprietairehome.update(UserCoproprietaire);
			msg = "UserCoproprietaire <b>" + User.getNomUser() + "</b> updated successfully";
		}
		addDetailMessage(msg);
	}

	public void clear() {
		UserCoproprietaire = new UserCoproprietaire();
		User = new User();
	}

	public boolean isNew() {
		return UserCoproprietaire == null;
	}

}
