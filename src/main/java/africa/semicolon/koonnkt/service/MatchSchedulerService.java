package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.data.model.RequestRide;
import africa.semicolon.koonnkt.data.repository.PostRideRepo;
import africa.semicolon.koonnkt.data.repository.RequestRideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchSchedulerService {

    @Autowired
    private PostRideRepo postRideRepo;

    @Autowired
    private RequestRideRepo requestRideRepo;

    @Autowired
    private MatchService matchService;

    @Scheduled(fixedRate = 100000)

    public void checkForMatch() {
        List<PostRide> postRides = postRideRepo.findAll();
        List<RequestRide> requestRides = requestRideRepo.findAll();

        for (RequestRide requestRide : requestRides) {
            for (PostRide postRide : postRides) {
                matchService.createMatch(requestRide, postRide);
            }
        }
    }
}
