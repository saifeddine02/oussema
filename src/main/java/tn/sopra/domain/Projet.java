package tn.sopra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_projet")
    private String nomProjet;

    @Column(name = "pays_projet")
    private String paysProjet;

    @Column(name = "region_projet")
    private String regionProjet;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "nombre_ressource")
    private Integer nombreRessource;

    @Column(name = "date_fin")
    private Instant dateFin;

    @Column(name = "competance_demander")
    private String competanceDemander;

    @ManyToMany
    @JoinTable(
        name = "rel_projet__project_menbers",
        joinColumns = @JoinColumn(name = "projet_id"),
        inverseJoinColumns = @JoinColumn(name = "project_menbers_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsable", "competances", "demandes", "usersmembers", "usermembers" }, allowSetters = true)
    private Set<UserSopra> projectMenbers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProjet() {
        return this.nomProjet;
    }

    public Projet nomProjet(String nomProjet) {
        this.setNomProjet(nomProjet);
        return this;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public String getPaysProjet() {
        return this.paysProjet;
    }

    public Projet paysProjet(String paysProjet) {
        this.setPaysProjet(paysProjet);
        return this;
    }

    public void setPaysProjet(String paysProjet) {
        this.paysProjet = paysProjet;
    }

    public String getRegionProjet() {
        return this.regionProjet;
    }

    public Projet regionProjet(String regionProjet) {
        this.setRegionProjet(regionProjet);
        return this;
    }

    public void setRegionProjet(String regionProjet) {
        this.regionProjet = regionProjet;
    }

    public Instant getDateDebut() {
        return this.dateDebut;
    }

    public Projet dateDebut(Instant dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Integer getNombreRessource() {
        return this.nombreRessource;
    }

    public Projet nombreRessource(Integer nombreRessource) {
        this.setNombreRessource(nombreRessource);
        return this;
    }

    public void setNombreRessource(Integer nombreRessource) {
        this.nombreRessource = nombreRessource;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public Projet dateFin(Instant dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getCompetanceDemander() {
        return this.competanceDemander;
    }

    public Projet competanceDemander(String competanceDemander) {
        this.setCompetanceDemander(competanceDemander);
        return this;
    }

    public void setCompetanceDemander(String competanceDemander) {
        this.competanceDemander = competanceDemander;
    }

    public Set<UserSopra> getProjectMenbers() {
        return this.projectMenbers;
    }

    public void setProjectMenbers(Set<UserSopra> userSopras) {
        this.projectMenbers = userSopras;
    }

    public Projet projectMenbers(Set<UserSopra> userSopras) {
        this.setProjectMenbers(userSopras);
        return this;
    }

    public Projet addProjectMenbers(UserSopra userSopra) {
        this.projectMenbers.add(userSopra);
        userSopra.getUsermembers().add(this);
        return this;
    }

    public Projet removeProjectMenbers(UserSopra userSopra) {
        this.projectMenbers.remove(userSopra);
        userSopra.getUsermembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nomProjet='" + getNomProjet() + "'" +
            ", paysProjet='" + getPaysProjet() + "'" +
            ", regionProjet='" + getRegionProjet() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", nombreRessource=" + getNombreRessource() +
            ", dateFin='" + getDateFin() + "'" +
            ", competanceDemander='" + getCompetanceDemander() + "'" +
            "}";
    }
}
