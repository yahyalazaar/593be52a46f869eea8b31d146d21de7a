package model;
// Generated Dec 29, 2018 5:07:18 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * VoteCopropProp generated by hbm2java
 */
@Entity
@Table(name = "vote_coprop_prop")
public class VoteCopropProp implements java.io.Serializable {

	private VoteCopropPropId id;
	private Assemble assemble;
	private UserCoproprietaire userCoproprietaire;
	private Date dateVote;
	private Boolean vote;

	public VoteCopropProp() {
	}

	public VoteCopropProp(VoteCopropPropId id, Assemble assemble, UserCoproprietaire userCoproprietaire) {
		this.id = id;
		this.assemble = assemble;
		this.userCoproprietaire = userCoproprietaire;
	}

	public VoteCopropProp(VoteCopropPropId id, Assemble assemble, UserCoproprietaire userCoproprietaire, Date dateVote,
			Boolean vote) {
		this.id = id;
		this.assemble = assemble;
		this.userCoproprietaire = userCoproprietaire;
		this.dateVote = dateVote;
		this.vote = vote;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idUser", column = @Column(name = "id_User", nullable = false)),
			@AttributeOverride(name = "idAssemble", column = @Column(name = "id_Assemble", nullable = false)) })
	public VoteCopropPropId getId() {
		return this.id;
	}

	public void setId(VoteCopropPropId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Assemble", nullable = false, insertable = false, updatable = false)
	public Assemble getAssemble() {
		return this.assemble;
	}

	public void setAssemble(Assemble assemble) {
		this.assemble = assemble;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_User", nullable = false, insertable = false, updatable = false)
	public UserCoproprietaire getUserCoproprietaire() {
		return this.userCoproprietaire;
	}

	public void setUserCoproprietaire(UserCoproprietaire userCoproprietaire) {
		this.userCoproprietaire = userCoproprietaire;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_Vote", length = 19)
	public Date getDateVote() {
		return this.dateVote;
	}

	public void setDateVote(Date dateVote) {
		this.dateVote = dateVote;
	}

	@Column(name = "vote")
	public Boolean getVote() {
		return this.vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

}
