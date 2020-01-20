package cinema.reservation.domain.repository;

import cinema.reservation.domain.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository {

    List<Cinema> findAll();

    Optional<Cinema> findById(long id);

    Cinema save(String name);

    void update(long id, String name);

    void deleteAll();
}
