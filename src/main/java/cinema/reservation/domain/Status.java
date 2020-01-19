package cinema.reservation.domain;

import cinema.reservation.infrastructure.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum Status {
    RESERVED,
    VACANT;

    public static Status fromString(String status) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.name(), status))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Unknown status : " + status));
    }
}
