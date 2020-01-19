package cinema.reservation.domain.repository;

import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> findAll();

    List<Seat> findByCinemaId(Long cinemaId);

    List<Seat> findByHallId(Long hallId);

    Optional<Seat> findById(long id);

    Seat save(Seat seat);

    void update(Long cinemaId, Long hallId, Long id, Status status);

    void deleteAll();
}
