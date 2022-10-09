package tn.sopra.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class CompetanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetanceDTO.class);
        CompetanceDTO competanceDTO1 = new CompetanceDTO();
        competanceDTO1.setId(1L);
        CompetanceDTO competanceDTO2 = new CompetanceDTO();
        assertThat(competanceDTO1).isNotEqualTo(competanceDTO2);
        competanceDTO2.setId(competanceDTO1.getId());
        assertThat(competanceDTO1).isEqualTo(competanceDTO2);
        competanceDTO2.setId(2L);
        assertThat(competanceDTO1).isNotEqualTo(competanceDTO2);
        competanceDTO1.setId(null);
        assertThat(competanceDTO1).isNotEqualTo(competanceDTO2);
    }
}
