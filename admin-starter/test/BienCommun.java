// default package
// Generated Dec 21, 2018 3:25:04 PM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * BienCommun generated by hbm2java
 */
@Entity
@Table(name = "bien_commun", catalog = "copropriete")
public class BienCommun implements java.io.Serializable {

	private Integer idBien;
	private Propriete propriete;
	private String typeBien;
	private Set<LocationBien> locationBiens = new HashSet<LocationBien>(0);

	public BienCommun() {
	}

	public BienCommun(Propriete propriete) {
		this.propriete = propriete;
	}

	public BienCommun(Propriete propriete, String typeBien, Set<LocationBien> locationBiens) {
		this.propriete = propriete;
		this.typeBien = typeBien;
		this.locationBiens = locationBiens;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_Bien", unique = true, nullable = false)
	public Integer getIdBien() {
		return this.idBien;
	}

	public void setIdBien(Integer idBien) {
		this.idBien = idBien;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Propriete", nullable = false)
	public Propriete getPropriete() {
		return this.propriete;
	}

	public void setPropriete(Propriete propriete) {
		this.propriete = propriete;
	}

	@Column(name = "type_Bien", length = 254)
	public String getTypeBien() {
		return this.typeBien;
	}

	public void setTypeBien(String typeBien) {
		this.typeBien = typeBien;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bienCommun")
	public Set<LocationBien> getLocationBiens() {
		return this.locationBiens;
	}

	public void setLocationBiens(Set<LocationBien> locationBiens) {
		this.locationBiens = locationBiens;
	}

}
