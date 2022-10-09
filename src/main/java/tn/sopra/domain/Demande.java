package tn.sopra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Demande.
 */
@Entity
@Table(name = "demande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_demande")
    private Instant startDemande;

    @Column(name = "end_demande")
    private Instant endDemande;

    @Column(name = "status_demande")
    private Instant statusDemande;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsable", "competances", "demandes", "usersmembers", "usermembers" }, allowSetters = true)
    private UserSopra userSopra;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDemande() {
        return this.startDemande;
    }

    public Demande startDemande(Instant startDemande) {
        this.setStartDemande(startDemande);
        return this;
    }

    public void setStartDemande(Instant startDemande) {
        this.startDemande = startDemande;
    }

    public Instant getEndDemande() {
        return this.endDemande;
    }

    public Demande endDemande(Instant endDemande) {
        this.setEndDemande(endDemande);
        return this;
    }

    public void setEndDemande(Instant endDemande) {
        this.endDemande = endDemande;
    }

    public Instant getStatusDemande() {
        return this.statusDemande;
    }

    public Demande statusDemande(Instant statusDemande) {
        this.setStatusDemande(statusDemande);
        return this;
    }

    public void setStatusDemande(Instant statusDemande) {
        this.statusDemande = statusDemande;
    }

    public UserSopra getUserSopra() {
        return this.userSopra;
    }

    public void setUserSopra(UserSopra userSopra) {
        this.userSopra = userSopra;
    }

    public Demande userSopra(UserSopra userSopra) {
        this.setUserSopra(userSopra);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demande)) {
            return false;
        }
        return id != null && id.equals(((Demande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demande{" +
            "id=" + getId() +
            ", startDemande='" + getStartDemande() + "'" +
            ", endDemande='" + getEndDemande() + "'" +
            ", statusDemande='" + getStatusDemande() + "'" +
            "}";
    }
}
