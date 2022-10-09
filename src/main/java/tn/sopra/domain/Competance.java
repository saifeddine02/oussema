package tn.sopra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Competance.
 */
@Entity
@Table(name = "competance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Competance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_competance")
    private String nomCompetance;

    @Column(name = "duree_competance")
    private Integer dureeCompetance;

    @Column(name = "niveau_competance")
    private Integer niveauCompetance;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsable", "competances", "demandes", "usersmembers", "usermembers" }, allowSetters = true)
    private UserSopra userSopra;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Competance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCompetance() {
        return this.nomCompetance;
    }

    public Competance nomCompetance(String nomCompetance) {
        this.setNomCompetance(nomCompetance);
        return this;
    }

    public void setNomCompetance(String nomCompetance) {
        this.nomCompetance = nomCompetance;
    }

    public Integer getDureeCompetance() {
        return this.dureeCompetance;
    }

    public Competance dureeCompetance(Integer dureeCompetance) {
        this.setDureeCompetance(dureeCompetance);
        return this;
    }

    public void setDureeCompetance(Integer dureeCompetance) {
        this.dureeCompetance = dureeCompetance;
    }

    public Integer getNiveauCompetance() {
        return this.niveauCompetance;
    }

    public Competance niveauCompetance(Integer niveauCompetance) {
        this.setNiveauCompetance(niveauCompetance);
        return this;
    }

    public void setNiveauCompetance(Integer niveauCompetance) {
        this.niveauCompetance = niveauCompetance;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Competance image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Competance imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public UserSopra getUserSopra() {
        return this.userSopra;
    }

    public void setUserSopra(UserSopra userSopra) {
        this.userSopra = userSopra;
    }

    public Competance userSopra(UserSopra userSopra) {
        this.setUserSopra(userSopra);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competance)) {
            return false;
        }
        return id != null && id.equals(((Competance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competance{" +
            "id=" + getId() +
            ", nomCompetance='" + getNomCompetance() + "'" +
            ", dureeCompetance=" + getDureeCompetance() +
            ", niveauCompetance=" + getNiveauCompetance() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
