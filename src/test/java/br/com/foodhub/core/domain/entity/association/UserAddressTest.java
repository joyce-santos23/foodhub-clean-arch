package br.com.foodhub.core.domain.entity.association;

import br.com.foodhub.core.domain.entity.association.UserAddress;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserAddressTest {

    @Test
    void shouldCreateUserAddressWithValidData() {
        UserAddress address = new UserAddress(
                "user-id",
                "address-id",
                "123",
                "Apto 1",
                true
        );

        assertThat(address.getId()).isNotNull();
        assertThat(address.getUserId()).isEqualTo("user-id");
        assertThat(address.isPrimary()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenNumberIsBlank() {
        assertThatThrownBy(() ->
                new UserAddress(
                        "user-id",
                        "address-id",
                        " ",
                        null,
                        false
                )
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldMarkAndUnmarkPrimaryCorrectly() {
        UserAddress address = new UserAddress(
                "user-id",
                "address-id",
                "123",
                null,
                false
        );

        address.markAsPrimary();
        assertThat(address.isPrimary()).isTrue();

        address.unmarkAsPrimary();
        assertThat(address.isPrimary()).isFalse();
    }

    @Test
    void shouldReconstituteUserAddressCorrectly() {
        String existingId = "address-123";
        UserAddress address = UserAddress.reconstitute(
                existingId, "user-id", "addr-id", "10", "Apto", true
        );

        assertThat(address.getId()).isEqualTo(existingId);
        assertThat(address.getUserId()).isEqualTo("user-id");
    }

    @Test
    void shouldUpdateAddressDetails() {
        UserAddress address = new UserAddress("u1", "a1", "10", null, false);

        address.update("20", "Casa nova");

        assertThat(address.getNumber()).isEqualTo("20");
        assertThat(address.getComplement()).isEqualTo("Casa nova");
    }
}
