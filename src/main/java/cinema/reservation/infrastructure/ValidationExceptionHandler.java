package cinema.reservation.infrastructure;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Singleton
public class ValidationExceptionHandler implements ExceptionHandler<ValidationException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, ValidationException exception) {
        return HttpResponse.badRequest(exception.getMessage());
    }
}
