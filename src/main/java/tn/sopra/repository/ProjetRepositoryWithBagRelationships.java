package tn.sopra.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import tn.sopra.domain.Projet;

public interface ProjetRepositoryWithBagRelationships {
    Optional<Projet> fetchBagRelationships(Optional<Projet> projet);

    List<Projet> fetchBagRelationships(List<Projet> projets);

    Page<Projet> fetchBagRelationships(Page<Projet> projets);
}
