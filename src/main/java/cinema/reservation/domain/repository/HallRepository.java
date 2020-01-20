package cinema.reservation.domain.repository;

import cinema.reservation.domain.Hall;

import java.util.List;
import java.util.Optional;

public interface HallRepository {

    List<Hall> findAll();

    List<Hall> findByCinemaId(long cinemaId);

    Optional<Hall> findById(long id);

    Hall save(long cinemaId, String name);

    void update(long id, long cinemaId, String name);
}
