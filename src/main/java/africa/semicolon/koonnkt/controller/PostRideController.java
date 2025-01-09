package africa.semicolon.koonnkt.controller;

import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.dto.request.PostRideRequest;
import africa.semicolon.koonnkt.dto.request.UpdatePostRideRequest;
import africa.semicolon.koonnkt.dto.response.ApiResponse;
import africa.semicolon.koonnkt.dto.response.DeletePostRide;
import africa.semicolon.koonnkt.dto.response.PostRideResponse;
import africa.semicolon.koonnkt.dto.response.UpdatePostRideResponse;
import africa.semicolon.koonnkt.service.PostRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/PostRide")
public class PostRideController {
    @Autowired
    private PostRideService postRideService;
    @PostMapping("/createRide")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?>  createPostRide(@RequestBody PostRideRequest postRideRequest){
        try {
            PostRideResponse postRideResponse = postRideService.createPostRide(postRideRequest);
            return   new ResponseEntity<> (new ApiResponse(true, postRideResponse), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
     @GetMapping("/driver")
     @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<PostRide>> getRidePostByDriver() {
        try{
            List<PostRide> postRide = postRideService.getRidePostByDriver();
            return new ResponseEntity<>(postRide, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateRide/{postRideId}")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?>  updatePostRide(@PathVariable Long postRideId, @RequestBody UpdatePostRideRequest updatePostRideRequest){
      try{
          UpdatePostRideResponse response = postRideService.updatePostRide(postRideId, updatePostRideRequest);
          return new ResponseEntity<> (new ApiResponse(true, response), HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }

   @DeleteMapping("/deletePostRide/{postRideId}")
   @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?>  deletePostRide(@PathVariable Long postRideId){
        try{
            DeletePostRide response = postRideService.deletePostRide(postRideId);
            return new ResponseEntity<> (new ApiResponse(true, response), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
