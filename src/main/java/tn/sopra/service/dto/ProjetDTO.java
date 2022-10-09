package tn.sopra.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link tn.sopra.domain.Projet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjetDTO implements Serializable {

    private Long id;

    private String nomProjet;

    private String paysProjet;

    private String regionProjet;

    private Instant dateDebut;

    private Integer nombreRessource;

    private Instant dateFin;

    private String competanceDemander;

    private Set<UserSopraDTO> projectMenbers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public String getPaysProjet() {
        return paysProjet;
    }

    public void setPaysProjet(String paysProjet) {
        this.paysProjet = paysProjet;
    }

    public String getRegionProjet() {
        return regionProjet;
    }

    public void setRegionProjet(String regionProjet) {
        this.regionProjet = regionProjet;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Integer getNombreRessource() {
        return nombreRessource;
    }

    public void setNombreRessource(Integer nombreRessource) {
        this.nombreRessource = nombreRessource;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getCompetanceDemander() {
        return competanceDemander;
    }

    public void setCompetanceDemander(String competanceDemander) {
        this.competanceDemander = competanceDemander;
    }

    public Set<UserSopraDTO> getProjectMenbers() {
        return projectMenbers;
    }

    public void setProjectMenbers(Set<UserSopraDTO> projectMenbers) {
        this.projectMenbers = projectMenbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjetDTO)) {
            return false;
        }

        ProjetDTO projetDTO = (ProjetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjetDTO{" +
            "id=" + getId() +
            ", nomProjet='" + getNomProjet() + "'" +
            ", paysProjet='" + getPaysProjet() + "'" +
            ", regionProjet='" + getRegionProjet() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", nombreRessource=" + getNombreRessource() +
            ", dateFin='" + getDateFin() + "'" +
            ", competanceDemander='" + getCompetanceDemander() + "'" +
            ", projectMenbers=" + getProjectMenbers() +
            "}";
    }
}
