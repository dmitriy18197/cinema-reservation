package cinema.reservation.application.api;

import cinema.reservation.application.api.command.CinemaSaveCommand;
import cinema.reservation.application.api.command.UpdateCinemaCommand;
import cinema.reservation.domain.Cinema;
import cinema.reservation.domain.repository.CinemaRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import java.util.List;

@Controller
public class CinemaController {
    private final CinemaRepository repository;

    public CinemaController(CinemaRepository repository) {
        this.repository = repository;
    }

    @Get("/cinemas")
    public List<Cinema> getAll() {
        return repository.findAll();
    }

    @Get("/cinemas/{id}")
    public HttpResponse<Cinema> getById(Long id) {
        return repository.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Post("/cinemas")
    public HttpResponse<Cinema> save(CinemaSaveCommand body) {
        Cinema cinema = repository.save(body.getName());
        return HttpResponse.created(cinema);
    }

    @Put("/cinemas")
    public HttpResponse update(UpdateCinemaCommand body) {
        repository.update(body.getId(), body.getName());
        return HttpResponse.ok();
    }

}
