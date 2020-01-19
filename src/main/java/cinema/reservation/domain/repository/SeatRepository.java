package cinema.reservation.domain.repository;

import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> findAll();

    Optional<Seat> findById(long id);

    List<Seat> findByStatus(Status status);

    Seat save(Status status);

    void update(long id, Status status);

    void deleteAll();
}
