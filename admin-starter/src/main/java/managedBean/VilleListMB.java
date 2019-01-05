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
import model.Ville;
import services.VilleHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class VilleListMB implements Serializable {

	@Inject
	VilleHome villehome;

	Integer idVille;

	LazyDataModel<Ville> villes;

	Filter<Ville> filter = new Filter<>(new Ville());

	List<Ville> selectedvilles; // villes selected in checkbox column

	List<Ville> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		villes = new LazyDataModel<Ville>() {
			@Override
			public List<Ville> load(int first, int pageSize, String sortField, SortOrder sortOrder,
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
				List<Ville> list = villehome.paginate(filter);
				setRowCount((int) villehome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public Ville getRowData(String key) {
				return villehome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return villehome.getAll().stream().filter(c -> c.getNomVille()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Ville::getNomVille)
                .collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<Ville>(new Ville());
	}

	public void findVilleById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide Ville ID to load");
		}
		selectedvilles.add(villehome.findById(id));
	}

	public List<Ville> getSelectedvilles() {
		return selectedvilles;
	}

	public List<Ville> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Ville> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedvilles(List<Ville> selectedvilles) {
		this.selectedvilles = selectedvilles;
	}

	public LazyDataModel<Ville> getvilles() {
		return villes;
	}

	public void setvilles(LazyDataModel<Ville> villes) {
		this.villes = villes;
	}

	public Filter<Ville> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Ville> filter) {
		this.filter = filter;
	}

	public Integer getIdVille() {
		return idVille;
	}

	public void setIdVille(Integer id) {
		this.idVille = id;
	}
}
