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
import model.Assemble;
import services.AssembleHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class AssembleListMB implements Serializable {

	@Inject
	AssembleHome Assemblehome;

	Integer idAssemble;

	LazyDataModel<Assemble> Assembles;

	Filter<Assemble> filter = new Filter<>(new Assemble());

	List<Assemble> selectedAssembles; // Assembles selected in checkbox column

	List<Assemble> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		Assembles = new LazyDataModel<Assemble>() {
			@Override
			public List<Assemble> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				infra.model.SortOrder order = null;
				if (sortOrder != null) {
					order = sortOrder.equals(SortOrder.ASCENDING) ? infra.model.SortOrder.ASCENDING
							: sortOrder.equals(SortOrder.DESCENDING) ? infra.model.SortOrder.DESCENDING
									: infra.model.SortOrder.UNSORTED;
				}
				filter.setFirst(first).setPageSize(pageSize).setSortField(sortField).setSortOrder(order)
						.setParams(filters);
				List<Assemble> list = Assemblehome.paginate(filter);
				setRowCount((int) Assemblehome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public Assemble getRowData(String key) {
				return Assemblehome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return Assemblehome.getAllAssemble().stream()
				.filter(c -> c.getPropriete().getNomPropriete().toLowerCase().contains(query.toLowerCase()))
				.map(Assemble -> Assemble.getPropriete().getNomPropriete()).collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<Assemble>(new Assemble());
	}

	public void findAssembleById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide Assemble ID to load");
		}
		selectedAssembles.add(Assemblehome.findById(id));
	}

	public List<Assemble> getSelectedAssembles() {
		return selectedAssembles;
	}

	public List<Assemble> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Assemble> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedAssembles(List<Assemble> selectedAssembles) {
		this.selectedAssembles = selectedAssembles;
	}

	public LazyDataModel<Assemble> getAssembles() {
		return Assembles;
	}

	public void setAssembles(LazyDataModel<Assemble> Assembles) {
		this.Assembles = Assembles;
	}

	public Filter<Assemble> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Assemble> filter) {
		this.filter = filter;
	}

	public Integer getIdAssemble() {
		return idAssemble;
	}

	public void setIdAssemble(Integer id) {
		this.idAssemble = id;
	}
}
