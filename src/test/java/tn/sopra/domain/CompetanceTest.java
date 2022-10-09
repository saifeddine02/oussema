package tn.sopra.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class CompetanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competance.class);
        Competance competance1 = new Competance();
        competance1.setId(1L);
        Competance competance2 = new Competance();
        competance2.setId(competance1.getId());
        assertThat(competance1).isEqualTo(competance2);
        competance2.setId(2L);
        assertThat(competance1).isNotEqualTo(competance2);
        competance1.setId(null);
        assertThat(competance1).isNotEqualTo(competance2);
    }
}
