package cinema.reservation.application.api.command;

import lombok.Getter;

@Getter
public class UpdateSeatCommand {
    private long id;
    private String status;
}
