package cinema.reservation.domain.mapper.impl;

import cinema.reservation.domain.Hall;
import cinema.reservation.domain.mapper.HallMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class HallMapperImpl implements HallMapper {
    private final SqlSessionFactory sessionFactory;

    public HallMapperImpl(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Hall> selectAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectAll();
        }
    }

    @Override
    public List<Hall> selectByCinemaId(long cinemaId) {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectByCinemaId(cinemaId);
        }
    }

    @Override
    public Hall selectById(long id) {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectById(id);
        }
    }

    @Override
    public void insert(Hall hall) {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).insert(hall);
            session.commit();
        }
    }

    @Override
    public void update(long id, long cinemaId, String name) {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).update(id, cinemaId, name);
            session.commit();
        }
    }

    private HallMapper getMapper(SqlSession session) {
        return session.getMapper(HallMapper.class);
    }

}
