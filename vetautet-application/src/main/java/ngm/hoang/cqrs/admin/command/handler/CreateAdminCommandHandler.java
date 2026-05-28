package ngm.hoang.cqrs.admin.command.handler;

import lombok.RequiredArgsConstructor;
import ngm.hoang.cqrs.admin.command.CreateAdminCommand;
import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.model.Admin;
import ngm.hoang.provider.Hasher;
import ngm.hoang.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdminCommandHandler {
    private final AdminRepository adminRepository;
    private final Hasher hasher;

    public Admin handle(CreateAdminCommand command){
        // Check already existed?
        this.validateUniqueUsername(command.username());

        // Hashing password
        String passwordHashed = this.hashPassword(command.password());

        // Create
        Admin model = this.createAdmin(command.username(), passwordHashed);

        // save and return
        return adminRepository.save(model);
    }

    private void validateUniqueUsername(String username){
        if (adminRepository.existsByUsername(username)) {
            throw new AlreadyExistsDomainException(Admin.class, "username", username);
        }
    }

    private String hashPassword(String password){
        return hasher.hash(password);
    }

    private Admin createAdmin(String username, String passwordHashed){
        return Admin.create(username, passwordHashed);
    };
}
