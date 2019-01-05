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
import model.UserCoproprietaire;
import services.UserCopHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class UserCopListMB implements Serializable {

	@Inject
	UserCopHome UserCoproprietairehome;

	Integer idUserCoproprietaire;

	LazyDataModel<UserCoproprietaire> UserCoproprietaires;

	Filter<UserCoproprietaire> filter = new Filter<>(new UserCoproprietaire());

	List<UserCoproprietaire> selectedUserCoproprietaires; // UserCoproprietaires selected in checkbox column

	List<UserCoproprietaire> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		UserCoproprietaires = new LazyDataModel<UserCoproprietaire>() {
			@Override
			public List<UserCoproprietaire> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				infra.model.SortOrder order = null;
				if (sortOrder != null) {
					order = sortOrder.equals(SortOrder.ASCENDING) ? infra.model.SortOrder.ASCENDING
							: sortOrder.equals(SortOrder.DESCENDING) ? infra.model.SortOrder.DESCENDING
									: infra.model.SortOrder.UNSORTED;
				}
				filter.setFirst(first).setPageSize(pageSize).setSortField(sortField).setSortOrder(order)
						.setParams(filters);
				List<UserCoproprietaire> list = UserCoproprietairehome.paginate(filter);
				setRowCount((int) UserCoproprietairehome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public UserCoproprietaire getRowData(String key) {
				return UserCoproprietairehome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return UserCoproprietairehome.getAllUserCoproprietaire().stream()
				.filter(c -> c.getUser().getNomUser().toLowerCase().contains(query.toLowerCase()))
				.map(UserCoproprietaire -> UserCoproprietaire.getUser().getNomUser()).collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<UserCoproprietaire>(new UserCoproprietaire());
	}

	public void findUserCoproprietaireById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide UserCoproprietaire ID to load");
		}
		selectedUserCoproprietaires.add(UserCoproprietairehome.findById(id));
	}

	public List<UserCoproprietaire> getSelectedUserCoproprietaires() {
		return selectedUserCoproprietaires;
	}

	public List<UserCoproprietaire> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<UserCoproprietaire> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedUserCoproprietaires(List<UserCoproprietaire> selectedUserCoproprietaires) {
		this.selectedUserCoproprietaires = selectedUserCoproprietaires;
	}

	public LazyDataModel<UserCoproprietaire> getUserCoproprietaires() {
		return UserCoproprietaires;
	}

	public void setUserCoproprietaires(LazyDataModel<UserCoproprietaire> UserCoproprietaires) {
		this.UserCoproprietaires = UserCoproprietaires;
	}

	public Filter<UserCoproprietaire> getFilter() {
		return filter;
	}

	public void setFilter(Filter<UserCoproprietaire> filter) {
		this.filter = filter;
	}

	public Integer getIdUserCoproprietaire() {
		return idUserCoproprietaire;
	}

	public void setIdUserCoproprietaire(Integer id) {
		this.idUserCoproprietaire = id;
	}
}
