package cinema.reservation.application.api;

import cinema.reservation.application.api.command.SaveSeatCommand;
import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import cinema.reservation.domain.repository.SeatRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static cinema.reservation.domain.Status.RESERVED;
import static cinema.reservation.domain.Status.VACANT;
import static io.micronaut.http.HttpRequest.*;
import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class SeatControllerTest {
    @Inject
    @Client("/seat")
    private RxHttpClient client;
    @Inject
    private SeatRepository repository;

    private BlockingHttpClient getClient() {
        return client.toBlocking();
    }

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testGetSeats() {
        saveSeats(RESERVED, VACANT, RESERVED);

        HttpResponse<List> getAllResponse = getClient().exchange(
                GET("/"),
                Argument.of(List.class, Seat.class)
        );
        List<Seat> list = getAllResponse.getBody().get();
        assertEquals(3, list.size());

        Seat seat = list.get(0);
        HttpResponse<Seat> getByIdResponse = getClient().exchange(
                GET("/" + seat.getId()),
                Argument.of(Seat.class)
        );
        assertEquals(seat, getByIdResponse.getBody().get());

        HttpResponse<List> getByStatusResponse = getClient().exchange(
                GET("/status=RESERVED"),
                Argument.of(List.class, Seat.class)
        );
        assertEquals(2, getByStatusResponse.getBody().get().size());
    }

    @Test
    void testPostSeat() {
        SaveSeatCommand body = new SaveSeatCommand("VACANT");
        HttpResponse<Seat> response = getClient().exchange(
                POST("/", body),
                Argument.of(Seat.class)
        );
        assertEquals(CREATED, response.getStatus());
        assertNotNull(response.getBody().get().getId());
        assertEquals(VACANT, response.getBody().get().getStatus());
    }

    @Test
    void testPutSeat() {
        Seat savedSeat = repository.save(RESERVED);
        savedSeat.setStatus(VACANT);
        HttpResponse response = getClient().exchange(
                PUT("/", savedSeat)
        );
        assertEquals(OK, response.getStatus());

        Optional<Seat> updatedSeat = repository.findById(savedSeat.getId());
        assertNotNull(updatedSeat.get());
        assertEquals(savedSeat.getId(), updatedSeat.get().getId());
        assertEquals(VACANT, updatedSeat.get().getStatus());
    }

    private void saveSeats(Status... statuses) {
        for (Status status : statuses) {
            repository.save(status);
        }
    }
}