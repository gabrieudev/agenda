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
            SELECT c.*
            FROM commitments c
            LEFT JOIN commitment_categories cc 
                ON c.commitment_category_id = cc.id
            LEFT JOIN statuses s 
                ON c.status_id = s.id
            WHERE c.user_id = :p1
            AND (:p2 IS NULL OR cc.id = :p2)
            AND (:p3 IS NULL OR s.id = :p3)
            AND (
                :p4 IS NULL OR
                (LOWER(c.title) LIKE '%' || LOWER(:p4) || '%' OR
                 LOWER(c.description) LIKE '%' || LOWER(:p4) || '%')
            )
                        """, nativeQuery = true)
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
