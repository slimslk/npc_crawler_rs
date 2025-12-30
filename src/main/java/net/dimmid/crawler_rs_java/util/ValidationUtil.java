package net.dimmid.crawler_rs_java.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import net.dimmid.crawler_rs_java.error.BadRequestException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationUtil {

    private final Validator validator;

    public ValidationUtil(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validateObject(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            return Mono.error(new BadRequestException(errorMessage));
        }
        return Mono.just(object);
    }
}
