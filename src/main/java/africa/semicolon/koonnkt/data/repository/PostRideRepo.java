package africa.semicolon.koonnkt.data.repository;

import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRideRepo extends JpaRepository<PostRide, Long> {
    List<PostRide> findByDepartureLocationAndDestinationLocation (String departureLocation, String destinationLocation);
    List<PostRide> findByDriver(Users driver);
}
