package africa.semicolon.koonnkt.data.repository;

import africa.semicolon.koonnkt.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Users findUserById(Long id);
}
