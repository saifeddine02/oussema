package tn.sopra.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link tn.sopra.domain.Demande} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDTO implements Serializable {

    private Long id;

    private Instant startDemande;

    private Instant endDemande;

    private Instant statusDemande;

    private UserSopraDTO userSopra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDemande() {
        return startDemande;
    }

    public void setStartDemande(Instant startDemande) {
        this.startDemande = startDemande;
    }

    public Instant getEndDemande() {
        return endDemande;
    }

    public void setEndDemande(Instant endDemande) {
        this.endDemande = endDemande;
    }

    public Instant getStatusDemande() {
        return statusDemande;
    }

    public void setStatusDemande(Instant statusDemande) {
        this.statusDemande = statusDemande;
    }

    public UserSopraDTO getUserSopra() {
        return userSopra;
    }

    public void setUserSopra(UserSopraDTO userSopra) {
        this.userSopra = userSopra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeDTO)) {
            return false;
        }

        DemandeDTO demandeDTO = (DemandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDTO{" +
            "id=" + getId() +
            ", startDemande='" + getStartDemande() + "'" +
            ", endDemande='" + getEndDemande() + "'" +
            ", statusDemande='" + getStatusDemande() + "'" +
            ", userSopra=" + getUserSopra() +
            "}";
    }
}
