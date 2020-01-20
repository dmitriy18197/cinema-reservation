package cinema.reservation.application.api;

import cinema.reservation.application.api.command.SaveHallCommand;
import cinema.reservation.application.api.command.UpdateHallCommand;
import cinema.reservation.domain.Cinema;
import cinema.reservation.domain.Hall;
import cinema.reservation.domain.repository.CinemaRepository;
import cinema.reservation.domain.repository.HallRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class HallControllerTest {
    @Inject
    @Client("/")
    private RxHttpClient client;

    @Inject
    private HallRepository hallRepository;
    @Inject
    private CinemaRepository cinemaRepository;

    @BeforeEach
    void setUp() {
        cinemaRepository.deleteAll();
    }

    @Test
    void testGet() {
        Cinema firstCinema = cinemaRepository.save("First cinema");
        Hall firstHall = hallRepository.save(firstCinema.getId(), "Hall 1");
        hallRepository.save(firstCinema.getId(), "Hall 2");
        Cinema secondCinema = cinemaRepository.save("Second cinema");
        hallRepository.save(secondCinema.getId(), "Hall 1");

        HttpResponse<List> getAllResponse = getClient().exchange(
                HttpRequest.GET("/halls"),
                Argument.of(List.class, Cinema.class)
        );
        assertEquals(HttpStatus.OK, getAllResponse.getStatus());
        assertTrue(getAllResponse.getBody().isPresent());
        assertEquals(3, getAllResponse.getBody().get().size());

        HttpResponse<Hall> getByIdResponse = getClient().exchange(
                HttpRequest.GET("/halls/" + firstHall.getId()),
                Argument.of(Hall.class)
        );
        assertEquals(HttpStatus.OK, getByIdResponse.getStatus());
        assertTrue(getByIdResponse.getBody().isPresent());
        assertEquals(firstHall, getByIdResponse.getBody().get());

        HttpResponse<List> getByCinemaIdResponse = getClient().exchange(
                HttpRequest.GET("/cinemas/" + firstCinema.getId() + "/halls"),
                Argument.of(List.class, Hall.class)
        );
        assertEquals(HttpStatus.OK, getByCinemaIdResponse.getStatus());
        assertTrue(getByCinemaIdResponse.getBody().isPresent());
        assertEquals(2, getByCinemaIdResponse.getBody().get().size());
    }

    @Test
    void testPost() {
        Cinema cinema = cinemaRepository.save("Cinema");
        SaveHallCommand body = new SaveHallCommand("New Hall");

        HttpResponse<Hall> response = getClient().exchange(
                HttpRequest.POST("/cinemas/" + cinema.getId() + "/halls", body),
                Argument.of(Hall.class)
        );
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(cinema.getId(), response.getBody().get().getCinemaId());
        assertEquals("New Hall", response.getBody().get().getName());
    }

    @Test
    void testPut() {
        Cinema cinema = cinemaRepository.save("Cinema");
        Hall oldHall = hallRepository.save(cinema.getId(), "Old hall");
        String newName = "New hall";
        UpdateHallCommand body = new UpdateHallCommand(oldHall.getId(), newName);

        HttpResponse response = getClient().exchange(
                HttpRequest.PUT("/cinemas/" + cinema.getId() + "/halls", body)
        );
        assertEquals(HttpStatus.OK, response.getStatus());

        Optional<Hall> newHall = hallRepository.findById(oldHall.getId());
        assertTrue(newHall.isPresent());
        assertEquals(newName, newHall.get().getName());
    }

    private BlockingHttpClient getClient() {
        return client.toBlocking();
    }

}