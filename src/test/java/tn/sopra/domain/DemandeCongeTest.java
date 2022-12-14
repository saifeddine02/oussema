package tn.sopra.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class DemandeCongeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeConge.class);
        DemandeConge demandeConge1 = new DemandeConge();
        demandeConge1.setId(1L);
        DemandeConge demandeConge2 = new DemandeConge();
        demandeConge2.setId(demandeConge1.getId());
        assertThat(demandeConge1).isEqualTo(demandeConge2);
        demandeConge2.setId(2L);
        assertThat(demandeConge1).isNotEqualTo(demandeConge2);
        demandeConge1.setId(null);
        assertThat(demandeConge1).isNotEqualTo(demandeConge2);
    }
}
