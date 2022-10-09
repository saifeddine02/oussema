package tn.sopra.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import tn.sopra.domain.enumeration.StatusDemande;

/**
 * A DTO for the {@link tn.sopra.domain.DemandeConge} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeCongeDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    private Boolean avisTl;

    private StatusDemande statusDemande;

    private Instant dateDebutConge;

    private Instant dateFinConge;

    private Integer nombreJour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getAvisTl() {
        return avisTl;
    }

    public void setAvisTl(Boolean avisTl) {
        this.avisTl = avisTl;
    }

    public StatusDemande getStatusDemande() {
        return statusDemande;
    }

    public void setStatusDemande(StatusDemande statusDemande) {
        this.statusDemande = statusDemande;
    }

    public Instant getDateDebutConge() {
        return dateDebutConge;
    }

    public void setDateDebutConge(Instant dateDebutConge) {
        this.dateDebutConge = dateDebutConge;
    }

    public Instant getDateFinConge() {
        return dateFinConge;
    }

    public void setDateFinConge(Instant dateFinConge) {
        this.dateFinConge = dateFinConge;
    }

    public Integer getNombreJour() {
        return nombreJour;
    }

    public void setNombreJour(Integer nombreJour) {
        this.nombreJour = nombreJour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeCongeDTO)) {
            return false;
        }

        DemandeCongeDTO demandeCongeDTO = (DemandeCongeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeCongeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeCongeDTO{" +
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
