package tn.sopra.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetanceMapperTest {

    private CompetanceMapper competanceMapper;

    @BeforeEach
    public void setUp() {
        competanceMapper = new CompetanceMapperImpl();
    }
}
