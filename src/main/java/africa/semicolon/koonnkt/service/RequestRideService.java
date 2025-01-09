package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.dto.request.CreateRideRequest;
import africa.semicolon.koonnkt.dto.request.UpdateRequestRideRequest;
import africa.semicolon.koonnkt.dto.response.ResponseRequestRide;
import africa.semicolon.koonnkt.dto.response.UpdateRequestRideResponse;

import java.util.List;

public interface RequestRideService {
    ResponseRequestRide createRideRequest(CreateRideRequest createRideRequest);
    UpdateRequestRideResponse updateRideRequest(Long requestRideId, UpdateRequestRideRequest updateRideRequest);
    List<RequestRide> getRequestRidesByPassenger();
}
