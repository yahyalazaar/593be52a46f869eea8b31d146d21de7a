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
import model.SyndicPropriete;
import model.User;
import model.UserSyndic;

@Named
@ViewScoped
public class UserSyndicHome implements Serializable {
	@Inject
	List<UserSyndic> allUserSyndics;

	public List<UserSyndic> paginate(Filter<UserSyndic> filter) {
		List<UserSyndic> pagedUserSyndics = new ArrayList<>();
		if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
			pagedUserSyndics = allUserSyndics.stream().sorted((c1, c2) -> {
				if (filter.getSortOrder().isAscending()) {
					return c1.getUser().getNomUser().compareTo(c2.getUser().getNomUser());
				} else {
					return (c2.getUser().getNomUser()).compareTo(c1.getUser().getNomUser());
				}
			}).collect(Collectors.toList());
		}

		int page = filter.getFirst() + filter.getPageSize();
		if (filter.getParams().isEmpty()) {
			pagedUserSyndics = pagedUserSyndics.subList(filter.getFirst(),
					page > allUserSyndics.size() ? allUserSyndics.size() : page);
			return pagedUserSyndics;
		}

		List<Predicate<UserSyndic>> predicates = configFilter(filter);

		List<UserSyndic> pagedList = allUserSyndics.stream()
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

	private List<Predicate<UserSyndic>> configFilter(Filter<UserSyndic> filter) {
		List<Predicate<UserSyndic>> predicates = new ArrayList<>();
		if (filter.hasParam("User")) {
			Predicate<UserSyndic> idPredicate = (UserSyndic c) -> c.getUser().getNomUser()
					.equals(filter.getParam("User"));
			predicates.add(idPredicate);
		}

//		if (has(filter.getEntity())) {
//			UserSyndic filterEntity = filter.getEntity();
//
//			if (has(filterEntity.getUser().getNomUser())) {
//				Predicate<UserSyndic> namePredicate = (UserSyndic c) -> c.getUser().getNomUser()
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

	public void attachDirtySyndic(UserSyndic instance) {
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

	public void attachDirtySyndicProp(SyndicPropriete instance) {
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

	public SyndicPropriete findByIdSyndicProp(Integer id) {

		SyndicPropriete instance = null;
		for (SyndicPropriete sp : getAllUserSyndicProp()) {
			if (sp.getUserSyndic().getIdUser() == id)
				instance = sp;
		}

		return instance;
	}

	public UserSyndic findById(Integer id) {
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		UserSyndic instance = null;
		try {
			tx = session.beginTransaction();
			instance = (UserSyndic) session.get(UserSyndic.class, id);

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

	public long count(Filter<UserSyndic> filter) {
		return allUserSyndics.stream().filter(configFilter(filter).stream().reduce(Predicate::or).orElse(t -> true))
				.count();
	}

	public List<UserSyndic> getAllUserSyndic() {

		List<UserSyndic> results = new ArrayList<UserSyndic>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from UserSyndic").list();

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

	public List<SyndicPropriete> getAllUserSyndicProp() {

		List<SyndicPropriete> results = new ArrayList<SyndicPropriete>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from SyndicPropriete").list();

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

	public static List<UserSyndic> findByName(String query) {
		List<UserSyndic> results = new ArrayList<UserSyndic>();
		SessionFactory factory = HibernateUtil.factory();
		Session session = factory.withOptions().tenantIdentifier("copropriete").openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			results = session.createQuery("from UserSyndic where nomUserSyndic = '" + query + "'").list();

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

	public void insert(UserSyndic UserSyndic) {
		beforeInsert(UserSyndic);
		attachDirtySyndic(UserSyndic);
		allUserSyndics.add(UserSyndic);
	}

	public void beforeInsert(UserSyndic User) {
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

	public void update(UserSyndic UserSyndic) {

		int ind = 0;
		for (UserSyndic v : allUserSyndics) {
			if (v.getUser().getIdUser() == UserSyndic.getUser().getIdUser()) {
				allUserSyndics.set(ind, UserSyndic);
			}
			ind++;
		}
		attachDirtyUser(UserSyndic.getUser());

	}
}
