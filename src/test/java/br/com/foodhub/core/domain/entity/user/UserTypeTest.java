package br.com.foodhub.core.domain.entity.user;

import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {

    /* =========================
       CONSTRUCTOR
       ========================= */

    @Test
    void shouldNormalizeNameToUppercase() {
        UserType type = new UserType(" owner ");

        assertEquals("OWNER", type.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(RequiredFieldException.class,
                () -> new UserType(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(RequiredFieldException.class,
                () -> new UserType(" "));
    }

    /* =========================
       SYSTEM TYPE RULES
       ========================= */

    @Test
    void ownerIsSystemType() {
        UserType owner = new UserType("OWNER");

        assertTrue(owner.isSystemType());
    }

    @Test
    void customerIsSystemType() {
        UserType customer = new UserType("CUSTOMER");

        assertTrue(customer.isSystemType());
    }

    @Test
    void customTypeIsNotSystemType() {
        UserType waiter = new UserType("WAITER");

        assertFalse(waiter.isSystemType());
    }

    /* =========================
       RENAME
       ========================= */

    @Test
    void shouldRenameCustomUserType() {
        UserType waiter = new UserType("WAITER");

        waiter.rename("chef");

        assertEquals("chef", waiter.getName());
    }

    @Test
    void shouldNotAllowRenamingSystemType() {
        UserType owner = new UserType("OWNER");

        assertThrows(BusinessRuleViolationException.class,
                () -> owner.rename("ADMIN"));
    }
}

