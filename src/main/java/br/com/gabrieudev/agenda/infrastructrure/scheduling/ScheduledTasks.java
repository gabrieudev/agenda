package br.com.gabrieudev.agenda.infrastructrure.scheduling;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationGuestModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.NotificationGuestRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.NotificationRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;
import br.com.gabrieudev.agenda.infrastructrure.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledTasks {
    private final NotificationRepository notificationRepository;
    private final CommitmentRepository commitmentRepository;
    private final StatusRepository statusRepository;
    private final NotificationGuestRepository notificationGuestRepository;
    private final EmailService emailService;

    public ScheduledTasks(NotificationRepository notificationRepository, CommitmentRepository commitmentRepository,
            StatusRepository statusRepository, NotificationGuestRepository notificationGuestRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.commitmentRepository = commitmentRepository;
        this.statusRepository = statusRepository;
        this.notificationGuestRepository = notificationGuestRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void sendNotifications() {
        List<NotificationModel> notifications = notificationRepository.findByDueDateBeforeAndIsSendedFalse(LocalDateTime.now());

        notifications.forEach(notification -> {
            List<NotificationGuestModel> notificationGuests = notificationGuestRepository.findByNotification(notification);

            sendEmailToCommitmentOwner(notification);
            sendEmailToGuests(notificationGuests, notification);

            notification.setIsSended(true);
            notificationRepository.save(notification);
        });
    }

    private void sendEmailToCommitmentOwner(NotificationModel notification) {
        emailService.sendEmail(notification.getCommitment().getUser().getEmail(), "Notificação de Agenda Digital", "Compromisso: " + notification.getCommitment().getTitle() + "\nMensagem: " +notification.getMessage());
    }

    private void sendEmailToGuests(List<NotificationGuestModel> notificationGuests, NotificationModel notification) {
        notificationGuests.stream()
            .filter(notificationGuest -> notificationGuest.getStatus().getName().equals("Concluido"))
            .forEach(notificationGuest -> emailService.sendEmail(notificationGuest.getUser().getEmail(), "Notificação de Agenda Digital", notification.getMessage()));
    }

    @Scheduled(fixedRate = 60000)
    public void updateCommitments() {
        log.info("Atualizando compromissos");

        StatusModel inProgressStatus = statusRepository.findByName("Em andamento")
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));

        StatusModel pendingStatus = statusRepository.findByName("Pendente")
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));

        List<CommitmentModel> commitments = commitmentRepository.findByDueDateBeforeAndStatus(LocalDateTime.now(), inProgressStatus);

        commitments.forEach(commitment -> {
            commitment.setStatus(pendingStatus);
            commitmentRepository.save(commitment);
        });
    }
}
