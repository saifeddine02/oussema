package tn.sopra.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import tn.sopra.domain.Equipe;

public interface EquipeRepositoryWithBagRelationships {
    Optional<Equipe> fetchBagRelationships(Optional<Equipe> equipe);

    List<Equipe> fetchBagRelationships(List<Equipe> equipes);

    Page<Equipe> fetchBagRelationships(Page<Equipe> equipes);
}
