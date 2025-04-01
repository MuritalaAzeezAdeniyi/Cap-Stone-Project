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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) throws UserExistException, InvalidCredentialException {
        validateUserEmail(registerUserRequest);
        validateUserInfo(registerUserRequest);
        phoneNumberValidation(registerUserRequest.getPhoneNumber());
        Users user = new Users();
        user.setEmail(registerUserRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(registerUserRequest.getPassword());
        logger.info("Encoded password at registration: " + encodedPassword);
        user.setPassword(encodedPassword);
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

        if (userLoginRequest.getUsername() == null || userLoginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        Users user = userRepository.findByUsername(userLoginRequest.getUsername());
        if (user == null) { throw new InvalidCredentialException("Invalid credentials"); }

        boolean matches = passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword());
        logger.info("Password matches: " + matches);
        if (!matches) { throw new InvalidCredentialException("Invalid credentials"); }


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
        Users userFound = findUserById(userId);
        if (userFound != null) {
           userFound .setFirstname(updateUserDetailRequest.getFirstname());
            userFound.setLastname(updateUserDetailRequest.getLastname());
            userFound.setEmail(updateUserDetailRequest.getEmail());
            userFound.setPassword(updateUserDetailRequest.getPassword());
            userFound.setRole(updateUserDetailRequest.getRole());
            userFound.setPhoneNumber(updateUserDetailRequest.getPhoneNumber());
            userFound.setUsername(updateUserDetailRequest.getUsername());
            userRepository.save(userFound);

            UpdateUserDetailResponse updateUserDetailResponse = new UpdateUserDetailResponse();
            updateUserDetailResponse.setMessage("user detail successfully updated");
            return updateUserDetailResponse;
        }else {
            throw new UserExistException("user not found");
        }

    }

    public void phoneNumberValidation(String phoneNumber){
        if(phoneNumber.length()!= 11){
            throw new RuntimeException("number must have 11 digits");
        }
    }

    private void passwordValidation(Users users, String password) throws InvalidCredentialException {
        boolean isPasswordMatch =
                passwordEncoder.matches(password, users.getPassword()
                );
        if (!isPasswordMatch) {
            throw new InvalidCredentialException("invalid credentials!");
        }
    }
}
