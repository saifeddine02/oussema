package tn.sopra.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class UserSopraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSopraDTO.class);
        UserSopraDTO userSopraDTO1 = new UserSopraDTO();
        userSopraDTO1.setId(1L);
        UserSopraDTO userSopraDTO2 = new UserSopraDTO();
        assertThat(userSopraDTO1).isNotEqualTo(userSopraDTO2);
        userSopraDTO2.setId(userSopraDTO1.getId());
        assertThat(userSopraDTO1).isEqualTo(userSopraDTO2);
        userSopraDTO2.setId(2L);
        assertThat(userSopraDTO1).isNotEqualTo(userSopraDTO2);
        userSopraDTO1.setId(null);
        assertThat(userSopraDTO1).isNotEqualTo(userSopraDTO2);
    }
}
