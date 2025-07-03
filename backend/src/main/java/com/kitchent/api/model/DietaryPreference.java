package com.kitchent.api.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "dietary_preferences")
public class DietaryPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String preference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getPreference() { return preference; }
    public void setPreference(String preference) { this.preference = preference; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
