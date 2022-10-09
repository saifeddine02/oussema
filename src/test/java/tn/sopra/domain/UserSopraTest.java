package tn.sopra.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.sopra.web.rest.TestUtil;

class UserSopraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSopra.class);
        UserSopra userSopra1 = new UserSopra();
        userSopra1.setId(1L);
        UserSopra userSopra2 = new UserSopra();
        userSopra2.setId(userSopra1.getId());
        assertThat(userSopra1).isEqualTo(userSopra2);
        userSopra2.setId(2L);
        assertThat(userSopra1).isNotEqualTo(userSopra2);
        userSopra1.setId(null);
        assertThat(userSopra1).isNotEqualTo(userSopra2);
    }
}
