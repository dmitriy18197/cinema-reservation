package cinema.reservation.application.api;

import cinema.reservation.application.api.command.SaveSeatCommand;
import cinema.reservation.application.api.command.UpdateSeatCommand;
import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import cinema.reservation.domain.repository.SeatRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/seat")
public class SeatController {
    private final SeatRepository seatRepository;

    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Get("/")
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Get("/{id}")
    public Seat getById(long id) {
        return seatRepository.findById(id)
                .orElse(null);
    }

    @Get("/status={status}")
    public List<Seat> getByStatus(Status status) {
        return seatRepository.findByStatus(status);
    }

    @Post("/")
    public HttpResponse<Seat> save(@Body SaveSeatCommand command) {
        Seat savedSeat = seatRepository.save(Status.fromString(command.getStatus()));
        return HttpResponse.created(savedSeat);
    }

    @Put("/")
    public HttpResponse<Seat> update(@Body UpdateSeatCommand command) {
        seatRepository.update(command.getId(), Status.fromString(command.getStatus()));
        return HttpResponse.ok();
    }
}
