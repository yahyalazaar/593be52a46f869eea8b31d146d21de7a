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
import model.User;
import model.UserCoproprietaire;

@Named
@ViewScoped
public class UserCopHome implements Serializable {
	@Inject
	List<UserCoproprietaire> allUserCoproprietaires;

	public List<UserCoproprietaire> paginate(Filter<UserCoproprietaire> filter) {
		List<UserCoproprietaire> pagedUserCoproprietaires = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedUserCoproprietaires = allUserCoproprietaires.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getUser().getNomUser().compareTo(c2.getUser().getNomUser());
				} else {
					return (c2.getUser().getNomUser()).compareTo(c1.getUser().getNomUser());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedUserCoproprietaires = pagedUserCoproprietaires.subList(filter.getFirst(),
					page > allUserCoproprietaires.size() ? allUserCoproprietaires.size() : page);
			return pagedUserCoproprietaires;
		}

		List<Predicate<UserCoproprietaire>> predicates = configFilter(filter);

		List<UserCoproprietaire> pagedList = allUserCoproprietaires.stream()
				.filter(predicates.stream().reduce(Predicate::or).orElse(t -> true)).collect(Collectors.toList());

		if (page < pagedList.size()) {
			pagedList = pagedList.subList(filter.getFirst(), page);
		}

		if (has(filter.getSortField())) {
			pagedList = pagedList.stream().sorted((c1, c2) -> {
				boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
				if (asc) {
					return c1.getUser().getNomUser().compareTo(c2.getUser().getNomUser());
				} else {
					return c2.getUser().getNomUser().compareTo(c1.getUser().getNomUser());
				}
			}).collect(Collectors.toList());
		}
		return pagedList;
	}

	private List<Predicate<UserCoproprietaire>> configFilter(Filter<UserCoproprietaire> filter) {
		List<Predicate<UserCoproprietaire>> predicates = new ArrayList<>();
		if (filter.hasParam("User")) {
			Predicate<UserCoproprietaire> idPredicate = (UserCoproprietaire c) -> c.getUser().getNomUser()
					.equals(filter.getParam("User"));
			predicates.add(idPredicate);
		}

//		if (has(filter.getEntity())) {
//			UserCoproprietaire filterEntity = filter.getEntity();
//
//			if (has(filterEntity.getUser().getNomUser())) {
//				Predicate<UserCoproprietaire> namePredicate = (UserCoproprietaire c) -> c.getUser().getNomUser()
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

	public void attachDirtyCop(UserCoproprietaire instance) {
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

	public UserCoproprietaire findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		UserCoproprietaire instance = null;
		try {
			tx = session.beginTransaction();
			instance = (UserCoproprietaire) session.get(UserCoproprietaire.class, id);

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

	public long count(Filter<UserCoproprietaire> filter) {
		return allUserCoproprietaires.stream()
				.filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true)).count();
	}

	public List<UserCoproprietaire> getAllUserCoproprietaire() {

		List<UserCoproprietaire> results = new ArrayList<UserCoproprietaire>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from UserCoproprietaire").list();

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

	public static List<UserCoproprietaire> findByName(String query) {
		List<UserCoproprietaire> results = new ArrayList<UserCoproprietaire>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from UserCoproprietaire where nomUserCoproprietaire = '" + query + "'")
					.list();

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

	public void insert(UserCoproprietaire UserCoproprietaire) {
		beforeInsert(UserCoproprietaire);
		attachDirtyCop(UserCoproprietaire);
		allUserCoproprietaires.add(UserCoproprietaire);
	}

	public void beforeInsert(UserCoproprietaire User) {
		if (!User.getUser().hasNomUser()) {
			throw new BusinessException("User<b> nom </b>cannot be empty");
		}
		if (!User.getUser().hasPrenomUser()) {
			throw new BusinessException("User<b> prenom </b>cannot be empty");
		}
		if (!User.getUser().hasLoginUser()) {
			throw new BusinessException("User<b> login </b>cannot be empty");
		}
		if (!User.getUser().hasMdpUser()) {
			throw new BusinessException("User<b> mot de passe </b>cannot be empty");
		}
	}

	public void update(UserCoproprietaire UserCoproprietaire) {

		int ind = 0;
		for (UserCoproprietaire v : allUserCoproprietaires) {
			if (v.getUser().getIdUser() == UserCoproprietaire.getUser().getIdUser()
					&& v.getPropriete().getIdPropriete() == UserCoproprietaire.getPropriete().getIdPropriete()) {
				allUserCoproprietaires.set(ind, UserCoproprietaire);
			}
			ind++;
		}
		attachDirtyUser(UserCoproprietaire.getUser());

	}
}
