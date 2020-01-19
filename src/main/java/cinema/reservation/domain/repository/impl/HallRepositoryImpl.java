package cinema.reservation.domain.repository.impl;

import cinema.reservation.domain.Hall;
import cinema.reservation.domain.mapper.HallMapper;
import cinema.reservation.domain.repository.HallRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class HallRepositoryImpl implements HallRepository {
    private final HallMapper hallMapper;

    public HallRepositoryImpl(HallMapper hallMapper) {
        this.hallMapper = hallMapper;
    }

    @Override
    public List<Hall> findAll() {
        return hallMapper.selectAll();
    }

    @Override
    public List<Hall> findByCinemaId(long cinemaId) {
        return hallMapper.selectByCinemaId(cinemaId);
    }

    @Override
    public Optional<Hall> findById(long id) {
        return Optional.ofNullable(hallMapper.selectById(id));
    }

    @Override
    public Hall save(long cinemaId, String name) {
        Hall hall = new Hall(cinemaId, name);
        hallMapper.insert(hall);
        return hall;
    }

    @Override
    public void update(long id, long cinemaId, String name) {
        hallMapper.update(id, cinemaId, name);
    }
}
