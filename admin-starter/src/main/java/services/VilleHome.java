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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.omnifaces.cdi.ViewScoped;

import com.github.adminfaces.template.exception.BusinessException;

import HibernateUtil.HibernateUtil;
import infra.model.Filter;
import infra.model.SortOrder;
import model.Ville;

@Named
@ViewScoped
public class VilleHome implements Serializable {
	@Inject
	List<Ville> allVilles;

	public List<Ville> paginate(Filter<Ville> filter) {
		List<Ville> pagedVilles = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedVilles = allVilles.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getNomVille().compareTo(c2.getNomVille());
				} else {
					return (c2.getNomVille()).compareTo(c1.getNomVille());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedVilles = pagedVilles.subList(filter.getFirst(), page > allVilles.size() ? allVilles.size() : page);
			return pagedVilles;
		}

		List<Predicate<Ville>> predicates = configFilter(filter);

		List<Ville> pagedList = allVilles.stream().filter(predicates.stream().reduce(Predicate::or).orElse(t -> true))
				.collect(Collectors.toList());

		if (page < pagedList.size()) {
			pagedList = pagedList.subList(filter.getFirst(), page);
		}

		if (has(filter.getSortField())) {
			pagedList = pagedList.stream().sorted((c1, c2) -> {
				boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
				if (asc) {
					return c1.getNomVille().compareTo(c2.getNomVille());
				} else {
					return c2.getNomVille().compareTo(c1.getNomVille());
				}
			}).collect(Collectors.toList());
		}
		return pagedList;
	}

	private List<Predicate<Ville>> configFilter(Filter<Ville> filter) {
		List<Predicate<Ville>> predicates = new ArrayList<>();
		if (filter.hasParam("nomVille")) {
			Predicate<Ville> idPredicate = (Ville c) -> c.getNomVille().equals(filter.getParam("nomVille"));
			predicates.add(idPredicate);
		}

		if (has(filter.getEntity())) {
			Ville filterEntity = filter.getEntity();

			if (has(filterEntity.getNomVille())) {
				Predicate<Ville> namePredicate = (Ville c) -> c.getNomVille().toLowerCase()
						.contains(filterEntity.getNomVille().toLowerCase());
				predicates.add(namePredicate);
			}
		}
		return predicates;
	}

	public void attachDirty(Ville instance) {
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

	public Ville findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		Ville instance = null;
		try {
			tx = session.beginTransaction();
			instance = (Ville) session.get(Ville.class, id);

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

	public long count(Filter<Ville> filter) {
		return allVilles.stream().filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true)).count();
	}

	public List<Ville> getAll() {

		List<Ville> results = new ArrayList<Ville>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Ville").list();
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

	public static List<Ville> findByName(String query) {
		List<Ville> results = new ArrayList<Ville>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Ville where nomVille = '" + query + "'").list();

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

	public void insert(Ville ville) {
		beforeInsert(ville);
		attachDirty(ville);
		allVilles.add(ville);
	}

	public void beforeInsert(Ville Ville) {

		if (!Ville.hasNomVille()) {
			throw new BusinessException("Ville name cannot be empty");
		}

	}

	public void update(Ville ville) {

		int ind = 0;
		for (Ville v : allVilles) {
			if (v.getIdVille() == ville.getIdVille()) {
				allVilles.set(ind, ville);
			}
			ind++;
		}
		attachDirty(ville);

	}
}
