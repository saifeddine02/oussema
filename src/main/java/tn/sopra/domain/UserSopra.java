package tn.sopra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tn.sopra.domain.enumeration.UserRolesSopra;

/**
 * A UserSopra.
 */
@Entity
@Table(name = "user_sopra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserSopra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_user")
    private String nomUser;

    @Column(name = "prenom_user")
    private String prenomUser;

    @Column(name = "email_user")
    private String emailUser;

    @Column(name = "matricule_user")
    private String matriculeUser;

    @Column(name = "disponibilite_user")
    private Boolean disponibiliteUser;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_roles_sopra")
    private UserRolesSopra userRolesSopra;

    @JsonIgnoreProperties(value = { "responsable" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Responsable responsable;

    @OneToMany(mappedBy = "userSopra")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userSopra" }, allowSetters = true)
    private Set<Competance> competances = new HashSet<>();

    @OneToMany(mappedBy = "userSopra")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userSopra" }, allowSetters = true)
    private Set<Demande> demandes = new HashSet<>();

    @ManyToMany(mappedBy = "equipeusers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "equipeusers" }, allowSetters = true)
    private Set<Equipe> usersmembers = new HashSet<>();

    @ManyToMany(mappedBy = "projectMenbers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectMenbers" }, allowSetters = true)
    private Set<Projet> usermembers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserSopra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUser() {
        return this.nomUser;
    }

    public UserSopra nomUser(String nomUser) {
        this.setNomUser(nomUser);
        return this;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return this.prenomUser;
    }

    public UserSopra prenomUser(String prenomUser) {
        this.setPrenomUser(prenomUser);
        return this;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getEmailUser() {
        return this.emailUser;
    }

    public UserSopra emailUser(String emailUser) {
        this.setEmailUser(emailUser);
        return this;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getMatriculeUser() {
        return this.matriculeUser;
    }

    public UserSopra matriculeUser(String matriculeUser) {
        this.setMatriculeUser(matriculeUser);
        return this;
    }

    public void setMatriculeUser(String matriculeUser) {
        this.matriculeUser = matriculeUser;
    }

    public Boolean getDisponibiliteUser() {
        return this.disponibiliteUser;
    }

    public UserSopra disponibiliteUser(Boolean disponibiliteUser) {
        this.setDisponibiliteUser(disponibiliteUser);
        return this;
    }

    public void setDisponibiliteUser(Boolean disponibiliteUser) {
        this.disponibiliteUser = disponibiliteUser;
    }

    public byte[] getImage() {
        return this.image;
    }

    public UserSopra image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public UserSopra imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public UserRolesSopra getUserRolesSopra() {
        return this.userRolesSopra;
    }

    public UserSopra userRolesSopra(UserRolesSopra userRolesSopra) {
        this.setUserRolesSopra(userRolesSopra);
        return this;
    }

    public void setUserRolesSopra(UserRolesSopra userRolesSopra) {
        this.userRolesSopra = userRolesSopra;
    }

    public Responsable getResponsable() {
        return this.responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public UserSopra responsable(Responsable responsable) {
        this.setResponsable(responsable);
        return this;
    }

    public Set<Competance> getCompetances() {
        return this.competances;
    }

    public void setCompetances(Set<Competance> competances) {
        if (this.competances != null) {
            this.competances.forEach(i -> i.setUserSopra(null));
        }
        if (competances != null) {
            competances.forEach(i -> i.setUserSopra(this));
        }
        this.competances = competances;
    }

    public UserSopra competances(Set<Competance> competances) {
        this.setCompetances(competances);
        return this;
    }

    public UserSopra addCompetances(Competance competance) {
        this.competances.add(competance);
        competance.setUserSopra(this);
        return this;
    }

    public UserSopra removeCompetances(Competance competance) {
        this.competances.remove(competance);
        competance.setUserSopra(null);
        return this;
    }

    public Set<Demande> getDemandes() {
        return this.demandes;
    }

    public void setDemandes(Set<Demande> demandes) {
        if (this.demandes != null) {
            this.demandes.forEach(i -> i.setUserSopra(null));
        }
        if (demandes != null) {
            demandes.forEach(i -> i.setUserSopra(this));
        }
        this.demandes = demandes;
    }

    public UserSopra demandes(Set<Demande> demandes) {
        this.setDemandes(demandes);
        return this;
    }

    public UserSopra addDemandes(Demande demande) {
        this.demandes.add(demande);
        demande.setUserSopra(this);
        return this;
    }

    public UserSopra removeDemandes(Demande demande) {
        this.demandes.remove(demande);
        demande.setUserSopra(null);
        return this;
    }

    public Set<Equipe> getUsersmembers() {
        return this.usersmembers;
    }

    public void setUsersmembers(Set<Equipe> equipes) {
        if (this.usersmembers != null) {
            this.usersmembers.forEach(i -> i.removeEquipeuser(this));
        }
        if (equipes != null) {
            equipes.forEach(i -> i.addEquipeuser(this));
        }
        this.usersmembers = equipes;
    }

    public UserSopra usersmembers(Set<Equipe> equipes) {
        this.setUsersmembers(equipes);
        return this;
    }

    public UserSopra addUsersmembers(Equipe equipe) {
        this.usersmembers.add(equipe);
        equipe.getEquipeusers().add(this);
        return this;
    }

    public UserSopra removeUsersmembers(Equipe equipe) {
        this.usersmembers.remove(equipe);
        equipe.getEquipeusers().remove(this);
        return this;
    }

    public Set<Projet> getUsermembers() {
        return this.usermembers;
    }

    public void setUsermembers(Set<Projet> projets) {
        if (this.usermembers != null) {
            this.usermembers.forEach(i -> i.removeProjectMenbers(this));
        }
        if (projets != null) {
            projets.forEach(i -> i.addProjectMenbers(this));
        }
        this.usermembers = projets;
    }

    public UserSopra usermembers(Set<Projet> projets) {
        this.setUsermembers(projets);
        return this;
    }

    public UserSopra addUsermembers(Projet projet) {
        this.usermembers.add(projet);
        projet.getProjectMenbers().add(this);
        return this;
    }

    public UserSopra removeUsermembers(Projet projet) {
        this.usermembers.remove(projet);
        projet.getProjectMenbers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSopra)) {
            return false;
        }
        return id != null && id.equals(((UserSopra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSopra{" +
            "id=" + getId() +
            ", nomUser='" + getNomUser() + "'" +
            ", prenomUser='" + getPrenomUser() + "'" +
            ", emailUser='" + getEmailUser() + "'" +
            ", matriculeUser='" + getMatriculeUser() + "'" +
            ", disponibiliteUser='" + getDisponibiliteUser() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", userRolesSopra='" + getUserRolesSopra() + "'" +
            "}";
    }
}
