package cinema.reservation.application.api;

import cinema.reservation.application.api.command.SaveSeatCommand;
import cinema.reservation.application.api.command.UpdateSeatCommand;
import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import cinema.reservation.domain.repository.SeatRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class SeatController {
    private final SeatRepository repository;

    public SeatController(SeatRepository repository) {
        this.repository = repository;
    }

    @Get("/seats")
    public List<Seat> getAll() {
        return repository.findAll();
    }

    @Get("/seats/{id}")
    public HttpResponse<Seat> getById(Long id) {
        return repository.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Get("/cinemas/{cinemaId}/seats")
    public List<Seat> getByCinemaId(Long cinemaId) {
        return repository.findByCinemaId(cinemaId);
    }

    @Get("/halls/{hallId}/seats")
    public List<Seat> getByHallId(Long hallId) {
        return repository.findByHallId(hallId);
    }

    @Post("/cinemas/{cinemaId}/halls/{hallId}/seats/")
    public HttpResponse<Seat> save(Long cinemaId, Long hallId, SaveSeatCommand body) {
        Seat seat = repository.save(new Seat(cinemaId, hallId, Status.fromString(body.getStatus())));
        return HttpResponse.created(seat);
    }

    @Put("/cinemas/{cinemaId}/halls/{hallId}/seats/")
    public HttpResponse update(Long cinemaId, Long hallId, UpdateSeatCommand body) {
        repository.update(cinemaId, hallId, body.getId(), Status.fromString(body.getStatus()));
        return HttpResponse.ok();
    }
}
