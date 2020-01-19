package cinema.reservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cinema {
    private Long id;
    private String name;
    @JsonIgnore
    private List<Hall> halls;

    public Cinema(String name) {
        this.name = name;
    }
}
