package junit.filtro;

import filtro.FiltroExposición;
import org.junit.jupiter.api.Test;

import exposicion.*;

import static org.junit.jupiter.api.Assertions.*;

public class FiltroExposicionTest {

    @Test
    void testCumpleFiltro_ExposiciónTemporal_DeberíaRetornarTrue() {
        // Arrange
        ExposicionTemporal exposiciónTemporal = new ExposicionTemporal("1", "temporal", 22.8); // Crear una exposición temporal

        FiltroExposición filtro = new FiltroExposición();

        // Act
        boolean resultado = filtro.cumpleFiltro(exposiciónTemporal);

        // Assert
        assertTrue(resultado);
    }

    @Test
    void testCumpleFiltro_ExposiciónNoTemporal_DeberíaRetornarFalse() {
        // Arrange
        ExposicionPermanente exposiciónPermanente = new ExposicionPermanente("2", "permanente", 3.8); // Crear una exposición no temporal

        FiltroExposición filtro = new FiltroExposición();

        // Act
        boolean resultado = filtro.cumpleFiltro(exposiciónPermanente);

        // Assert
        assertFalse(resultado);
    }
}

