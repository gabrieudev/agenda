package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentCategoryModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;

@Repository
public interface CommitmentRepository extends JpaRepository<CommitmentModel, UUID> {
    boolean existsByCategory(CommitmentCategoryModel category);

    boolean existsByStatus(StatusModel status);

    List<CommitmentModel> findByDueDateBeforeAndStatus(LocalDateTime dueDate, StatusModel status);

    @Query(value = """
            SELECT c
            FROM CommitmentModel c
            WHERE c.user.id = :p1
            AND (:p2 IS NULL OR c.category.id = :p2)
            AND (:p3 IS NULL OR c.status.id = :p3)
            AND :p4 IS NULL
            OR LOWER(c.title) LIKE LOWER(CONCAT('%',:p4,'%'))
            OR LOWER(c.description) LIKE LOWER(CONCAT('%',:p4,'%'))
            """)
    Page<CommitmentModel> findByUserId(
            @Param("p1") UUID userId,
            @Param("p2") UUID commitmentCategoryId,
            @Param("p3") UUID statusId,
            @Param("p4") String param,
            Pageable pageable);

    @Query(value = """
            SELECT c
            FROM CommitmentModel c
            WHERE c.user.id = :p1
            AND c.status.id = :p2
            AND c.updatedAt BETWEEN :p3 AND :p4
            """)
    List<CommitmentModel> findByUserIdAndStatusIdAndUpdatedBetween(
            @Param("p1") UUID userId,
            @Param("p2") UUID statusId,
            @Param("p3") LocalDateTime startDate,
            @Param("p4") LocalDateTime endDate);
}
