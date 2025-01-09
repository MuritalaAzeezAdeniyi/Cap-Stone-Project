package africa.semicolon.koonnkt.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class RequestRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departureLocation;
    private String destinationLocation;
    private LocalDateTime departureTime;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Users passenger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Users getPassenger() {
        return passenger;
    }

    public void setPassenger(Users passenger) {
        this.passenger = passenger;
    }
}
