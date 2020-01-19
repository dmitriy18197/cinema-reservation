package cinema.reservation.domain.mapper.impl;

import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import cinema.reservation.domain.mapper.SeatMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class SeatMapperImpl implements SeatMapper {
    private final SqlSessionFactory sessionFactory;

    public SeatMapperImpl(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Seat> selectAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectAll();
        }
    }

    @Override
    public List<Seat> selectByCinemaId(long cinemaId) {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectByCinemaId(cinemaId);
        }
    }

    @Override
    public List<Seat> selectByHallId(Long hallId) {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectByHallId(hallId);
        }
    }

    @Override
    public Seat selectById(long id) {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectById(id);
        }
    }


    @Override
    public void save(Seat seat) {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).save(seat);
            session.commit();
        }
    }

    @Override
    public void update(long id, long cinemaId, long hallId, Status status) {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).update(id, cinemaId, hallId, status);
            session.commit();
        }
    }


    @Override
    public void deleteAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).deleteAll();
            session.commit();
        }
    }

    private SeatMapper getMapper(SqlSession session) {
        return session.getMapper(SeatMapper.class);
    }
}
