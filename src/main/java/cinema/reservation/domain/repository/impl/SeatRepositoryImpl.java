package cinema.reservation.domain.repository.impl;

import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import cinema.reservation.domain.mapper.SeatMapper;
import cinema.reservation.domain.repository.SeatRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class SeatRepositoryImpl implements SeatRepository {
    private final SeatMapper seatMapper;

    public SeatRepositoryImpl(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    @Override
    public List<Seat> findAll() {
        return seatMapper.selectAll();
    }

    @Override
    public List<Seat> findByCinemaId(Long cinemaId) {
        return seatMapper.selectByCinemaId(cinemaId);
    }

    @Override
    public List<Seat> findByHallId(Long hallId) {
        return seatMapper.selectByHallId(hallId);
    }

    @Override
    public Optional<Seat> findById(long id) {
        return Optional.ofNullable(seatMapper.selectById(id));
    }

    @Override
    public Seat save(Seat seat) {
        seatMapper.save(seat);
        return seat;
    }

    @Override
    public void update(Long cinemaId, Long hallId, Long id, Status status) {
        seatMapper.update(id, cinemaId, hallId, status);
    }

    @Override
    public void deleteAll() {
        seatMapper.deleteAll();
    }

}
