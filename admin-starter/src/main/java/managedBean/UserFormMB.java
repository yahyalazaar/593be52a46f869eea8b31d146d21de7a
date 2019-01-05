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
import model.UserAdmin;
import model.UserCoproprietaire;
import model.UserSyndic;
import services.ProprieteHome;
import services.UserHome;

@Named
@ViewScoped
public class UserFormMB implements Serializable {

	private Integer id;
	private User User;
	private Integer idprop;

	@Inject
	UserHome Userhome;

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (has(id)) {
			User = Userhome.findById(id);

		} else {
			User = new User();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User User) {
		this.User = User;
	}

	public Integer getIdprop() {
		return idprop;
	}

	public void setIdprop(Integer idprop) {
		this.idprop = idprop;
	}

	public void save() {
		String msg;

		if (User.getIdUser() == null) {
			Userhome.insert(User);
			if (User.getTypeUser().equals("admin")) {
				Userhome.attachDirtyAdmin(new UserAdmin(User));
			}
			msg = "User <b>" + User.getNomUser() + "</b> created successfully";
		} else {
			Userhome.update(User);
			msg = "User <b>" + User.getNomUser() + "</b> updated successfully";
		}
		addDetailMessage(msg);
	}

	public void clear() {
		User = new User();
	}

	public boolean isNew() {
		return User == null || User.getIdUser() == null;
	}

}
