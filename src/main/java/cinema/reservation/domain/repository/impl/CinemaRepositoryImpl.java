package cinema.reservation.domain.repository.impl;

import cinema.reservation.domain.Cinema;
import cinema.reservation.domain.mapper.CinemaMapper;
import cinema.reservation.domain.repository.CinemaRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class CinemaRepositoryImpl implements CinemaRepository {
    private final CinemaMapper cinemaMapper;

    public CinemaRepositoryImpl(CinemaMapper cinemaMapper) {
        this.cinemaMapper = cinemaMapper;
    }

    @Override
    public List<Cinema> findAll() {
        return cinemaMapper.selectAll();
    }

    @Override
    public Optional<Cinema> findById(long id) {
        return Optional.ofNullable(cinemaMapper.selectById(id));
    }

    @Override
    public Cinema save(String name) {
        Cinema cinema = new Cinema(name);
        cinemaMapper.insert(cinema);
        return cinema;
    }

    @Override
    public void update(long id, String name) {
        cinemaMapper.update(id, name);
    }

    @Override
    public void deleteAll() {
        cinemaMapper.deleteAll();
    }
}
