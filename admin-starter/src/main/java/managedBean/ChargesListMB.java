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
import model.Charges;
import services.ChargesHome;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class ChargesListMB implements Serializable {

	@Inject
	ChargesHome Chargeshome;

	Integer idCharges;

	LazyDataModel<Charges> Chargess;

	Filter<Charges> filter = new Filter<>(new Charges());

	List<Charges> selectedChargess; // Chargess selected in checkbox column

	List<Charges> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		Chargess = new LazyDataModel<Charges>() {
			@Override
			public List<Charges> load(int first, int pageSize, String sortField, SortOrder sortOrder,
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
				List<Charges> list = Chargeshome.paginate(filter);
				setRowCount((int) Chargeshome.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public Charges getRowData(String key) {
				return Chargeshome.findById(new Integer(key));
			}
		};
	}

	public List<String> completeModel(String query) {
		return Chargeshome.getAll().stream().filter(c -> c.getNomCharges()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Charges::getNomCharges)
                .collect(Collectors.toList());
	}

	public void clear() {
		filter = new Filter<Charges>(new Charges());
	}

	public void findChargesById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide Charges ID to load");
		}
		selectedChargess.add(Chargeshome.findById(id));
	}

	public List<Charges> getSelectedChargess() {
		return selectedChargess;
	}

	public List<Charges> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Charges> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedChargess(List<Charges> selectedChargess) {
		this.selectedChargess = selectedChargess;
	}

	public LazyDataModel<Charges> getChargess() {
		return Chargess;
	}

	public void setChargess(LazyDataModel<Charges> Chargess) {
		this.Chargess = Chargess;
	}

	public Filter<Charges> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Charges> filter) {
		this.filter = filter;
	}

	public Integer getIdCharges() {
		return idCharges;
	}

	public void setIdCharges(Integer id) {
		this.idCharges = id;
	}
}
