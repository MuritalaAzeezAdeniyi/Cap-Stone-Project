package africa.semicolon.koonnkt;

import africa.semicolon.koonnkt.dto.request.PostRideRequest;
import africa.semicolon.koonnkt.dto.response.PostRideResponse;
import africa.semicolon.koonnkt.service.PostRideService;
import africa.semicolon.koonnkt.service.PostRideServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostRideServiceImplTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRideServiceImpl postRideService;
    @Test
    @WithMockUser(username = "Yayaha33", roles = {"DRIVER"})
    public void testThatDriveCanPostRide() throws Exception {
        PostRideRequest postRideRequest = new PostRideRequest();
        postRideRequest.setDepartureLocation("Kaduna");
        postRideRequest.setDestinationLocation("Kano");
        postRideRequest.setDepartureTime(LocalDateTime.now());


        mockMvc.perform(post("/api/PostRide/createRide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRideRequest)))
                .andExpect(status().isCreated());


    }

    @Test
    @WithMockUser(username = "Azeez", roles = {"DRIVER"})
    public void testThatDriveCanPostAnotherRide() throws Exception {
        PostRideRequest postRideRequest = new PostRideRequest();
        postRideRequest.setDepartureLocation("AJA");
        postRideRequest.setDestinationLocation("SABO");
        postRideRequest.setDepartureTime(LocalDateTime.now());


        mockMvc.perform(post("/api/PostRide/createRide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRideRequest)))
                .andExpect(status().isCreated());


    }
}
