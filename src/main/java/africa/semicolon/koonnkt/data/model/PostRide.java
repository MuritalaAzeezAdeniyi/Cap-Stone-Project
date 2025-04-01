package africa.semicolon.koonnkt.data.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
public class PostRide {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String departureLocation;
    private String destinationLocation;
    private int availableSeat;
    private LocalDateTime departureTime;
    private boolean notified;
    private BigDecimal price;


    @ManyToOne
//    @JoinColumn(name = "driver_id")
    private Users driver;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartureLocation() {
        return this.departureLocation;
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

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Users getDriver() {
        return driver;
    }

    public void setDriver(Users driver) {
        this.driver = driver;
    }
}
