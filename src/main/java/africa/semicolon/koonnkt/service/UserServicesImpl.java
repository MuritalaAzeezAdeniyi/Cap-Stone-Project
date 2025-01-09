package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.DriverInfo;
import africa.semicolon.koonnkt.data.model.Users;
import africa.semicolon.koonnkt.data.repository.UsersRepo;
import africa.semicolon.koonnkt.dto.request.RegisterUserRequest;
import africa.semicolon.koonnkt.dto.request.UpdateUserDetailRequest;
import africa.semicolon.koonnkt.dto.request.UserLoginRequest;
import africa.semicolon.koonnkt.dto.response.BlockUserResponse;
import africa.semicolon.koonnkt.dto.response.RegisterUserResponse;
import africa.semicolon.koonnkt.dto.response.UpdateUserDetailResponse;
import africa.semicolon.koonnkt.dto.response.UserLoginResponse;
import africa.semicolon.koonnkt.execption.InvalidCredentialException;
import africa.semicolon.koonnkt.execption.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UsersService {
    @Autowired
    private UsersRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) throws UserExistException, InvalidCredentialException {
        validateUserEmail(registerUserRequest);
        validateUserInfo(registerUserRequest);
        Users user = new Users();
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setUsername(registerUserRequest.getUsername());
        user.setFirstname(registerUserRequest.getFirstname());
        user.setLastname(registerUserRequest.getLastname());
        user.setRole(registerUserRequest.getRole());
        user.setPhoneNumber(registerUserRequest.getPhoneNumber());

        if ("DRIVER".equals(registerUserRequest.getRole())) {
            DriverInfo driverInfo = new DriverInfo();
            driverInfo.setDrivingLicenceNumber(registerUserRequest.getDrivingLicenceNumber());
            driverInfo.setVehiclePlateNumber(registerUserRequest.getVehiclePlateNumber());
            user.setDriverInfo(driverInfo);

        }
        userRepository.save(user);
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setMessage("User registered successfully");
        registerUserResponse.setUsername(registerUserRequest.getUsername());
        registerUserResponse.setRole(registerUserRequest.getRole());


        return registerUserResponse;
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) throws InvalidCredentialException {
         Users user = userRepository.findByUsername(userLoginRequest.getUsername());
         if (user == null || !passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
             throw new InvalidCredentialException("Invalid credentials");
         }
          UserLoginResponse loginResponse = new UserLoginResponse();
          loginResponse.setUsername(user.getUsername());
          loginResponse.setRole(user.getRole());
          loginResponse.setMessage("User logged in successfully");
        return loginResponse;
    }




    private void validateUserEmail(RegisterUserRequest registerUserRequest) throws UserExistException {
        Optional<Users> user = userRepository.findByEmail(registerUserRequest.getEmail());
        if (user.isPresent()) {
            throw new UserExistException(String.format("User with email %s already exists", registerUserRequest.getEmail()));
        }
    }

    private void validateUserInfo(RegisterUserRequest registerUserRequest) throws InvalidCredentialException {
        if (registerUserRequest.getFirstname().trim().isEmpty() || registerUserRequest.getLastname().trim().isEmpty()
                || registerUserRequest.getEmail().trim().isEmpty() ||
                registerUserRequest.getPassword().trim().isEmpty() || registerUserRequest.getUsername().trim().isEmpty()
                || registerUserRequest.getRole().trim().isEmpty()) {
            throw new InvalidCredentialException("Invalid credentials provided");
        }
    }

    private Users findUserById(Long id) throws UserExistException {
      return userRepository.findById(id).
              orElseThrow(() -> new UserExistException(String.format("User with id %s not found", id)));

    }
    @Override
    public BlockUserResponse blockUser(Long userId) throws UserExistException {
         Users userFound = findUserById(userId);
        if (userFound != null) {
            userFound.setBlocked(true);
            userRepository.save(userFound);
            BlockUserResponse blockUserResponse = new BlockUserResponse();
            blockUserResponse.setMessage("user successfully blocked");
            return blockUserResponse;
        }else{
            throw new UserExistException("user not found");
        }
    }

    @Override
    public BlockUserResponse unblockUser(Long userId) throws UserExistException {
       Users userFound = findUserById(userId);
       if (userFound != null) {
           userFound.setBlocked(false);
           userRepository.save(userFound);
           BlockUserResponse blockUserResponse = new BlockUserResponse();
           blockUserResponse.setMessage("user successfully unblocked");
           return blockUserResponse;
       }else{
           throw new UserExistException("user not found");
       }

    }

    @Override
    public UpdateUserDetailResponse updateUserDetail(Long userId, UpdateUserDetailRequest updateUserDetailRequest) throws InvalidCredentialException, UserExistException {
        Users UserFound = findUserById(userId);
        if (UserFound != null) {
            updateUserDetailRequest.setFirstname(updateUserDetailRequest.getFirstname());
            updateUserDetailRequest.setLastname(updateUserDetailRequest.getLastname());
            updateUserDetailRequest.setEmail(updateUserDetailRequest.getEmail());
            updateUserDetailRequest.setPassword(updateUserDetailRequest.getPassword());
            updateUserDetailRequest.setRole(updateUserDetailRequest.getRole());
            updateUserDetailRequest.setPhoneNumber(updateUserDetailRequest.getPhoneNumber());
            updateUserDetailRequest.setUsername(updateUserDetailRequest.getUsername());
            userRepository.save(UserFound);

            UpdateUserDetailResponse updateUserDetailResponse = new UpdateUserDetailResponse();
            updateUserDetailResponse.setMessage("user detail successfully updated");
            return updateUserDetailResponse;
        }else {
            throw new UserExistException("user not found");
        }

    }


}
