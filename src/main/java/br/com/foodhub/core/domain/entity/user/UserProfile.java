package br.com.foodhub.core.domain.entity.user;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserProfile {

    private final Map<String, Object> customFields;

    public UserProfile(Map<String, Object> customFields) {
        this.customFields = customFields != null
                ? Map.copyOf(customFields)
                : Map.of();
    }


    public static UserProfile empty() {
        return new UserProfile(Map.of());
    }

    public boolean isEmpty() {
        return customFields.isEmpty();
    }

    public Map<String, Object> toMap() {
        return Map.copyOf(customFields);
    }
}

