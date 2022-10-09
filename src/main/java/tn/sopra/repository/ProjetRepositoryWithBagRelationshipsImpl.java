package tn.sopra.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tn.sopra.domain.Projet;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProjetRepositoryWithBagRelationshipsImpl implements ProjetRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Projet> fetchBagRelationships(Optional<Projet> projet) {
        return projet.map(this::fetchProjectMenbers);
    }

    @Override
    public Page<Projet> fetchBagRelationships(Page<Projet> projets) {
        return new PageImpl<>(fetchBagRelationships(projets.getContent()), projets.getPageable(), projets.getTotalElements());
    }

    @Override
    public List<Projet> fetchBagRelationships(List<Projet> projets) {
        return Optional.of(projets).map(this::fetchProjectMenbers).orElse(Collections.emptyList());
    }

    Projet fetchProjectMenbers(Projet result) {
        return entityManager
            .createQuery("select projet from Projet projet left join fetch projet.projectMenbers where projet is :projet", Projet.class)
            .setParameter("projet", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Projet> fetchProjectMenbers(List<Projet> projets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, projets.size()).forEach(index -> order.put(projets.get(index).getId(), index));
        List<Projet> result = entityManager
            .createQuery(
                "select distinct projet from Projet projet left join fetch projet.projectMenbers where projet in :projets",
                Projet.class
            )
            .setParameter("projets", projets)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
