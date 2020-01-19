package cinema.reservation.domain.mapper;

import cinema.reservation.domain.Cinema;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

public interface CinemaMapper {

    @Select("select * from cinema")
    List<Cinema> selectAll();

    @Select("select * from cinema where id=#{id}")
    Cinema selectById(long id);

    @Insert("insert into cinema(name) values(#{name})")
    @Options(useGeneratedKeys = true)
    void insert(Cinema cinema);

    @Update("update cinema set name=#{name} where id=#{id}")
    void update(long id, String name);

    @Delete("delete from cinema")
    void deleteAll();
}
