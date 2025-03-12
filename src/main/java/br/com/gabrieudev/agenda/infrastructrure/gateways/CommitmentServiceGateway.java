package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentCategoryModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentCategoryRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;

@Service
public class CommitmentServiceGateway implements CommitmentGateway {
    private final CommitmentRepository commitmentRepository;
    private final CommitmentCategoryRepository commitmentCategoryRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    public CommitmentServiceGateway(CommitmentRepository commitmentRepository,
            CommitmentCategoryRepository commitmentCategoryRepository, StatusRepository statusRepository,
            UserRepository userRepository, JwtDecoder jwtDecoder) {
        this.commitmentRepository = commitmentRepository;
        this.commitmentCategoryRepository = commitmentCategoryRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    @CacheEvict(value = "Commitments", key = "#commitment.id")
    @Transactional
    public Commitment create(Commitment commitment, String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();

            UserModel user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

            commitment.setUser(user.toDomainObj());

            return commitmentRepository.save(CommitmentModel.from(commitment)).toDomainObj();
        } catch (Exception e) {
            throw new InvalidTokenException("Token inválido");
        }
    }

    @Override
    @CacheEvict(value = "Commitments", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Compromisso não encontrado");
        }

        commitmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCommitmentCategoryId(UUID commitmentCategoryId) {
        CommitmentCategoryModel commitmentCategory = commitmentCategoryRepository.findById(commitmentCategoryId)
            .orElseThrow(() -> new EntityNotFoundException("Categoria de compromisso não encontrada"));

        return commitmentRepository.existsByCategory(commitmentCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return commitmentRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByStatusId(UUID statusId) {
        StatusModel status = statusRepository.findById(statusId)
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));

        return commitmentRepository.existsByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commitment> findByDueDateBeforeAndStatusId(LocalDateTime dueDate, UUID statusId) {
        StatusModel status = statusRepository.findById(statusId)
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));

        return commitmentRepository.findByDueDateBeforeAndStatus(dueDate, status)
            .stream()
            .map(CommitmentModel::toDomainObj)
            .toList();
    }

    @Override
    @Cacheable(value = "Commitments", key = "#id")
    @Transactional(readOnly = true)
    public Commitment findById(UUID id) {
        return commitmentRepository.findById(id)
            .map(CommitmentModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Compromisso não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commitment> findByUserIdAndStatusIdAndUpdatedBetween(UUID userId, UUID statusId, LocalDateTime startDate,
            LocalDateTime endDate) {
        return commitmentRepository.findByUserIdAndStatusIdAndUpdatedBetween(userId, statusId, startDate, endDate)
            .stream()
            .map(CommitmentModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commitment> findByUserId(UUID userId, UUID commitmentCategoryId, UUID statusId, String param,
            Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return commitmentRepository.findByUserId(userId, commitmentCategoryId, statusId, param, pageable)
            .stream()
            .map(CommitmentModel::toDomainObj)
            .toList();
    }

    @Override
    @CacheEvict(value = "Commitments", key = "#commitment.id")
    @Transactional
    public Commitment update(Commitment commitment) {
        CommitmentModel commitmentToUpdate = commitmentRepository.findById(commitment.getId())
            .orElseThrow(() -> new EntityNotFoundException("Compromisso não encontrado"));

        commitmentToUpdate.setCategory(CommitmentCategoryModel.from(commitment.getCategory()));
        commitmentToUpdate.setStatus(StatusModel.from(commitment.getStatus()));
        commitmentToUpdate.setTitle(commitment.getTitle());
        commitmentToUpdate.setDescription(commitment.getDescription());
        commitmentToUpdate.setDueDate(commitment.getDueDate());
        commitmentToUpdate.setUpdatedAt(LocalDateTime.now());

        return commitmentRepository.save(commitmentToUpdate).toDomainObj();
    }
}
