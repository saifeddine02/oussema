package tn.sopra.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandeCongeMapperTest {

    private DemandeCongeMapper demandeCongeMapper;

    @BeforeEach
    public void setUp() {
        demandeCongeMapper = new DemandeCongeMapperImpl();
    }
}
