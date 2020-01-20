package cinema.reservation.application.api;

import cinema.reservation.application.api.command.SaveSeatCommand;
import cinema.reservation.application.api.command.UpdateSeatCommand;
import cinema.reservation.domain.Cinema;
import cinema.reservation.domain.Hall;
import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import cinema.reservation.domain.repository.CinemaRepository;
import cinema.reservation.domain.repository.HallRepository;
import cinema.reservation.domain.repository.SeatRepository;
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
class SeatControllerTest {
    @Inject
    @Client("/")
    private RxHttpClient client;
    @Inject
    private CinemaRepository cinemaRepository;
    @Inject
    private HallRepository hallRepository;
    @Inject
    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() {
        cinemaRepository.deleteAll();
    }

    @Test
    void testGet() {
        Cinema firstCinema = cinemaRepository.save("First cinema");
        Cinema secondCinema = cinemaRepository.save("Second cinema");
        Hall firstHall = hallRepository.save(firstCinema.getId(), "Hall 1");
        Hall secondHall = hallRepository.save(firstCinema.getId(), "Hall 2");
        Hall thirdHall = hallRepository.save(secondCinema.getId(), "Hall 1");
        seatRepository.save(new Seat(firstCinema.getId(), firstHall.getId(), Status.VACANT));
        seatRepository.save(new Seat(firstCinema.getId(), firstHall.getId(), Status.VACANT));
        seatRepository.save(new Seat(firstCinema.getId(), firstHall.getId(), Status.RESERVED));
        seatRepository.save(new Seat(firstCinema.getId(), secondHall.getId(), Status.VACANT));
        seatRepository.save(new Seat(secondCinema.getId(), thirdHall.getId(), Status.RESERVED));

        HttpResponse<List> getAllResponse = getClient().exchange(
                HttpRequest.GET("/seats"),
                Argument.of(List.class, Seat.class)
        );
        assertEquals(HttpStatus.OK, getAllResponse.getStatus());
        assertTrue(getAllResponse.getBody().isPresent());
        assertEquals(5, getAllResponse.getBody().get().size());

        HttpResponse<List> getByHallIdResponse = getClient().exchange(
                HttpRequest.GET("/halls/" + firstHall.getId() + "/seats"),
                Argument.of(List.class, Seat.class)
        );
        assertEquals(HttpStatus.OK, getByHallIdResponse.getStatus());
        assertTrue(getByHallIdResponse.getBody().isPresent());
        assertEquals(3, getByHallIdResponse.getBody().get().size());

        HttpResponse<List> getByCinemaIdResponse = getClient().exchange(
                HttpRequest.GET("/cinemas/" + secondCinema.getId() + "/seats"),
                Argument.of(List.class, Seat.class)
        );
        assertEquals(HttpStatus.OK, getByCinemaIdResponse.getStatus());
        assertTrue(getByCinemaIdResponse.getBody().isPresent());
        assertEquals(1, getByCinemaIdResponse.getBody().get().size());
    }

    @Test
    void testPost() {
        Cinema cinema = cinemaRepository.save("Cinema");
        Hall hall = hallRepository.save(cinema.getId(), "Hall 1");

        SaveSeatCommand body = new SaveSeatCommand("RESERVED");
        HttpResponse<Seat> response = getClient().exchange(
                HttpRequest.POST("/cinemas/" + cinema.getId() + "/halls/" + hall.getId() + "/seats", body),
                Argument.of(Seat.class)
        );
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertTrue(response.getBody().isPresent());
        Seat seat = response.getBody().get();
        assertEquals(cinema.getId(), seat.getCinemaId());
        assertEquals(hall.getId(), seat.getHallId());
        assertEquals(Status.RESERVED, seat.getStatus());
    }

    @Test
    void testPut() {
        Cinema cinema = cinemaRepository.save("Cinema");
        Hall hall = hallRepository.save(cinema.getId(), "Hall 1");
        Seat savedSeat = seatRepository.save(new Seat(cinema.getId(), hall.getId(), Status.VACANT));

        UpdateSeatCommand body = new UpdateSeatCommand(savedSeat.getId(), "RESERVED");
        HttpResponse<Object> response = getClient().exchange(
                HttpRequest.PUT("/cinemas/" + cinema.getId() + "/halls/" + hall.getId() + "/seats", body)
        );
        assertEquals(HttpStatus.OK, response.getStatus());

        Optional<Seat> updatedSeat = seatRepository.findById(savedSeat.getId());
        assertTrue(updatedSeat.isPresent());
        assertEquals(Status.RESERVED, updatedSeat.get().getStatus());
    }

    private BlockingHttpClient getClient() {
        return client.toBlocking();
    }
}