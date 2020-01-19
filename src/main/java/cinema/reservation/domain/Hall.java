package cinema.reservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    private Long id;
    private Long cinemaId;
    private String name;
    @JsonIgnore
    private List<Seat> seats;

    public Hall(Long cinemaId, String name) {
        this.cinemaId = cinemaId;
        this.name = name;
    }
}
