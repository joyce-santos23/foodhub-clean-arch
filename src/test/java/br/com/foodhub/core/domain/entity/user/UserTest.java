package br.com.foodhub.core.domain.entity.user;

import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.association.UserRestaurant;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import br.com.foodhub.core.domain.exceptions.user.InvalidCpfException;
import br.com.foodhub.core.domain.exceptions.user.InvalidEmailException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    /* =========================
       CONSTRUCTOR
       ========================= */

    @Test
    void shouldCreateUserSuccessfully() {
        User user = createValidUser();

        assertEquals("John Doe", user.getName());
        assertEquals("john@doe.com", user.getEmail());
        assertEquals("11999999999", user.getPhone());
        assertNotNull(user.getUserType());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(RequiredFieldException.class,
                () -> new User(null, "a@b.com", "119", "123", ownerType()));
    }

    /* =========================
       CHANGE NAME
       ========================= */

    @Test
    void shouldChangeNameSuccessfully() {
        User user = createValidUser();

        user.changeName("New Name");

        assertEquals("New Name", user.getName());
    }

    @Test
    void shouldThrowExceptionWhenChangingNameToBlank() {
        User user = createValidUser();

        assertThrows(RequiredFieldException.class,
                () -> user.changeName(" "));
    }

    /* =========================
       CHANGE EMAIL
       ========================= */

    @Test
    void shouldChangeAndNormalizeEmail() {
        User user = createValidUser();

        user.changeEmail("  TEST@EMAIL.COM ");

        assertEquals("test@email.com", user.getEmail());
    }

    @Test
    void shouldThrowExceptionForInvalidEmail() {
        User user = createValidUser();

        assertThrows(InvalidEmailException.class,
                () -> user.changeEmail("invalid-email"));
    }

    /* =========================
       CPF
       ========================= */

    @Test
    void shouldDefineCpfSuccessfully() {
        User user = createValidUser();

        user.defineCpf("123.456.789-09");

        assertEquals("12345678909", user.getCpf());
    }

    @Test
    void shouldNotAllowCpfRedefinition() {
        User user = createValidUser();
        user.defineCpf("12345678909");

        assertThrows(ResourceConflictException.class,
                () -> user.defineCpf("98765432100"));
    }

    @Test
    void shouldThrowExceptionForInvalidCpf() {
        User user = createValidUser();

        assertThrows(InvalidCpfException.class,
                () -> user.defineCpf("123"));
    }

    /* =========================
       CREATE RESTAURANT
       ========================= */

    @Test
    void ownerCanCreateRestaurant() {
        User owner = createUserWithType("OWNER");

        assertDoesNotThrow(owner::ensureCanCreateRestaurant);
    }

    @Test
    void nonOwnerCannotCreateRestaurant() {
        User customer = createUserWithType("CUSTOMER");

        assertThrows(BusinessRuleViolationException.class,
                customer::ensureCanCreateRestaurant);
    }

    /* =========================
       MANAGE RESTAURANT
       ========================= */

    @Test
    void customerCannotManageRestaurant() {
        User customer = createUserWithType("CUSTOMER");
        Restaurant restaurant = mockRestaurant("r1", "owner1");

        assertThrows(BusinessRuleViolationException.class,
                () -> customer.ensureCanManageRestaurant(restaurant));
    }

    @Test
    void ownerCanManageOwnRestaurant() {
        UserType ownerType = UserType.reconstitute("type-owner", "OWNER");

        User owner = User.reconstitute(
                "owner1",                 // 👈 id já nasce correto
                "Owner User",
                "owner@email.com",
                "11999999999",
                null,
                "password",
                ownerType
        );

        Restaurant restaurant = mockRestaurant("r1", "owner1");

        assertDoesNotThrow(() ->
                owner.ensureCanManageRestaurant(restaurant)
        );
    }


    @Test
    void ownerCannotManageOthersRestaurant() {
        User owner = createUserWithTypeAndId("OWNER", "owner1");

        Restaurant restaurant = mockRestaurant("r1", "owner2");

        assertThrows(BusinessRuleViolationException.class,
                () -> owner.ensureCanManageRestaurant(restaurant));
    }

    @Test
    void restaurantRelatedUserMustBeLinked() {
        User staff = createUserWithType("WAITER");
        staff.addRestaurant(new UserRestaurant("u1", "r1", "WAITER"));

        Restaurant restaurant = mockRestaurant("r1", "owner1");

        assertDoesNotThrow(() ->
                staff.ensureCanManageRestaurant(restaurant));
    }

    @Test
    void shouldRemoveRestaurantLinkSuccessfully() {
        User user = createValidUser();

        UserRestaurant link = new UserRestaurant("u1", "r1", "WAITER");
        user.addRestaurant(link);

        assertEquals(1, user.getRestaurants().size());

        user.removeRestaurantLink("r1");

        assertTrue(user.getRestaurants().isEmpty());
    }


    /* =========================
       RESTAURANT LINKS
       ========================= */

    @Test
    void shouldAddRestaurantLink() {
        User user = createValidUser();
        UserRestaurant link = new UserRestaurant("u1", "r1", "WAITER");

        user.addRestaurant(link);

        assertEquals(1, user.getRestaurants().size());
    }

    @Test
    void shouldThrowExceptionWhenAddingNullRestaurant() {
        User user = createValidUser();

        assertThrows(RequiredFieldException.class,
                () -> user.addRestaurant(null));
    }

    /* =========================
       ADDRESSES
       ========================= */

    @Test
    void shouldAddAddressSuccessfully() {
        User user = createValidUser();

        UserAddress address = new UserAddress(
                "u1", "a1", "123", null, false
        );

        user.addAddress(address);

        assertEquals(1, user.getAddresses().size());
    }

    @Test
    void shouldNotAllowDuplicateAddress() {
        User user = createValidUser();

        UserAddress address = new UserAddress(
                "u1", "a1", "123", null, false
        );

        user.addAddress(address);

        assertThrows(ResourceConflictException.class,
                () -> user.addAddress(address));
    }

    @Test
    void shouldEnsureOnlyOnePrimaryAddress() {
        User user = createValidUser();

        UserAddress a1 = new UserAddress("u1", "a1", "10", null, true);
        UserAddress a2 = new UserAddress("u1", "a2", "20", null, true);

        user.addAddress(a1);
        user.addAddress(a2);

        assertFalse(a1.isPrimary());
        assertTrue(a2.isPrimary());
    }

    @Test
    void shouldThrowExceptionWhenDefiningPrimaryForUnknownAddress() {
        User user = createValidUser();

        assertThrows(AddressNotBelongsToUserException.class,
                () -> user.definePrimaryAddress("invalid-id"));
    }

    /* =========================
       HELPERS
       ========================= */

    private User createValidUser() {
        return new User(
                "John Doe",
                "john@doe.com",
                "11999999999",
                "123456",
                ownerType()
        );
    }

    private User createUserWithType(String type) {
        return new User(
                "User",
                "user@test.com",
                "11999999999",
                "123456",
                new UserType(type)
        );
    }

    private User createUserWithTypeAndId(String type, String id) {
        return User.reconstitute(
                id,
                "User",
                "user@test.com",
                "11999999999",
                null,
                "123456",
                UserType.reconstitute("type-" + type.toLowerCase(), type)
        );
    }


    private UserType ownerType() {
        return new UserType("OWNER");
    }

    private Restaurant mockRestaurant(String id, String ownerId) {
        return Restaurant.reconstitute(
                id,
                "Restaurant",
                "12345678000190",
                "Italiana",
                ownerId,
                "addressId",
                "100",
                null,
                List.of(),
                List.of()
        );
    }



}
