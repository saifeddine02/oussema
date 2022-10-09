package tn.sopra.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link tn.sopra.domain.Equipe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipeDTO implements Serializable {

    private Long id;

    private String nomEquipe;

    private Set<UserSopraDTO> equipeusers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public Set<UserSopraDTO> getEquipeusers() {
        return equipeusers;
    }

    public void setEquipeusers(Set<UserSopraDTO> equipeusers) {
        this.equipeusers = equipeusers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipeDTO)) {
            return false;
        }

        EquipeDTO equipeDTO = (EquipeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, equipeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipeDTO{" +
            "id=" + getId() +
            ", nomEquipe='" + getNomEquipe() + "'" +
            ", equipeusers=" + getEquipeusers() +
            "}";
    }
}
