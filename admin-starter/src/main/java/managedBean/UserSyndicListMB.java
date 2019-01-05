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
import model.UserSyndic;
import services.UserSyndicHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class UserSyndicListMB implements Serializable {

	@Inject
	UserSyndicHome UserSyndichome;

	Integer idUserSyndic;

	LazyDataModel<UserSyndic> UserSyndics;

	Filter<UserSyndic> filter = new Filter<>(new UserSyndic());

	List<UserSyndic> selectedUserSyndics; // UserSyndics selected in checkbox column

	List<UserSyndic> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		UserSyndics = new LazyDataModel<UserSyndic>() {
			@Override
			public List<UserSyndic> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				infra.model.SortOrder order = null;
				if (sortOrder != null) {
					order = sortOrder.equals(SortOrder.ASCENDING) ? infra.model.SortOrder.ASCENDING
							: sortOrder.equals(SortOrder.DESCENDING) ? infra.model.SortOrder.DESCENDING
									: infra.model.SortOrder.UNSORTED;
				}
				filter.setFirst(first).setPageSize(pageSize).setSortField(sortField).setSortOrder(order)
						.setParams(filters);
				List<UserSyndic> list = UserSyndichome.paginate(filter);
				setRowCount((int) UserSyndichome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public UserSyndic getRowData(String key) {
				return UserSyndichome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return UserSyndichome.getAllUserSyndic().stream()
				.filter(c -> c.getUser().getNomUser().toLowerCase().contains(query.toLowerCase()))
				.map(UserSyndic -> UserSyndic.getUser().getNomUser()).collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<UserSyndic>(new UserSyndic());
	}

	public void findUserSyndicById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide UserSyndic ID to load");
		}
		selectedUserSyndics.add(UserSyndichome.findById(id));
	}

	public List<UserSyndic> getSelectedUserSyndics() {
		return selectedUserSyndics;
	}

	public List<UserSyndic> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<UserSyndic> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedUserSyndics(List<UserSyndic> selectedUserSyndics) {
		this.selectedUserSyndics = selectedUserSyndics;
	}

	public LazyDataModel<UserSyndic> getUserSyndics() {
		return UserSyndics;
	}

	public void setUserSyndics(LazyDataModel<UserSyndic> UserSyndics) {
		this.UserSyndics = UserSyndics;
	}

	public Filter<UserSyndic> getFilter() {
		return filter;
	}

	public void setFilter(Filter<UserSyndic> filter) {
		this.filter = filter;
	}

	public Integer getIdUserSyndic() {
		return idUserSyndic;
	}

	public void setIdUserSyndic(Integer id) {
		this.idUserSyndic = id;
	}
}
