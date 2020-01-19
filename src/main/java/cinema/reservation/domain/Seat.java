package cinema.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private Long id;
    private Long cinemaId;
    private Long hallId;
    private Status status;

    public Seat(Long cinemaId, Long hallId, Status status) {
        this.cinemaId = cinemaId;
        this.hallId = hallId;
        this.status = status;
    }
}
