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

    @Select("select * from SEAT where status=#{status}")
    List<Seat> selectByStatus(Status status);

    @Insert("insert into seat(status) values(#{status})")
    @Options(useGeneratedKeys = true)
    void save(Seat seat);

    @Update("update seat set status=#{status} where id=#{id}")
    void update(long id, Status status);

    @Delete("delete from seat")
    void deleteAll();
}
