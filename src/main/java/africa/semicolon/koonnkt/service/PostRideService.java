package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.dto.request.PostRideRequest;
import africa.semicolon.koonnkt.dto.request.UpdatePostRideRequest;
import africa.semicolon.koonnkt.dto.response.DeletePostRide;
import africa.semicolon.koonnkt.dto.response.PostRideResponse;
import africa.semicolon.koonnkt.dto.response.UpdatePostRideResponse;

import java.util.List;

public interface PostRideService {
    PostRideResponse createPostRide(PostRideRequest postRideRequest);
    List<PostRide> getRidePostByDriver();
    UpdatePostRideResponse updatePostRide( Long postRideId, UpdatePostRideRequest postRideRequest);
    DeletePostRide deletePostRide(Long postRideId);
}
