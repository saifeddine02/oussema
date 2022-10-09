package tn.sopra.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tn.sopra.domain.enumeration.StatusDemande;

/**
 * A DemandeConge.
 */
@Entity
@Table(name = "demande_conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeConge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "avis_tl")
    private Boolean avisTl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_demande")
    private StatusDemande statusDemande;

    @Column(name = "date_debut_conge")
    private Instant dateDebutConge;

    @Column(name = "date_fin_conge")
    private Instant dateFinConge;

    @Column(name = "nombre_jour")
    private Integer nombreJour;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeConge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public DemandeConge creationDate(Instant creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getAvisTl() {
        return this.avisTl;
    }

    public DemandeConge avisTl(Boolean avisTl) {
        this.setAvisTl(avisTl);
        return this;
    }

    public void setAvisTl(Boolean avisTl) {
        this.avisTl = avisTl;
    }

    public StatusDemande getStatusDemande() {
        return this.statusDemande;
    }

    public DemandeConge statusDemande(StatusDemande statusDemande) {
        this.setStatusDemande(statusDemande);
        return this;
    }

    public void setStatusDemande(StatusDemande statusDemande) {
        this.statusDemande = statusDemande;
    }

    public Instant getDateDebutConge() {
        return this.dateDebutConge;
    }

    public DemandeConge dateDebutConge(Instant dateDebutConge) {
        this.setDateDebutConge(dateDebutConge);
        return this;
    }

    public void setDateDebutConge(Instant dateDebutConge) {
        this.dateDebutConge = dateDebutConge;
    }

    public Instant getDateFinConge() {
        return this.dateFinConge;
    }

    public DemandeConge dateFinConge(Instant dateFinConge) {
        this.setDateFinConge(dateFinConge);
        return this;
    }

    public void setDateFinConge(Instant dateFinConge) {
        this.dateFinConge = dateFinConge;
    }

    public Integer getNombreJour() {
        return this.nombreJour;
    }

    public DemandeConge nombreJour(Integer nombreJour) {
        this.setNombreJour(nombreJour);
        return this;
    }

    public void setNombreJour(Integer nombreJour) {
        this.nombreJour = nombreJour;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeConge)) {
            return false;
        }
        return id != null && id.equals(((DemandeConge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeConge{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", avisTl='" + getAvisTl() + "'" +
            ", statusDemande='" + getStatusDemande() + "'" +
            ", dateDebutConge='" + getDateDebutConge() + "'" +
            ", dateFinConge='" + getDateFinConge() + "'" +
            ", nombreJour=" + getNombreJour() +
            "}";
    }
}
