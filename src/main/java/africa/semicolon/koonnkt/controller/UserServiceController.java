package africa.semicolon.koonnkt.controller;

import africa.semicolon.koonnkt.dto.request.RegisterUserRequest;
import africa.semicolon.koonnkt.dto.request.UpdateUserDetailRequest;
import africa.semicolon.koonnkt.dto.request.UserLoginRequest;
import africa.semicolon.koonnkt.dto.response.*;
import africa.semicolon.koonnkt.execption.UserExistException;
import africa.semicolon.koonnkt.service.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*")
public class UserServiceController {
    @Autowired
    private UserServicesImpl userServices;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        try {
            RegisterUserResponse registerUserResponse = userServices.registerUser(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, registerUserResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            UserLoginResponse loginResponse = userServices.loginUser(userLoginRequest);
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/blockUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockUser(@PathVariable Long userId) throws UserExistException {
        try {
            BlockUserResponse blockUserResponse = userServices.blockUser(userId);
            return new ResponseEntity<>(new ApiResponse(true, blockUserResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/unblockUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unblockUser(@PathVariable Long userId) throws UserExistException {
        try {
            BlockUserResponse blockUserResponse = userServices.blockUser(userId);
            return new ResponseEntity<>(new ApiResponse(true, blockUserResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateUserDetail/{userId}")
    public ResponseEntity<?> updateUserDetail(@PathVariable Long userId,@RequestBody UpdateUserDetailRequest updateUserDetailRequest) throws UserExistException {
        try {
            UpdateUserDetailResponse response = userServices.updateUserDetail(userId, updateUserDetailRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
