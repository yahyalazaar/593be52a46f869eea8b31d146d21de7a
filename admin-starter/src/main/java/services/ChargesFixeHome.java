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
import model.ChargesFixe;

@Named
@ViewScoped
public class ChargesFixeHome implements Serializable {
	
	public void attachDirty(ChargesFixe instance) {
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

	public ChargesFixe findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		ChargesFixe instance = null;
		try {
			tx = session.beginTransaction();
			instance = (ChargesFixe) session.get(ChargesFixe.class, id);

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

	public List<ChargesFixe> getAll() {

		List<ChargesFixe> results = new ArrayList<ChargesFixe>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from ChargesFixe").list();

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

	public static List<ChargesFixe> findByName(String query) {
		List<ChargesFixe> results = new ArrayList<ChargesFixe>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("coChargesFixe").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from ChargesFixe where nomChargesFixe = '" + query + "'").list();

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

}
