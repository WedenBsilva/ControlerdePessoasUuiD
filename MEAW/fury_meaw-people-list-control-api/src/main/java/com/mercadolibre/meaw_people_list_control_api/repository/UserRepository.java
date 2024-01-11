package com.mercadolibre.meaw_people_list_control_api.repository;

import com.mercadolibre.meaw_people_list_control_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByNameContainingIgnoreCase(String termoBusca);

    boolean existsByNickname(String nickname);
}
