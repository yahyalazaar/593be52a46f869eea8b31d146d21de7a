package services;
// Generated 1 ao�t 2018 09:52:45 by Hibernate Tools 5.3.0.Beta2

import static com.github.adminfaces.template.util.Assert.has;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.omnifaces.cdi.ViewScoped;

import HibernateUtil.HibernateUtil;
import infra.model.Filter;
import infra.model.SortOrder;
import model.Assemble;
import model.SyndicPropriete;
import model.User;
import model.VoteCopropProp;

@Named
@ViewScoped
public class AssembleHome implements Serializable {
	@Inject
	List<Assemble> allAssembles;

	public List<Assemble> paginate(Filter<Assemble> filter) {
		List<Assemble> pagedAssembles = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedAssembles = allAssembles.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getPropriete().getNomPropriete().compareTo(c2.getPropriete().getNomPropriete());
				} else {
					return (c2.getPropriete().getNomPropriete()).compareTo(c1.getPropriete().getNomPropriete());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedAssembles = pagedAssembles.subList(filter.getFirst(),
					page > allAssembles.size() ? allAssembles.size() : page);
			return pagedAssembles;
		}

		List<Predicate<Assemble>> predicates = configFilter(filter);

		List<Assemble> pagedList = allAssembles.stream()
				.filter(predicates.stream().reduce(Predicate::or).orElse(t -> true)).collect(Collectors.toList());

		if (page < pagedList.size()) {
			pagedList = pagedList.subList(filter.getFirst(), page);
		}

		if (has(filter.getSortField())) {
			pagedList = pagedList.stream().sorted((c1, c2) -> {
				boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
				if (asc) {
					return c1.getPropriete().getNomPropriete().compareTo(c2.getPropriete().getNomPropriete());
				} else {
					return c2.getPropriete().getNomPropriete().compareTo(c1.getPropriete().getNomPropriete());
				}
			}).collect(Collectors.toList());
		}
		return pagedList;
	}

	private List<Predicate<Assemble>> configFilter(Filter<Assemble> filter) {
		List<Predicate<Assemble>> predicates = new ArrayList<>();
		if (filter.hasParam("Propriete")) {
			Predicate<Assemble> idPredicate = (Assemble c) -> c.getPropriete().getNomPropriete()
					.equals(filter.getParam("Propriete"));
			predicates.add(idPredicate);
		}

//		if (has(filter.getEntity())) {
//			Assemble filterEntity = filter.getEntity();
//
//			if (has(filterEntity.getUser().getNomUser())) {
//				Predicate<Assemble> namePredicate = (Assemble c) -> c.getUser().getNomUser()
//						.toLowerCase().contains(filterEntity.getUser().getNomUser().toLowerCase());
//				predicates.add(namePredicate);
//			}
//		}
		return predicates;
	}

	public void attachDirtyUser(User instance) {
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

	public void attachDirtyAssemble(Assemble instance) {
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

	public void attachDirtyAssembleProp(SyndicPropriete instance) {
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

	public Assemble findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		Assemble instance = null;
		try {
			tx = session.beginTransaction();
			instance = (Assemble) session.get(Assemble.class, id);

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

	public long count(Filter<Assemble> filter) {
		return allAssembles.stream().filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true))
				.count();
	}

	public List<Assemble> getAllAssemble() {

		List<Assemble> results = new ArrayList<Assemble>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Assemble").list();
			for (Assemble a : results) {
				Hibernate.initialize(a.getPropriete());
			}

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

	public static List<Assemble> findByName(String query) {
		List<Assemble> results = new ArrayList<Assemble>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Assemble where nomAssemble = '" + query + "'").list();

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

	

	public void insert(Assemble Assemble) {
		// beforeInsert(Assemble);
		attachDirtyAssemble(Assemble);
		allAssembles.add(Assemble);
	}


	public void update(Assemble Assemble) {

		int ind = 0;
		for (Assemble v : allAssembles) {
			if (v.getIdAssemble() == Assemble.getIdAssemble()) {
				allAssembles.set(ind, Assemble);
			}
			ind++;
		}
		attachDirtyAssemble(Assemble);

	}
}
