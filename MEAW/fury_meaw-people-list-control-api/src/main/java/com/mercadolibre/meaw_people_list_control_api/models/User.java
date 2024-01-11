package com.mercadolibre.meaw_people_list_control_api.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;

    @Column(name = "nickname", length = 32, nullable = false, unique = true)
    private String nickname;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;


    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDate updatedAt;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "stacks", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "stack")
    private List<String> stack = new ArrayList<>();

    public User(String nickname, String name, LocalDate birthdate) {
        this.uuid = UUID.randomUUID();
        this.nickname = nickname;
        this.name = name;
        this.birthdate = birthdate;
    }

    public User() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null||getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid)&&Objects.equals(nickname, user.nickname)&&Objects.equals(name, user.name)&&Objects.equals(birthdate, user.birthdate)&&Objects.equals(createdAt, user.createdAt)&&Objects.equals(updatedAt, user.updatedAt)&&Objects.equals(stack, user.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nickname, name, birthdate, createdAt, updatedAt, stack);
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", stack=" + stack +
                '}';
    }
}

