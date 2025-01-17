package africa.semicolon.koonnkt.service;


import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.data.model.Users;
import africa.semicolon.koonnkt.data.repository.RequestRideRepo;
import africa.semicolon.koonnkt.data.repository.UsersRepo;
import africa.semicolon.koonnkt.dto.request.CreateRideRequest;
import africa.semicolon.koonnkt.dto.request.UpdateRequestRideRequest;
import africa.semicolon.koonnkt.dto.response.ResponseRequestRide;
import africa.semicolon.koonnkt.dto.response.UpdateRequestRideResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestRideImpl implements RequestRideService {
    @Autowired
    private UsersRepo usersRepository;
    @Autowired
    private RequestRideRepo requestRideRepo;

    @Autowired
    private PostRideServiceImpl postRideService;
    @Override
    public ResponseRequestRide createRideRequest(CreateRideRequest createRideRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users passenger = usersRepository.findByUsername(username);

        if (passenger != null && passenger.getRole().equals("PASSENGER")){
            RequestRide requestRide = new RequestRide();
            requestRide.setPassenger(passenger);
            requestRide.setDepartureLocation(createRideRequest.getDepartureLocation());
            requestRide.setDestinationLocation(createRideRequest.getDestinationLocation());
            requestRide.setDepartureTime(createRideRequest.getDepartureTime());
            requestRideRepo.save(requestRide);

            ResponseRequestRide responseRequestRide = new ResponseRequestRide();
            responseRequestRide.setMessage("Successfully requested ride");
            return responseRequestRide;
        }else {
            throw new RuntimeException("User is not authorized to Request Ride");
        }

    }

    @Override
    public UpdateRequestRideResponse updateRideRequest(Long requestRideId, UpdateRequestRideRequest updateRideRequest) {
         Authentication authentication = SecurityContextHolder.getContext()
                 .getAuthentication();
         String username = authentication.getName();
         Users passenger = usersRepository.findByUsername(username);

         Optional<RequestRide> requestRide = requestRideRepo.findById(requestRideId);
         if(requestRide.isPresent()){
             RequestRide request = requestRide.get();
             if(request.getPassenger().equals(passenger)){
                 request.setDepartureLocation(updateRideRequest.getDestinationLocation());
                 request.setDestinationLocation(updateRideRequest.getDestinationLocation());
                 request.setDepartureTime(LocalDateTime.now());
                 requestRideRepo.save(request);

                 UpdateRequestRideResponse updateRequestRideResponse = new UpdateRequestRideResponse();
                 updateRequestRideResponse.setMessage("Successfully updated ride");
                 return updateRequestRideResponse;
             }else {
                 throw new RuntimeException("User is not authorized to Request Ride");
             }
         }else {
             throw new RuntimeException("request ride not found");
         }

    }

    @Override
    public List<RequestRide> getRequestRidesByPassenger() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users passenger = usersRepository.findByUsername(username);
        if(passenger != null && passenger.getRole().equals("PASSENGER")){
            return requestRideRepo.findByPassenger(passenger);

        }else {
            throw new RuntimeException("User is not authorized to view Request Ride");
        }

    }
}
