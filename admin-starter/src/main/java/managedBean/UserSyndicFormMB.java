/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import static com.github.adminfaces.template.util.Assert.has;
import static utils.Utils.addDetailMessage;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import model.SyndicPropriete;
import model.SyndicProprieteId;
import model.User;
import model.UserSyndic;
import services.ProprieteHome;
import services.UserHome;
import services.UserSyndicHome;

@Named
@ViewScoped
public class UserSyndicFormMB implements Serializable {

	private Integer id;
	private UserSyndic UserSyndic;
	private User User;
	private Integer idprop;
	private Date dateD;
	private Date dateF;
	@Inject
	UserSyndicHome UserSyndichome;

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (has(id)) {
			User = new UserHome().findById(id);
			UserSyndic = UserSyndichome.findById(id);
			UserSyndic.setUser(User);
			idprop = UserSyndichome.findByIdSyndicProp(User.getIdUser()).getPropriete().getIdPropriete();
			dateD= UserSyndichome.findByIdSyndicProp(User.getIdUser()).getDateDSyndProp();
			dateF= UserSyndichome.findByIdSyndicProp(User.getIdUser()).getDateFSyndProp();
			
		} else {
			UserSyndic = new UserSyndic();
			User = new User();
			UserSyndic.setUser(User);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserSyndic getUserSyndic() {
		return UserSyndic;
	}

	public void setUserSyndic(UserSyndic UserSyndic) {
		this.UserSyndic = UserSyndic;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		this.User = user;
	}

	public Integer getIdprop() {
		return idprop;
	}

	public void setIdprop(Integer idprop) {
		this.idprop = idprop;
	}

	public Date getDateD() {
		return dateD;
	}

	public void setDateD(Date dateD) {
		this.dateD = dateD;
	}

	public Date getDateF() {
		return dateF;
	}

	public void setDateF(Date dateF) {
		this.dateF = dateF;
	}

	public void save() {
		String msg;

		if (User.getIdUser() == null) {
			UserSyndichome.attachDirtyUser(User);
			UserSyndic.setUser(User);
			UserSyndichome.insert(UserSyndic);
			SyndicProprieteId id = new SyndicProprieteId(User.getIdUser(), idprop);
			SyndicPropriete sp = new SyndicPropriete(id, new ProprieteHome().findById(idprop), UserSyndic);
			sp.setDateDSyndProp(dateD);
			sp.setDateFSyndProp(dateF);
			UserSyndichome.attachDirtySyndicProp(sp);
			msg = "UserSyndic <b>" + User.getNomUser() + "</b> created successfully";
		} else {
			UserSyndichome.update(UserSyndic);
			msg = "UserSyndic <b>" + User.getNomUser() + "</b> updated successfully";
		}
		addDetailMessage(msg);
	}

	public void clear() {
		UserSyndic = new UserSyndic();
		User = new User();
	}

	public boolean isNew() {
		return UserSyndic == null;
	}

}
