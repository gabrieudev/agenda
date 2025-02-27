package br.com.gabrieudev.agenda.infrastructrure.config.database;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UsersRolesModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.RoleRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UsersRolesRepository;

@Configuration
@Profile("dev")
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UsersRolesRepository usersRolesRepository;
    private final StatusRepository statusRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DatabaseLoader(UserRepository userRepository, UsersRolesRepository usersRolesRepository,
            StatusRepository statusRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.statusRepository = statusRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            UserModel user = new UserModel();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("adminpassword"));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setIsActive(Boolean.TRUE);

            RoleModel adminRole = new RoleModel();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Role de administrador");
            RoleModel userRole = new RoleModel();
            userRole.setName("USER");
            userRole.setDescription("Role de usuário comum");

            UsersRolesModel adminUsersRoles = new UsersRolesModel();
            adminUsersRoles.setRole(adminRole);
            adminUsersRoles.setUser(user);
            UsersRolesModel userUsersRoles = new UsersRolesModel();
            userUsersRoles.setRole(userRole);
            userUsersRoles.setUser(user);

            StatusModel inProgressStatus = new StatusModel();
            inProgressStatus.setName("Em andamento");
            StatusModel pendingStatus = new StatusModel();
            pendingStatus.setName("Pendente");
            StatusModel completedStatus = new StatusModel();
            completedStatus.setName("Concluido");

            userRepository.save(user);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            usersRolesRepository.save(userUsersRoles);
            usersRolesRepository.save(adminUsersRoles);
            statusRepository.save(inProgressStatus);
            statusRepository.save(pendingStatus);
            statusRepository.save(completedStatus);
        }
    }
}
