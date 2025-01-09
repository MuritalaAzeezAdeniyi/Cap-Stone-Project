package africa.semicolon.koonnkt;

import africa.semicolon.koonnkt.dto.request.RegisterUserRequest;
import africa.semicolon.koonnkt.dto.response.RegisterUserResponse;
import africa.semicolon.koonnkt.execption.InvalidCredentialException;
import africa.semicolon.koonnkt.execption.UserExistException;
import africa.semicolon.koonnkt.service.UserServicesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServicesImpl userServices;
    @Test
    public void testThatUserCanRegister() throws UserExistException, InvalidCredentialException {
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

}
