package security;

import com.github.adminfaces.template.session.AdminSession;

import HibernateUtil.HibernateUtil;
import model.*;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import static utils.Utils.addDetailMessage;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

	private String currentUser;
	private String email;
	private String password;
	private boolean remember;

	public void login() throws IOException {
		currentUser = email;
		addDetailMessage("Logged in successfully as <b>" + email + "</b>");
//		Configuration configuration = new Configuration().configure("./hibernate.cfg.xml");
//		configuration
//				.setProperty("hibernate.connection.url",
//						"jdbc:mysql://localhost:3306/" + email
//								+ "?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8")
//				.setProperty("hibernate.hbm2ddl.auto", "update")
//				.setProperty("hibernate.current_session_context_class", "thread").addAnnotatedClass(Assemble.class)
//				.addAnnotatedClass(BienCommun.class).addAnnotatedClass(Charges.class)
//				.addAnnotatedClass(ChargesFixe.class).addAnnotatedClass(ChargesTemporaire.class)
//				.addAnnotatedClass(Cotisation.class).addAnnotatedClass(CotisationAnnuel.class)
//				.addAnnotatedClass(CotisationMensuel.class).addAnnotatedClass(Fournisseurs.class)
//				.addAnnotatedClass(FrsEntreprise.class).addAnnotatedClass(FrsParticulier.class)
//				.addAnnotatedClass(Location.class).addAnnotatedClass(LocationBien.class)
//				.addAnnotatedClass(Notification.class).addAnnotatedClass(NotificationUser.class)
//				.addAnnotatedClass(Propriete.class).addAnnotatedClass(ProprieteFrs.class)
//				.addAnnotatedClass(Rapport.class).addAnnotatedClass(SyndicPropriete.class).addAnnotatedClass(User.class)
//				.addAnnotatedClass(UserAdmin.class).addAnnotatedClass(UserCoproprietaire.class)
//				.addAnnotatedClass(UserSyndic.class).addAnnotatedClass(Ville.class)
//				.addAnnotatedClass(VoteCopropProp.class);
//		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//				.applySettings(configuration.getProperties()).build();
//
//		HibernateUtil.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Faces.getExternalContext().getFlash().setKeepMessages(true);
		Faces.redirect("index.xhtml");
	}

	@Override
	public boolean isLoggedIn() {

		return currentUser != null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
