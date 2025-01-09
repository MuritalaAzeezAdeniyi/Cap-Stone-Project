package africa.semicolon.koonnkt.data.repository;

import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface RequestRideRepo extends JpaRepository<RequestRide, Long> {
    List<RequestRide> findByPassenger(Users passenger);
}
