package cinema.reservation.domain.mapper;

import cinema.reservation.domain.Seat;
import cinema.reservation.domain.Status;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SeatMapper {

    @Select("select * from SEAT")
    List<Seat> selectAll();

    @Select("select * from SEAT where id=#{id}")
    Seat selectById(long id);

    @Select("select * from SEAT where cinemaId=#{cinemaId}")
    List<Seat> selectByCinemaId(long cinemaId);

    @Select("select * from SEAT where hallId=#{hallId}")
    List<Seat> selectByHallId(Long hallId);

    @Insert("insert into SEAT(cinemaId, hallId, status) values(#{cinemaId}, #{hallId}, #{status})")
    @Options(useGeneratedKeys = true)
    void save(Seat seat);

    @Update("update seat set cinemaId=#{cinemaId}, hallId=#{hallId}, status=#{status} where id=#{id}")
    void update(long id, long cinemaId, long hallId, Status status);

    @Delete("delete from seat")
    void deleteAll();
}
