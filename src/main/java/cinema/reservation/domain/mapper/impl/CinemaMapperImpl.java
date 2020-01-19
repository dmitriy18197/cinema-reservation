package cinema.reservation.domain.mapper.impl;

import cinema.reservation.domain.Cinema;
import cinema.reservation.domain.mapper.CinemaMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CinemaMapperImpl implements CinemaMapper {
    private final SqlSessionFactory sessionFactory;

    public CinemaMapperImpl(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Cinema> selectAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectAll();
        }
    }

    @Override
    public Cinema selectById(long id) {
        try (SqlSession session = sessionFactory.openSession()) {
            return getMapper(session).selectById(id);
        }
    }

    @Override
    public void insert(Cinema cinema) {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).insert(cinema);
            session.commit();
        }
    }

    @Override
    public void update(long id, String name) {
        try (SqlSession session = sessionFactory.openSession()) {
            getMapper(session).update(id, name);
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

    private CinemaMapper getMapper(SqlSession session) {
        return session.getMapper(CinemaMapper.class);
    }
}
