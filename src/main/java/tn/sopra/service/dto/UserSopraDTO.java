package tn.sopra.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import tn.sopra.domain.enumeration.UserRolesSopra;

/**
 * A DTO for the {@link tn.sopra.domain.UserSopra} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserSopraDTO implements Serializable {

    private Long id;

    private String nomUser;

    private String prenomUser;

    private String emailUser;

    private String matriculeUser;

    private Boolean disponibiliteUser;

    @Lob
    private byte[] image;

    private String imageContentType;
    private UserRolesSopra userRolesSopra;

    private ResponsableDTO responsable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getMatriculeUser() {
        return matriculeUser;
    }

    public void setMatriculeUser(String matriculeUser) {
        this.matriculeUser = matriculeUser;
    }

    public Boolean getDisponibiliteUser() {
        return disponibiliteUser;
    }

    public void setDisponibiliteUser(Boolean disponibiliteUser) {
        this.disponibiliteUser = disponibiliteUser;
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

    public UserRolesSopra getUserRolesSopra() {
        return userRolesSopra;
    }

    public void setUserRolesSopra(UserRolesSopra userRolesSopra) {
        this.userRolesSopra = userRolesSopra;
    }

    public ResponsableDTO getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableDTO responsable) {
        this.responsable = responsable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSopraDTO)) {
            return false;
        }

        UserSopraDTO userSopraDTO = (UserSopraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userSopraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSopraDTO{" +
            "id=" + getId() +
            ", nomUser='" + getNomUser() + "'" +
            ", prenomUser='" + getPrenomUser() + "'" +
            ", emailUser='" + getEmailUser() + "'" +
            ", matriculeUser='" + getMatriculeUser() + "'" +
            ", disponibiliteUser='" + getDisponibiliteUser() + "'" +
            ", image='" + getImage() + "'" +
            ", userRolesSopra='" + getUserRolesSopra() + "'" +
            ", responsable=" + getResponsable() +
            "}";
    }
}
