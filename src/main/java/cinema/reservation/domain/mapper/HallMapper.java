package cinema.reservation.domain.mapper;

import cinema.reservation.domain.Hall;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HallMapper {

    @Select("select * from hall")
    List<Hall> selectAll();

    @Select("select * from hall where cinemaId=#{cinemaId}")
    List<Hall> selectByCinemaId(long cinemaId);

    @Select("select * from hall where id=#{id}")
    Hall selectById(long id);

    @Insert("insert into hall(cinemaId, name) values(#{cinemaId}, #{name})")
    @Options(useGeneratedKeys = true)
    void insert(Hall hall);

    @Update("update hall set cinemaId=#{cinemaId}, name=#{name} where id=#{id}")
    void update(long id, long cinemaId, String name);
}
