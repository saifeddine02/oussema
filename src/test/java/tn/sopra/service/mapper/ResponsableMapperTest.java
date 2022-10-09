package tn.sopra.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResponsableMapperTest {

    private ResponsableMapper responsableMapper;

    @BeforeEach
    public void setUp() {
        responsableMapper = new ResponsableMapperImpl();
    }
}
