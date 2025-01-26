package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);

    @Query(value = """
            SELECT u FROM UserModel u
            WHERE
                :p1 IS NULL
                OR LOWER(u.email) LIKE LOWER(CONCAT('%',:p1,'%'))
                OR LOWER(u.firstName) LIKE LOWER(CONCAT('%',:p1,'%'))
                OR LOWER(u.lastName) LIKE LOWER(CONCAT('%',:p1,'%'))
            """)
    Page<UserModel> search(
            @Param("p1") String param,
            Pageable pageable);
}