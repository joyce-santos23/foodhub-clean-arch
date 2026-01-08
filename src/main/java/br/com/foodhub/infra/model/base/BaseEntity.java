package br.com.foodhub.infra.model.base;

import java.time.LocalDateTime;

public abstract class BaseEntity {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
