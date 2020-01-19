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
    public Optional<Seat> findById(long id) {
        return Optional.ofNullable(seatMapper.selectById(id));
    }

    @Override
    public List<Seat> findByStatus(Status status) {
        return seatMapper.selectByStatus(status);
    }

    @Override
    public Seat save(Status status) {
        Seat seat = new Seat(status);
        seatMapper.save(seat);
        return seat;
    }

    @Override
    public void update(long id, Status status) {
        seatMapper.update(id, status);
    }

    @Override
    public void deleteAll() {
        seatMapper.deleteAll();
    }

}
