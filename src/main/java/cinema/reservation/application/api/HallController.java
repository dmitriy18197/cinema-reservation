package cinema.reservation.application.api;

import cinema.reservation.application.api.command.SaveHallCommand;
import cinema.reservation.application.api.command.UpdateHallCommand;
import cinema.reservation.domain.Hall;
import cinema.reservation.domain.repository.HallRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import java.util.List;

@Controller
public class HallController {
    private final HallRepository repository;

    public HallController(HallRepository repository) {
        this.repository = repository;
    }

    @Get("/halls")
    public List<Hall> getAll() {
        return repository.findAll();
    }

    @Get("/halls/{id}")
    public HttpResponse<Hall> getByHallId(Long id) {
        return repository.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Get("/halls/?cinemaId=#{cinemaId}")
    public List<Hall> getByCinemaId(Long cinemaId) {
        return repository.findByCinemaId(cinemaId);
    }

    @Get("/cinemas/{cinemaId}/halls")
    public List<Hall> getByCinemaId_(Long cinemaId) {
        return repository.findByCinemaId(cinemaId);
    }

    @Post("/cinemas/{cinemaId}/halls")
    public HttpResponse<Hall> save(Long cinemaId, SaveHallCommand body) {
        Hall hall = repository.save(cinemaId, body.getName());
        return HttpResponse.created(hall);
    }

    @Put("/cinemas/{cinemaId}/halls")
    public HttpResponse update(Long cinemaId, UpdateHallCommand body) {
        repository.update(body.getId(), cinemaId, body.getName());
        return HttpResponse.ok();
    }

}
