package services;
// Generated 1 ao�t 2018 09:52:45 by Hibernate Tools 5.3.0.Beta2

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.omnifaces.cdi.ViewScoped;

import HibernateUtil.HibernateUtil;
import model.ChargesTemporaire;

@Named
@ViewScoped
public class ChargesTemporaireHome implements Serializable {
	
	public void attachDirty(ChargesTemporaire instance) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(instance);

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public ChargesTemporaire findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		ChargesTemporaire instance = null;
		try {
			tx = session.beginTransaction();
			instance = (ChargesTemporaire) session.get(ChargesTemporaire.class, id);

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return instance;
	}


	public List<ChargesTemporaire> getAll() {

		List<ChargesTemporaire> results = new ArrayList<ChargesTemporaire>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from ChargesTemporaire").list();

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public static List<ChargesTemporaire> findByName(String query) {
		List<ChargesTemporaire> results = new ArrayList<ChargesTemporaire>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("coChargesTemporaire").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from ChargesTemporaire where nomChargesTemporaire = '" + query + "'").list();

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public void insert(ChargesTemporaire ChargesTemporaire) {
		beforeInsert(ChargesTemporaire);
		attachDirty(ChargesTemporaire);
	}

	public void beforeInsert(ChargesTemporaire ChargesTemporaire) {

//		if (!ChargesTemporaire.hasNomChargesTemporaire()) {
//			throw new BusinessException("ChargesTemporaire name cannot be empty");
//		}
//		if (!ChargesTemporaire.hasAdresseChargesTemporaire()) {
//			throw new BusinessException("ChargesTemporaire adress cannot be empty");
//		}
//		if (!ChargesTemporaire.hasVilleChargesTemporaire()) {
//			throw new BusinessException("ChargesTemporaire ville cannot be empty");
//		}

	}

}
