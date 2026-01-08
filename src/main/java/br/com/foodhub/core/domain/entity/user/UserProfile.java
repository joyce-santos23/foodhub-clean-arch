package br.com.foodhub.core.domain.entity.user;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class UserProfile {
    private Map<String, Object> customFields;
}
