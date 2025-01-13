package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.data.model.Users;
import africa.semicolon.koonnkt.data.repository.PostRideRepo;
import africa.semicolon.koonnkt.data.repository.RequestRideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
    @Autowired
    private  NotificationService notificationService;

    @Autowired
    private PostRideRepo postRideRepo;

    @Autowired
    private RequestRideRepo requestRideRepo;


    public void createMatch(RequestRide requestRide, PostRide postRide){
        if(requestRide.getDepartureLocation().equals(postRide.getDepartureLocation())
                && requestRide.getDestinationLocation().equals(postRide.getDestinationLocation())){

           if(!requestRide.isNotified() && !postRide.isNotified()){

               sendNotification(requestRide, postRide);

               requestRide.setNotified(true);
               postRide.setNotified(true);

               requestRideRepo.save(requestRide);
               postRideRepo.save(postRide);

           }


        }
    }


    private void  sendNotification(RequestRide requestRide, PostRide postRide){

       Users driver = postRide.getDriver();
       Users passenger = requestRide.getPassenger();


        String driverMessage = "You have a new passenger request for your ride! Passenger" + passenger.getUsername() +
                " is headed to " + requestRide.getDestinationLocation() + " from " + requestRide.getDepartureLocation() +
                "." + " Passenger Contact: " + passenger.getPhoneNumber() + "Please contact each other for more details.";


        String passengerMessage = "A ride match has been found for your request! Driver " + driver.getUsername() +
                " is headed to " + postRide.getDestinationLocation() + " from " + postRide.getDepartureLocation() +
                "." + " Driver Contact: " + driver.getPhoneNumber() + "Please contact each other for more details.";

        String subject = "Route Match Notification from KOONNKT";


        notificationService.sendEmail(passenger.getEmail(), subject, passengerMessage);
          notificationService.sendEmail(driver.getEmail(), subject, driverMessage);


    }
}
