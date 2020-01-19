package cinema.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    public Long id;
    private Status status;

    public Seat(Status status) {
        this.status = status;
    }
}
