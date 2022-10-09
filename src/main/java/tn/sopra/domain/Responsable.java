package tn.sopra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Responsable.
 */
@Entity
@Table(name = "responsable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Responsable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_responsable")
    private String nomResponsable;

    @Column(name = "prenom_responsable")
    private String prenomResponsable;

    @JsonIgnoreProperties(value = { "responsable", "competances", "demandes", "usersmembers", "usermembers" }, allowSetters = true)
    @OneToOne(mappedBy = "responsable")
    private UserSopra responsable;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Responsable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomResponsable() {
        return this.nomResponsable;
    }

    public Responsable nomResponsable(String nomResponsable) {
        this.setNomResponsable(nomResponsable);
        return this;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public String getPrenomResponsable() {
        return this.prenomResponsable;
    }

    public Responsable prenomResponsable(String prenomResponsable) {
        this.setPrenomResponsable(prenomResponsable);
        return this;
    }

    public void setPrenomResponsable(String prenomResponsable) {
        this.prenomResponsable = prenomResponsable;
    }

    public UserSopra getResponsable() {
        return this.responsable;
    }

    public void setResponsable(UserSopra userSopra) {
        if (this.responsable != null) {
            this.responsable.setResponsable(null);
        }
        if (userSopra != null) {
            userSopra.setResponsable(this);
        }
        this.responsable = userSopra;
    }

    public Responsable responsable(UserSopra userSopra) {
        this.setResponsable(userSopra);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsable)) {
            return false;
        }
        return id != null && id.equals(((Responsable) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Responsable{" +
            "id=" + getId() +
            ", nomResponsable='" + getNomResponsable() + "'" +
            ", prenomResponsable='" + getPrenomResponsable() + "'" +
            "}";
    }
}
