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

import com.github.adminfaces.template.exception.BusinessException;

import HibernateUtil.HibernateUtil;
import infra.model.Filter;
import infra.model.SortOrder;
import model.Propriete;

@Named
@ViewScoped
public class ProprieteHome implements Serializable {
	@Inject
	List<Propriete> allProprietes;

	public List<Propriete> paginate(Filter<Propriete> filter) {
		List<Propriete> pagedProprietes = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedProprietes = allProprietes.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getNomPropriete().compareTo(c2.getNomPropriete());
				} else {
					return (c2.getNomPropriete()).compareTo(c1.getNomPropriete());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedProprietes = pagedProprietes.subList(filter.getFirst(),
					page > allProprietes.size() ? allProprietes.size() : page);
			return pagedProprietes;
		}

		List<Predicate<Propriete>> predicates = configFilter(filter);

		List<Propriete> pagedList = allProprietes.stream()
				.filter(predicates.stream().reduce(Predicate::or).orElse(t -> true)).collect(Collectors.toList());

		if (page < pagedList.size()) {
			pagedList = pagedList.subList(filter.getFirst(), page);
		}

		if (has(filter.getSortField())) {
			pagedList = pagedList.stream().sorted((c1, c2) -> {
				boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
				if (asc) {
					return c1.getNomPropriete().compareTo(c2.getNomPropriete());
				} else {
					return c2.getNomPropriete().compareTo(c1.getNomPropriete());
				}
			}).collect(Collectors.toList());
		}
		return pagedList;
	}

	private List<Predicate<Propriete>> configFilter(Filter<Propriete> filter) {
		List<Predicate<Propriete>> predicates = new ArrayList<>();
		if (filter.hasParam("nomPropriete")) {
			Predicate<Propriete> idPredicate = (Propriete c) -> c.getNomPropriete()
					.equals(filter.getParam("nomPropriete"));
			predicates.add(idPredicate);
		}

		if (has(filter.getEntity())) {
			Propriete filterEntity = filter.getEntity();

			if (has(filterEntity.getNomPropriete())) {
				Predicate<Propriete> namePredicate = (Propriete c) -> c.getNomPropriete().toLowerCase()
						.contains(filterEntity.getNomPropriete().toLowerCase());
				predicates.add(namePredicate);
			}
		}
		return predicates;
	}

	public void attachDirty(Propriete instance) {
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

	public Propriete findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		Propriete instance = null;
		try {
			tx = session.beginTransaction();
			instance = (Propriete) session.get(Propriete.class, id);

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

	public long count(Filter<Propriete> filter) {
		return allProprietes.stream().filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true))
				.count();
	}

	public List<Propriete> getAll() {

		List<Propriete> results = new ArrayList<Propriete>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Propriete").list();
			for (Propriete p : results) {
				Hibernate.initialize(p.getVille());
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

	public static List<Propriete> findByName(String query) {
		List<Propriete> results = new ArrayList<Propriete>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Propriete where nomPropriete = '" + query + "'").list();

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

	public void insert(Propriete Propriete) {
		beforeInsert(Propriete);
		attachDirty(Propriete);
		allProprietes.add(Propriete);
	}

	public void beforeInsert(Propriete Propriete) {

		if (!Propriete.hasNomPropriete()) {
			throw new BusinessException("Propriete name cannot be empty");
		}
		if (!Propriete.hasAdressePropriete()) {
			throw new BusinessException("Propriete adress cannot be empty");
		}
		if (!Propriete.hasVillePropriete()) {
			throw new BusinessException("Propriete ville cannot be empty");
		}

	}

	public void update(Propriete Propriete) {

		int ind = 0;
		for (Propriete v : allProprietes) {
			if (v.getIdPropriete() == Propriete.getIdPropriete()) {
				allProprietes.set(ind, Propriete);
			}
			ind++;
		}
		attachDirty(Propriete);

	}
}
