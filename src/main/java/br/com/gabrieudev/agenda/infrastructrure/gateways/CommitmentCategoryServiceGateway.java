package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.gateways.CommitmentCategoryGateway;
import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentCategoryModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentCategoryRepository;

@Service
public class CommitmentCategoryServiceGateway implements CommitmentCategoryGateway {
    private final CommitmentCategoryRepository commitmentCategoryRepository;

    public CommitmentCategoryServiceGateway(CommitmentCategoryRepository commitmentCategoryRepository) {
        this.commitmentCategoryRepository = commitmentCategoryRepository;
    }

    @Override
    @CacheEvict(value = "CommitmentCategories", key = "#commitmentCategory.id")
    @Transactional
    public CommitmentCategory create(CommitmentCategory commitmentCategory) {
        return commitmentCategoryRepository.save(CommitmentCategoryModel.from(commitmentCategory)).toDomainObj();
    }

    @Override
    @CacheEvict(value = "CommitmentCategories", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        if (!commitmentCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("CommitmentCategory não encontrado");
        }

        commitmentCategoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return commitmentCategoryRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return commitmentCategoryRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommitmentCategory> findAll() {
        return commitmentCategoryRepository.findAll().stream()
            .map(CommitmentCategoryModel::toDomainObj)
            .toList();
    }

    @Override
    @Cacheable(value = "CommitmentCategories", key = "#id")
    @Transactional(readOnly = true)
    public CommitmentCategory findById(UUID id) {
        return commitmentCategoryRepository.findById(id)
            .map(CommitmentCategoryModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("CommitmentCategory não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public CommitmentCategory findByName(String name) {
        return commitmentCategoryRepository.findByName(name)
            .map(CommitmentCategoryModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("CommitmentCategory não encontrado"));
    }

    @Override
    @CacheEvict(value = "CommitmentCategories", key = "#commitmentCategory.id")
    @Transactional
    public CommitmentCategory update(CommitmentCategory commitmentCategory) {
        CommitmentCategoryModel commitmentCategoryToUpdate = commitmentCategoryRepository.findById(commitmentCategory.getId())
            .orElseThrow(() -> new EntityNotFoundException("CommitmentCategory não encontrado"));

        commitmentCategoryToUpdate.update(commitmentCategory);

        return commitmentCategoryRepository.save(commitmentCategoryToUpdate).toDomainObj();
    }
}
