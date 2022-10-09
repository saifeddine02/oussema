package tn.sopra.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link tn.sopra.domain.Responsable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsableDTO implements Serializable {

    private Long id;

    private String nomResponsable;

    private String prenomResponsable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public String getPrenomResponsable() {
        return prenomResponsable;
    }

    public void setPrenomResponsable(String prenomResponsable) {
        this.prenomResponsable = prenomResponsable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsableDTO)) {
            return false;
        }

        ResponsableDTO responsableDTO = (ResponsableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responsableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsableDTO{" +
            "id=" + getId() +
            ", nomResponsable='" + getNomResponsable() + "'" +
            ", prenomResponsable='" + getPrenomResponsable() + "'" +
            "}";
    }
}
