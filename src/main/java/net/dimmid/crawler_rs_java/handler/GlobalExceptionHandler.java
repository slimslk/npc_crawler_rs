package net.dimmid.crawler_rs_java.handler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import net.dimmid.crawler_rs_java.error.ErrorResponse;
import net.dimmid.crawler_rs_java.error.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webflux.autoconfigure.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final Map<Class<? extends Throwable>, HttpStatus> exceptionMap;
    private final HttpStatus defaultStatus;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties webProperties,
                                  ApplicationContext applicationContext,
                                  Map<Class<? extends Throwable>, HttpStatus> exceptionMap,
                                  HttpStatus defaultStatus,
                                  ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.exceptionMap = exceptionMap;
        this.defaultStatus = defaultStatus;
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = this.getError(request);
        HttpStatus httpStatus;
        String errorMessage;
        assert error != null;
        if (error instanceof ResponseStatusException rse && rse.getStatusCode() == HttpStatus.NOT_FOUND) {
            error = new NotFoundException(request.path() + " not found");
        }
        if (this.exceptionMap.containsKey(error.getClass())) {
            this.log.warn("{}?{}: {}",
                    request.path(),
                    request.queryParams(),
                    error.getMessage());
            httpStatus = this.exceptionMap.getOrDefault(error.getClass(), this.defaultStatus);
        } else {
            this.log.error("Unhandled exception at {} {}?{}: {}",
                    request.method(),
                    request.path(),
                    request.queryParams(),
                    error.getMessage(),
                    error);
            httpStatus = HttpStatus.NOT_FOUND;
        }
        errorMessage = error.getMessage();

        return this.generateErrorResponse(httpStatus, errorMessage, request.path());
    }

    private Mono<ServerResponse> generateErrorResponse(HttpStatus errorHttpStatus, String errorMessage, String uriPath) {
        return ServerResponse
                .status(errorHttpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorResponse(errorHttpStatus.value(),
                        errorMessage, uriPath, this.getCurrentDate())));
    }

    private String getCurrentDate() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
