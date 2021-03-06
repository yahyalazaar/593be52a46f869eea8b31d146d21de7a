package model;
// Generated Dec 29, 2018 5:07:18 PM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Fournisseurs generated by hbm2java
 */
@Entity
@Table(name = "fournisseurs")
public class Fournisseurs implements java.io.Serializable {

	private Integer idFournisseurs;
	private String nomFournisseur;
	private String adresseFournisseur;
	private FrsParticulier frsParticulier;
	private FrsEntreprise frsEntreprise;
	private Set<ProprieteFrs> proprieteFrses = new HashSet<ProprieteFrs>(0);

	public Fournisseurs() {
	}

	public Fournisseurs(String nomFournisseur, String adresseFournisseur, FrsParticulier frsParticulier,
			FrsEntreprise frsEntreprise, Set<ProprieteFrs> proprieteFrses) {
		this.nomFournisseur = nomFournisseur;
		this.adresseFournisseur = adresseFournisseur;
		this.frsParticulier = frsParticulier;
		this.frsEntreprise = frsEntreprise;
		this.proprieteFrses = proprieteFrses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_Fournisseurs", unique = true, nullable = false)
	public Integer getIdFournisseurs() {
		return this.idFournisseurs;
	}

	public void setIdFournisseurs(Integer idFournisseurs) {
		this.idFournisseurs = idFournisseurs;
	}

	@Column(name = "nom_Fournisseur", length = 254)
	public String getNomFournisseur() {
		return this.nomFournisseur;
	}

	public void setNomFournisseur(String nomFournisseur) {
		this.nomFournisseur = nomFournisseur;
	}

	@Column(name = "adresse_Fournisseur", length = 254)
	public String getAdresseFournisseur() {
		return this.adresseFournisseur;
	}

	public void setAdresseFournisseur(String adresseFournisseur) {
		this.adresseFournisseur = adresseFournisseur;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "fournisseurs")
	public FrsParticulier getFrsParticulier() {
		return this.frsParticulier;
	}

	public void setFrsParticulier(FrsParticulier frsParticulier) {
		this.frsParticulier = frsParticulier;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "fournisseurs")
	public FrsEntreprise getFrsEntreprise() {
		return this.frsEntreprise;
	}

	public void setFrsEntreprise(FrsEntreprise frsEntreprise) {
		this.frsEntreprise = frsEntreprise;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fournisseurs")
	public Set<ProprieteFrs> getProprieteFrses() {
		return this.proprieteFrses;
	}

	public void setProprieteFrses(Set<ProprieteFrs> proprieteFrses) {
		this.proprieteFrses = proprieteFrses;
	}

}
