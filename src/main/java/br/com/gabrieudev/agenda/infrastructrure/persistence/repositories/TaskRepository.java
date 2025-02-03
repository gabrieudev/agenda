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

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
        @Query(value = """
                        SELECT t
                        FROM tasks t
                        LEFT JOIN commitments c
                            ON t.commitment_id = c.id
                        LEFT JOIN statuses s
                            ON t.status_id = s.id
                        WHERE t.commitment_id = :p1
                        AND (:p2 IS NULL OR s.id = :p2)
                        AND (:p3 IS NULL OR (LOWER(t.title) LIKE '%' || LOWER(:p3) || '%' OR LOWER(t.description) LIKE '%' || LOWER(:p3) || '%'))
                        """, nativeQuery = true)
        Page<TaskModel> findByCommitmentId(
                        @Param("p1") UUID commitmentId,
                        @Param("p2") UUID statusId,
                        @Param("p3") String searchParam,
                        Pageable pageable);

        @Query(value = """
                        SELECT t
                        FROM TaskModel t
                        INNER JOIN CommitmentModel c
                            ON t.commitment.id = c.id
                        WHERE c.user.id = :p1
                        AND t.status.id = :p2
                        AND t.updatedAt BETWEEN :p3 AND :p4
                        """)
        List<TaskModel> findByUserIdAndStatusIdAndUpdatedBetween(
                        @Param("p1") UUID userId,
                        @Param("p2") UUID statusId,
                        @Param("p3") LocalDateTime startDate,
                        @Param("p4") LocalDateTime endDate);

        boolean existsByStatus(StatusModel status);
}
