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
import model.UserAdmin;

@Named
@ViewScoped
public class UserHome implements Serializable {
	@Inject
	List<User> allUsers;

	public List<User> paginate(Filter<User> filter) {
		List<User> pagedUsers = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedUsers = allUsers.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getNomUser().compareTo(c2.getNomUser());
				} else {
					return (c2.getNomUser()).compareTo(c1.getNomUser());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedUsers = pagedUsers.subList(filter.getFirst(), page > allUsers.size() ? allUsers.size() : page);
			return pagedUsers;
		}

		List<Predicate<User>> predicates = configFilter(filter);

		List<User> pagedList = allUsers.stream().filter(predicates.stream().reduce(Predicate::or).orElse(t -> true))
				.collect(Collectors.toList());

		if (page < pagedList.size()) {
			pagedList = pagedList.subList(filter.getFirst(), page);
		}

		if (has(filter.getSortField())) {
			pagedList = pagedList.stream().sorted((c1, c2) -> {
				boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
				if (asc) {
					return c1.getNomUser().compareTo(c2.getNomUser());
				} else {
					return c2.getNomUser().compareTo(c1.getNomUser());
				}
			}).collect(Collectors.toList());
		}
		return pagedList;
	}

	private List<Predicate<User>> configFilter(Filter<User> filter) {
		List<Predicate<User>> predicates = new ArrayList<>();
		if (filter.hasParam("nomUser")) {
			Predicate<User> idPredicate = (User c) -> c.getNomUser().equals(filter.getParam("nomUser"));
			predicates.add(idPredicate);
		}

		if (has(filter.getEntity())) {
			User filterEntity = filter.getEntity();

			if (has(filterEntity.getNomUser())) {
				Predicate<User> namePredicate = (User c) -> c.getNomUser().toLowerCase()
						.contains(filterEntity.getNomUser().toLowerCase());
				predicates.add(namePredicate);
			}
		}
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

	public void attachDirtyAdmin(UserAdmin instance) {
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

	public User findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		User instance = null;
		try {
			tx = session.beginTransaction();
			instance = (User) session.get(User.class, id);

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

	public long count(Filter<User> filter) {
		return allUsers.stream().filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true)).count();
	}

	public List<User> getAllUser() {

		List<User> results = new ArrayList<User>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from User").list();

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

	public static List<User> findByName(String query) {
		List<User> results = new ArrayList<User>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from User where nomUser = '" + query + "'").list();

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

	public void insert(User User) {
		beforeInsert(User);
		attachDirtyUser(User);
		allUsers.add(User);
	}

	public void beforeInsert(User User) {

		if (!User.hasNomUser()) {
			throw new BusinessException("User<b> nom </b>cannot be empty");
		}
		if (!User.hasPrenomUser()) {
			throw new BusinessException("User<b> prenom </b>cannot be empty");
		}
		if (!User.hasLoginUser()) {
			throw new BusinessException("User<b> login </b>cannot be empty");
		}
		if (!User.hasMdpUser()) {
			throw new BusinessException("User<b> mot de passe </b>cannot be empty");
		}

	}

	public void update(User User) {

		int ind = 0;
		for (User v : allUsers) {
			if (v.getIdUser() == User.getIdUser()) {
				allUsers.set(ind, User);
			}
			ind++;
		}
		attachDirtyUser(User);

	}
}
