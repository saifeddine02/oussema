package tn.sopra.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class ResponsableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsableDTO.class);
        ResponsableDTO responsableDTO1 = new ResponsableDTO();
        responsableDTO1.setId(1L);
        ResponsableDTO responsableDTO2 = new ResponsableDTO();
        assertThat(responsableDTO1).isNotEqualTo(responsableDTO2);
        responsableDTO2.setId(responsableDTO1.getId());
        assertThat(responsableDTO1).isEqualTo(responsableDTO2);
        responsableDTO2.setId(2L);
        assertThat(responsableDTO1).isNotEqualTo(responsableDTO2);
        responsableDTO1.setId(null);
        assertThat(responsableDTO1).isNotEqualTo(responsableDTO2);
    }
}
