package tn.sopra.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSopraMapperTest {

    private UserSopraMapper userSopraMapper;

    @BeforeEach
    public void setUp() {
        userSopraMapper = new UserSopraMapperImpl();
    }
}
