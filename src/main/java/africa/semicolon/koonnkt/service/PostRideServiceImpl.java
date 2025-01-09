package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.data.model.Users;
import africa.semicolon.koonnkt.data.repository.PostRideRepo;
import africa.semicolon.koonnkt.data.repository.UsersRepo;
import africa.semicolon.koonnkt.dto.request.PostRideRequest;
import africa.semicolon.koonnkt.dto.request.UpdatePostRideRequest;
import africa.semicolon.koonnkt.dto.response.DeletePostRide;
import africa.semicolon.koonnkt.dto.response.PostRideResponse;
import africa.semicolon.koonnkt.dto.response.UpdatePostRideResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostRideServiceImpl implements PostRideService {
    @Autowired
    private UsersRepo usersRepository;
    @Autowired
    private PostRideRepo postRideRepo;
    @Autowired
    private NotificationService notificationService;

    @Override
    public PostRideResponse createPostRide(PostRideRequest postRideRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users driver = usersRepository.findByUsername(username);

        if (driver != null && driver.getRole().equals("DRIVER")) {
            PostRide postRide = new PostRide();
            postRide.setDriver(driver);
            postRide.setDestinationLocation(postRideRequest.getDestinationLocation());
            postRide.setDepartureLocation(postRideRequest.getDepartureLocation());
            postRide.setAvailableSeat(postRideRequest.getAvailableSeat());
            postRide.setDepartureTime(postRideRequest.getDepartureTime());
            postRideRepo.save(postRide);
            PostRideResponse postRideResponse = new PostRideResponse();
            postRideResponse.setMessage("Successfully created a new post ride");
            return postRideResponse;
        } else {
            throw new RuntimeException("User is not authorized to post a ride");
        }

    }

    @Override
    public List<PostRide> getRidePostByDriver() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users driver = usersRepository.findByUsername(username);
        if (driver != null && driver.getRole().equals("DRIVER")) {
            return postRideRepo.findByDriver(driver);
        } else {
            throw new RuntimeException("User is not authorized to view the posted ride");
        }
    }

    @Override
    public UpdatePostRideResponse updatePostRide(Long postRideId, UpdatePostRideRequest postRideRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users driver = usersRepository.findByUsername(username);

        Optional<PostRide> postRideExist = postRideRepo.findById(postRideId);
        if (postRideExist.isPresent()) {
            PostRide postRide = postRideExist.get();
            if (postRide.getDriver().equals(driver)){
                postRide.setDestinationLocation(postRideRequest.getDestinationLocation());
                postRide.setDepartureLocation(postRideRequest.getDepartureLocation());
                postRide.setAvailableSeat(postRideRequest.getAvailableSeat());
                postRide.setDepartureTime(postRideRequest.getDepartureTime());
                postRideRepo.save(postRide);

                UpdatePostRideResponse updatePostRideResponse = new UpdatePostRideResponse();
                updatePostRideResponse.setMessage("Successfully updated a post ride");
                return updatePostRideResponse;
            }else {
                throw new RuntimeException("User is not authorized to update a post ride");
            }

        }else {
            throw new RuntimeException("Ride does not exist");
        }


    }

    @Override
    public DeletePostRide deletePostRide(Long postRideId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users driver = usersRepository.findByUsername(username);
        Optional<PostRide> postRide = postRideRepo.findById(postRideId);
        if (postRide.isPresent()) {
            PostRide postRideExist = postRide.get();
            if (postRideExist.getDriver().equals(driver)){
                postRideRepo.delete(postRideExist);

                DeletePostRide deletePostRide = new DeletePostRide();
                deletePostRide.setMessage("Successfully deleted a post ride");
                return deletePostRide;
            }else {
                throw new RuntimeException("User is not authorized to delete a post ride");
            }
        }else {
            throw new RuntimeException("Ride does not exist");
        }

    }

    public List<PostRide> findMatchingRide(String destinationLocation, String departureLocation, Users passenger) {

        List<PostRide> matchingRides = postRideRepo.findByDepartureLocationAndDestinationLocation(normalizeString(departureLocation),
                normalizeString(destinationLocation));

        matchingRides.forEach(ride -> sendNotifications(ride, passenger));
        return matchingRides;
    }

    private String normalizeString(String input) {
        return input.trim().toLowerCase();
    }


    private void sendNotifications(PostRide ride, Users passenger) {

        Users driver = ride.getDriver();
        String driverEmail = driver.getEmail();
        String driverPhoneNumber = driver.getPhoneNumber();
        String passengerEmail = passenger.getEmail();
        String passengerPhoneNumber = passenger.getPhoneNumber();

        String subject = "Ride Match Found!";

        String driverMessage = "You have a new passenger request for your ride from " + ride.getDepartureLocation() +
                " to " + ride.getDestinationLocation() + " on " + ride.getDepartureTime() +
                "." + " Passenger Contact: " + passengerPhoneNumber;


        String passengerMessage = "A ride match has been found for your request from " + ride.getDepartureLocation() +
                " to " + ride.getDestinationLocation() + " on " + ride.getDepartureTime() +
                "." + " Driver Contact: " + driverPhoneNumber;


        notificationService.sendEmail(driverEmail, subject, driverMessage);
        notificationService.sendEmail(passengerEmail, subject, passengerMessage);


    }


}


