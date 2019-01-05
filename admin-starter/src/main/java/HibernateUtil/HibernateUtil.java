package HibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.Assemble;
import model.BienCommun;
import model.Charges;
import model.ChargesFixe;
import model.ChargesTemporaire;
import model.Cotisation;
import model.CotisationAnnuel;
import model.CotisationMensuel;
import model.Fournisseurs;
import model.FrsEntreprise;
import model.FrsParticulier;
import model.Location;
import model.LocationBien;
import model.Notification;
import model.NotificationUser;
import model.Propriete;
import model.ProprieteFrs;
import model.Rapport;
import model.SyndicPropriete;
import model.User;
import model.UserAdmin;
import model.UserCoproprietaire;
import model.UserSyndic;
import model.Ville;
import model.VoteCopropProp;

public class HibernateUtil {

	private static SessionFactory factory = build();

	private static SessionFactory build() {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(Assemble.class)
				.addAnnotatedClass(BienCommun.class).addAnnotatedClass(Charges.class)
				.addAnnotatedClass(ChargesFixe.class).addAnnotatedClass(ChargesTemporaire.class)
				.addAnnotatedClass(Cotisation.class).addAnnotatedClass(CotisationAnnuel.class)
				.addAnnotatedClass(CotisationMensuel.class).addAnnotatedClass(Fournisseurs.class)
				.addAnnotatedClass(FrsEntreprise.class).addAnnotatedClass(FrsParticulier.class)
				.addAnnotatedClass(Location.class).addAnnotatedClass(LocationBien.class)
				.addAnnotatedClass(Notification.class).addAnnotatedClass(NotificationUser.class)
				.addAnnotatedClass(Propriete.class).addAnnotatedClass(ProprieteFrs.class)
				.addAnnotatedClass(Rapport.class).addAnnotatedClass(SyndicPropriete.class).addAnnotatedClass(User.class)
				.addAnnotatedClass(UserAdmin.class).addAnnotatedClass(UserCoproprietaire.class)
				.addAnnotatedClass(UserSyndic.class).addAnnotatedClass(Ville.class)
				.addAnnotatedClass(VoteCopropProp.class);
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
		serviceRegistryBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory factory() {
		return factory;
	}
}