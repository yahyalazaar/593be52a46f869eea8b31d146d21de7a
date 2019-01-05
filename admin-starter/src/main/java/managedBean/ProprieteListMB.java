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
import model.Propriete;
import services.ProprieteHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class ProprieteListMB implements Serializable {

	@Inject
	ProprieteHome Proprietehome;

	Integer idPropriete;

	LazyDataModel<Propriete> Proprietes;

	Filter<Propriete> filter = new Filter<>(new Propriete());

	List<Propriete> selectedProprietes; // Proprietes selected in checkbox column

	List<Propriete> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		Proprietes = new LazyDataModel<Propriete>() {
			@Override
			public List<Propriete> load(int first, int pageSize, String sortField, SortOrder sortOrder,
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
				List<Propriete> list = Proprietehome.paginate(filter);
				setRowCount((int) Proprietehome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public Propriete getRowData(String key) {
				return Proprietehome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return Proprietehome.getAll().stream().filter(c -> c.getNomPropriete()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Propriete::getNomPropriete)
                .collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<Propriete>(new Propriete());
	}

	public void findProprieteById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide Propriete ID to load");
		}
		selectedProprietes.add(Proprietehome.findById(id));
	}

	public List<Propriete> getSelectedProprietes() {
		return selectedProprietes;
	}

	public List<Propriete> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Propriete> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedProprietes(List<Propriete> selectedProprietes) {
		this.selectedProprietes = selectedProprietes;
	}

	public LazyDataModel<Propriete> getProprietes() {
		return Proprietes;
	}

	public void setProprietes(LazyDataModel<Propriete> Proprietes) {
		this.Proprietes = Proprietes;
	}

	public Filter<Propriete> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Propriete> filter) {
		this.filter = filter;
	}

	public Integer getIdPropriete() {
		return idPropriete;
	}

	public void setIdPropriete(Integer id) {
		this.idPropriete = id;
	}
}
