package br.com.foodhub.core.domain.entity.address;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressBaseTest {

    @Test
    void shouldCreateAddressSuccessfully() {
        AddressBase address = new AddressBase(
                "81880-390",
                "Rua Carlópolis",
                "Pinheirinho",
                "Curitiba",
                "PR",
                "Brasil"
        );

        assertEquals("81880390", address.getCep()); // normalizado
        assertEquals("Rua Carlópolis", address.getStreet());
        assertEquals("Pinheirinho", address.getNeighborhood());
        assertEquals("Curitiba", address.getCity());
        assertEquals("PR", address.getState());
        assertEquals("Brasil", address.getCountry());
    }

    @Test
    void shouldThrowExceptionWhenCepIsNull() {
        assertThrows(RequiredFieldException.class, () ->
                new AddressBase(
                        null,
                        "Rua",
                        "Bairro",
                        "Cidade",
                        "Estado",
                        "País"
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenCepIsBlank() {
        assertThrows(RequiredFieldException.class, () ->
                new AddressBase(
                        "   ",
                        "Rua",
                        "Bairro",
                        "Cidade",
                        "Estado",
                        "País"
                )
        );
    }


    @Test
    void shouldReconstituteAddressSuccessfully() {
        AddressBase address = AddressBase.reconstitute(
                "addr-123",
                "81880390",
                "Rua Persistida",
                "Bairro Persistido",
                "Cidade Persistida",
                "PR",
                "Brasil"
        );

        assertEquals("addr-123", address.getId());
        assertEquals("81880390", address.getCep());
        assertEquals("Rua Persistida", address.getStreet());
        assertEquals("Bairro Persistido", address.getNeighborhood());
        assertEquals("Cidade Persistida", address.getCity());
        assertEquals("PR", address.getState());
        assertEquals("Brasil", address.getCountry());
    }
}
