package africa.semicolon.koonnkt.controller;

import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.dto.request.CreateRideRequest;
import africa.semicolon.koonnkt.dto.request.UpdateRequestRideRequest;
import africa.semicolon.koonnkt.dto.response.ApiResponse;
import africa.semicolon.koonnkt.dto.response.ResponseRequestRide;
import africa.semicolon.koonnkt.dto.response.UpdateRequestRideResponse;
import africa.semicolon.koonnkt.service.RequestRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rideRequest")
@CrossOrigin(origins = "*")
public class RequestRideController {
    @Autowired
    private RequestRideService requestRideService;

    @PostMapping("/createRideRequest")

    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity <?> createRideRequest(@RequestBody CreateRideRequest createRideRequest) {
        try{
            ResponseRequestRide responseRequestRide = requestRideService.createRideRequest(createRideRequest);
            return new ResponseEntity <>  (new ApiResponse(true,responseRequestRide), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity <>  (e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateRideRequest/{requestRideId}")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<?>  updateRideRequest(@PathVariable Long requestRideId,@RequestBody UpdateRequestRideRequest updateRequestRideRequest) {
        try{
            UpdateRequestRideResponse response = requestRideService.updateRideRequest(requestRideId,updateRequestRideRequest);
            return new ResponseEntity <>  (new ApiResponse(true,response), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity <>  (e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/passenger")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<List<RequestRide>> getRequestRidesByPassenger(){
        try{
            List<RequestRide> requestRides = requestRideService.getRequestRidesByPassenger();
            return new ResponseEntity<>(requestRides, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
