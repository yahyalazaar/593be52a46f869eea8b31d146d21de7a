package managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.github.adminfaces.template.exception.BusinessException;

import infra.model.Filter;
import model.User;
import services.UserHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class UserListMB implements Serializable {

	@Inject
	UserHome Userhome;

	Integer idUser;

	LazyDataModel<User> Users;

	Filter<User> filter = new Filter<>(new User());

	List<User> selectedUsers; // Users selected in checkbox column

	List<User> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		Users = new LazyDataModel<User>() {
			@Override
			public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				infra.model.SortOrder order = null;
				if (sortOrder != null) {
					order = sortOrder.equals(SortOrder.ASCENDING)
							? infra.model.SortOrder.ASCENDING
							: sortOrder.equals(SortOrder.DESCENDING)
									? infra.model.SortOrder.DESCENDING
									: infra.model.SortOrder.UNSORTED;
				}
				filter.setFirst(first).setPageSize(pageSize).setSortField(sortField).setSortOrder(order)
						.setParams(filters);
				List<User> list = Userhome.paginate(filter);
				setRowCount((int) Userhome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public User getRowData(String key) {
				return Userhome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return Userhome.getAllUser().stream().filter(c -> c.getNomUser()
                .toLowerCase().contains(query.toLowerCase()))
                .map(User::getNomUser)
                .collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<User>(new User());
	}

	public void findUserById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide User ID to load");
		}
		selectedUsers.add(Userhome.findById(id));
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public List<User> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<User> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public LazyDataModel<User> getUsers() {
		return Users;
	}

	public void setUsers(LazyDataModel<User> Users) {
		this.Users = Users;
	}

	public Filter<User> getFilter() {
		return filter;
	}

	public void setFilter(Filter<User> filter) {
		this.filter = filter;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer id) {
		this.idUser = id;
	}
}
