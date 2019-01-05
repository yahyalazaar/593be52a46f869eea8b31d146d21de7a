package controllers;
// Generated 1 ao�t 2018 09:52:45 by Hibernate Tools 5.3.0.Beta2

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate.util.HibernateUtil;
import model.Ville;

/**
 * Home object for domain model class Ville.
 * 
 * @see ma.mem.beans.Ville
 * @author Hibernate Tools
 */
@ApplicationScoped
public class VilleHome {


	public void attachDirty(Ville instance) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(instance);
			if (!tx.wasCommitted()) {
				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public Ville findById(long id) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		Ville instance = null;
		try {
			tx = session.beginTransaction();
			instance = (Ville) session.get(Ville.class, id);
			if (!tx.wasCommitted()) {
				tx.commit();
			}
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

	@Produces
	@SuppressWarnings("unchecked")
	public List<Ville> getAll() {

		List<Ville> results = new ArrayList<Ville>();
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createCriteria(Ville.class).list();
			if (!tx.wasCommitted()) {
				tx.commit();
			}
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
