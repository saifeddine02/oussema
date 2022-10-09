package tn.sopra.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class DemandeCongeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeCongeDTO.class);
        DemandeCongeDTO demandeCongeDTO1 = new DemandeCongeDTO();
        demandeCongeDTO1.setId(1L);
        DemandeCongeDTO demandeCongeDTO2 = new DemandeCongeDTO();
        assertThat(demandeCongeDTO1).isNotEqualTo(demandeCongeDTO2);
        demandeCongeDTO2.setId(demandeCongeDTO1.getId());
        assertThat(demandeCongeDTO1).isEqualTo(demandeCongeDTO2);
        demandeCongeDTO2.setId(2L);
        assertThat(demandeCongeDTO1).isNotEqualTo(demandeCongeDTO2);
        demandeCongeDTO1.setId(null);
        assertThat(demandeCongeDTO1).isNotEqualTo(demandeCongeDTO2);
    }
}
