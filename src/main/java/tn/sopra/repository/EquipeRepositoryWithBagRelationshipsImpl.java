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
import tn.sopra.domain.Equipe;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class EquipeRepositoryWithBagRelationshipsImpl implements EquipeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Equipe> fetchBagRelationships(Optional<Equipe> equipe) {
        return equipe.map(this::fetchEquipeusers);
    }

    @Override
    public Page<Equipe> fetchBagRelationships(Page<Equipe> equipes) {
        return new PageImpl<>(fetchBagRelationships(equipes.getContent()), equipes.getPageable(), equipes.getTotalElements());
    }

    @Override
    public List<Equipe> fetchBagRelationships(List<Equipe> equipes) {
        return Optional.of(equipes).map(this::fetchEquipeusers).orElse(Collections.emptyList());
    }

    Equipe fetchEquipeusers(Equipe result) {
        return entityManager
            .createQuery("select equipe from Equipe equipe left join fetch equipe.equipeusers where equipe is :equipe", Equipe.class)
            .setParameter("equipe", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Equipe> fetchEquipeusers(List<Equipe> equipes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, equipes.size()).forEach(index -> order.put(equipes.get(index).getId(), index));
        List<Equipe> result = entityManager
            .createQuery(
                "select distinct equipe from Equipe equipe left join fetch equipe.equipeusers where equipe in :equipes",
                Equipe.class
            )
            .setParameter("equipes", equipes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
