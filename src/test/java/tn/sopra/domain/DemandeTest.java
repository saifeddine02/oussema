package tn.sopra.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class DemandeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demande.class);
        Demande demande1 = new Demande();
        demande1.setId(1L);
        Demande demande2 = new Demande();
        demande2.setId(demande1.getId());
        assertThat(demande1).isEqualTo(demande2);
        demande2.setId(2L);
        assertThat(demande1).isNotEqualTo(demande2);
        demande1.setId(null);
        assertThat(demande1).isNotEqualTo(demande2);
    }
}
