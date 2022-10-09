package tn.sopra.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link tn.sopra.domain.Competance} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetanceDTO implements Serializable {

    private Long id;

    private String nomCompetance;

    private Integer dureeCompetance;

    private Integer niveauCompetance;

    @Lob
    private byte[] image;

    private String imageContentType;
    private UserSopraDTO userSopra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCompetance() {
        return nomCompetance;
    }

    public void setNomCompetance(String nomCompetance) {
        this.nomCompetance = nomCompetance;
    }

    public Integer getDureeCompetance() {
        return dureeCompetance;
    }

    public void setDureeCompetance(Integer dureeCompetance) {
        this.dureeCompetance = dureeCompetance;
    }

    public Integer getNiveauCompetance() {
        return niveauCompetance;
    }

    public void setNiveauCompetance(Integer niveauCompetance) {
        this.niveauCompetance = niveauCompetance;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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
        if (!(o instanceof CompetanceDTO)) {
            return false;
        }

        CompetanceDTO competanceDTO = (CompetanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, competanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetanceDTO{" +
            "id=" + getId() +
            ", nomCompetance='" + getNomCompetance() + "'" +
            ", dureeCompetance=" + getDureeCompetance() +
            ", niveauCompetance=" + getNiveauCompetance() +
            ", image='" + getImage() + "'" +
            ", userSopra=" + getUserSopra() +
            "}";
    }
}
