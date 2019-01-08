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

import HibernateUtil.HibernateUtil;
import infra.model.Filter;
import infra.model.SortOrder;
import model.Charges;

@Named
@ViewScoped
public class ChargesHome implements Serializable {
	@Inject
	List<Charges> allChargess;

	public List<Charges> paginate(Filter<Charges> filter) {
		List<Charges> pagedChargess = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedChargess = allChargess.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getNomCharges().compareTo(c2.getNomCharges());
				} else {
					return (c2.getNomCharges()).compareTo(c1.getNomCharges());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedChargess = pagedChargess.subList(filter.getFirst(),
					page > allChargess.size() ? allChargess.size() : page);
			return pagedChargess;
		}

		List<Predicate<Charges>> predicates = configFilter(filter);

		List<Charges> pagedList = allChargess.stream()
				.filter(predicates.stream().reduce(Predicate::or).orElse(t -> true)).collect(Collectors.toList());

		if (page < pagedList.size()) {
			pagedList = pagedList.subList(filter.getFirst(), page);
		}

		if (has(filter.getSortField())) {
			pagedList = pagedList.stream().sorted((c1, c2) -> {
				boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
				if (asc) {
					return c1.getNomCharges().compareTo(c2.getNomCharges());
				} else {
					return c2.getNomCharges().compareTo(c1.getNomCharges());
				}
			}).collect(Collectors.toList());
		}
		return pagedList;
	}

	private List<Predicate<Charges>> configFilter(Filter<Charges> filter) {
		List<Predicate<Charges>> predicates = new ArrayList<>();
		if (filter.hasParam("nomCharges")) {
			Predicate<Charges> idPredicate = (Charges c) -> c.getNomCharges()
					.equals(filter.getParam("nomCharges"));
			predicates.add(idPredicate);
		}

		if (has(filter.getEntity())) {
			Charges filterEntity = filter.getEntity();

			if (has(filterEntity.getNomCharges())) {
				Predicate<Charges> namePredicate = (Charges c) -> c.getNomCharges().toLowerCase()
						.contains(filterEntity.getNomCharges().toLowerCase());
				predicates.add(namePredicate);
			}
		}
		return predicates;
	}

	public void attachDirty(Charges instance) {
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

	public Charges findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		Charges instance = null;
		try {
			tx = session.beginTransaction();
			instance = (Charges) session.get(Charges.class, id);

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

	public long count(Filter<Charges> filter) {
		return allChargess.stream().filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true))
				.count();
	}

	public List<Charges> getAll() {

		List<Charges> results = new ArrayList<Charges>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Charges").list();
		

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

	public static List<Charges> findByName(String query) {
		List<Charges> results = new ArrayList<Charges>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("coCharges").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from Charges where nomCharges = '" + query + "'").list();

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

	public void insert(Charges Charges) {
		beforeInsert(Charges);
		attachDirty(Charges);
		allChargess.add(Charges);
	}

	public void beforeInsert(Charges Charges) {

//		if (!Charges.hasNomCharges()) {
//			throw new BusinessException("Charges name cannot be empty");
//		}
//		if (!Charges.hasAdresseCharges()) {
//			throw new BusinessException("Charges adress cannot be empty");
//		}
//		if (!Charges.hasVilleCharges()) {
//			throw new BusinessException("Charges ville cannot be empty");
//		}

	}

	public void update(Charges Charges) {

		int ind = 0;
		for (Charges v : allChargess) {
			if (v.getIdCharges() == Charges.getIdCharges()) {
				allChargess.set(ind, Charges);
			}
			ind++;
		}
		attachDirty(Charges);

	}
}
