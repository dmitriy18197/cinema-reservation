package cinema.reservation.application.api;

import cinema.reservation.application.api.command.CinemaSaveCommand;
import cinema.reservation.application.api.command.UpdateCinemaCommand;
import cinema.reservation.domain.Cinema;
import cinema.reservation.domain.repository.CinemaRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class CinemaControllerTest {
    @Inject
    @Client("/")
    private RxHttpClient client;
    @Inject
    private CinemaRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testGetAll() {
        repository.save("First");
        repository.save("Second");

        HttpResponse<List> response = getClient().exchange(
                HttpRequest.GET("/cinemas"),
                Argument.of(List.class, Cinema.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(2, response.getBody().get().size());
    }

    @Test
    void testGetById() {
        Cinema cinema = repository.save("First");

        HttpResponse<Cinema> response = getClient().exchange(
                HttpRequest.GET("/cinemas/" + cinema.getId()),
                Argument.of(Cinema.class)
        );
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(cinema, response.getBody().get());
    }

    @Test
    void testPost() {
        String cinemaName = "New Cinema";
        CinemaSaveCommand body = new CinemaSaveCommand(cinemaName);
        HttpResponse<Cinema> response = getClient().exchange(
                HttpRequest.POST("/cinemas/", body),
                Argument.of(Cinema.class)
        );
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(cinemaName, response.getBody().get().getName());
    }

    @Test
    void testPut() {
        Cinema cinema = repository.save("Saved Cinema");
        String newName = "New name";
        UpdateCinemaCommand body = new UpdateCinemaCommand(cinema.getId(), newName);

        HttpResponse response = getClient().exchange(
                HttpRequest.PUT("/cinemas/", body)
        );
        assertEquals(HttpStatus.OK, response.getStatus());

        Optional<Cinema> updatedCinema = repository.findById(cinema.getId());
        assertTrue(updatedCinema.isPresent());
        assertEquals(newName, updatedCinema.get().getName());
    }

    private BlockingHttpClient getClient() {
        return client.toBlocking();
    }

}