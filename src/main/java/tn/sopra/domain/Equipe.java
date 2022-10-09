package tn.sopra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_equipe")
    private String nomEquipe;

    @ManyToMany
    @JoinTable(
        name = "rel_equipe__equipeuser",
        joinColumns = @JoinColumn(name = "equipe_id"),
        inverseJoinColumns = @JoinColumn(name = "equipeuser_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsable", "competances", "demandes", "usersmembers", "usermembers" }, allowSetters = true)
    private Set<UserSopra> equipeusers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEquipe() {
        return this.nomEquipe;
    }

    public Equipe nomEquipe(String nomEquipe) {
        this.setNomEquipe(nomEquipe);
        return this;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public Set<UserSopra> getEquipeusers() {
        return this.equipeusers;
    }

    public void setEquipeusers(Set<UserSopra> userSopras) {
        this.equipeusers = userSopras;
    }

    public Equipe equipeusers(Set<UserSopra> userSopras) {
        this.setEquipeusers(userSopras);
        return this;
    }

    public Equipe addEquipeuser(UserSopra userSopra) {
        this.equipeusers.add(userSopra);
        userSopra.getUsersmembers().add(this);
        return this;
    }

    public Equipe removeEquipeuser(UserSopra userSopra) {
        this.equipeusers.remove(userSopra);
        userSopra.getUsersmembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipe)) {
            return false;
        }
        return id != null && id.equals(((Equipe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", nomEquipe='" + getNomEquipe() + "'" +
            "}";
    }
}
