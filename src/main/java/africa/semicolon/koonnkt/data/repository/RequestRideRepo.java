package africa.semicolon.koonnkt.data.repository;

import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RequestRideRepo extends JpaRepository<RequestRide, Long> {
    List<RequestRide> findByPassenger(Users passenger);
}
