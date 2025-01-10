package africa.semicolon.koonnkt;

import africa.semicolon.koonnkt.dto.request.RegisterUserRequest;
import africa.semicolon.koonnkt.dto.request.UpdateUserDetailRequest;
import africa.semicolon.koonnkt.dto.request.UserLoginRequest;
import africa.semicolon.koonnkt.dto.response.BlockUserResponse;
import africa.semicolon.koonnkt.dto.response.RegisterUserResponse;
import africa.semicolon.koonnkt.dto.response.UpdateUserDetailResponse;
import africa.semicolon.koonnkt.dto.response.UserLoginResponse;
import africa.semicolon.koonnkt.execption.InvalidCredentialException;
import africa.semicolon.koonnkt.execption.UserExistException;
import africa.semicolon.koonnkt.service.UserServicesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServicesImpl userServices;
    @Test
    public void testThatUserCanNotRegisterTwice() throws UserExistException, InvalidCredentialException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("admin22");
        registerUserRequest.setPassword("admin01");
        registerUserRequest.setEmail("admin@admin.com");
        registerUserRequest.setFirstname("wale");
        registerUserRequest.setLastname("walele");
        registerUserRequest.setRole("ADMIN");
        registerUserRequest.setPassword("08109643956");
        RegisterUserResponse registerUserResponse = userServices.registerUser(registerUserRequest);
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.getUsername()).isEqualTo("admin22");


    }
    @Test
    public void testThatUserCanLogin() throws InvalidCredentialException, UserExistException {
        String password = passwordEncoder.encode("Yaya22");
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("Yayaha50");
        loginRequest.setPassword("7909");
        UserLoginResponse loginResponse = userServices.loginUser(loginRequest);
        System.out.println(loginResponse);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getUsername()).isEqualTo("Yayaha50");

    }
      @Test
    public void testThatUserCantRegister() throws UserExistException, InvalidCredentialException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Yayaha33");
        registerUserRequest.setPassword("Yaya11");
        registerUserRequest.setEmail("yahaha@gmail.com");
        registerUserRequest.setFirstname("Yayaha");
        registerUserRequest.setLastname("Bello");
        registerUserRequest.setRole("DRIVER");
        registerUserRequest.setPassword("08109643957");
        registerUserRequest.setDrivingLicenceNumber("334567");
        registerUserRequest.setVehiclePlateNumber("kjtp3456");
        RegisterUserResponse registerUserResponse = userServices.registerUser(registerUserRequest);
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.getUsername()).isEqualTo("Yayaha33");

    }
    @Test
    public void testThatAdminCanBlockUser() throws UserExistException {
      BlockUserResponse blockUserResponse = userServices.blockUser(402L);
      assertThat(blockUserResponse).isNotNull();
    }

    @Test
    public void tesThatAdminUserCanUnBlockUser() throws UserExistException {
        BlockUserResponse blockUserResponse = userServices.unblockUser(402L);
        assertThat(blockUserResponse).isNotNull();

    }
    @Test
    public void testThatUserDetailCanBeUpdated() throws UserExistException, InvalidCredentialException {
        Long userId = 202L;
        UpdateUserDetailRequest updateUserDetailRequest = new UpdateUserDetailRequest();
        updateUserDetailRequest.setRole("ADMIN");
        UpdateUserDetailResponse updateUserDetailResponse = userServices.updateUserDetail(userId,updateUserDetailRequest);
        assertThat(updateUserDetailResponse).isNotNull();

    }
     @Test
    public void testThatUserCantRegisters() throws UserExistException, InvalidCredentialException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        String password = passwordEncoder.encode("Yaya22");
        registerUserRequest.setUsername("Yayaha50");
        registerUserRequest.setPassword("7909");
        registerUserRequest.setEmail("abe@gmail.com");
        registerUserRequest.setFirstname("Yayaha");
        registerUserRequest.setLastname("Bello");
        registerUserRequest.setRole("DRIVER");
        registerUserRequest.setPhoneNumber("08109643756");
        registerUserRequest.setDrivingLicenceNumber("334567");
        registerUserRequest.setVehiclePlateNumber("kjtp3456");
        RegisterUserResponse registerUserResponse = userServices.registerUser(registerUserRequest);
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.getUsername()).isEqualTo("Yayaha50");

    }

}
