package net.dimmid.crawler_rs_java.config;

import net.dimmid.crawler_rs_java.error.AuthenticationException;
import net.dimmid.crawler_rs_java.error.BadRequestException;
import net.dimmid.crawler_rs_java.error.DuplicateDataException;
import net.dimmid.crawler_rs_java.error.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Configuration
public class ExceptionConfig {
    @Bean
    HttpStatus defaultHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Bean
    Map<Class<? extends Throwable>, HttpStatus> getExceptionMap() {
        return Map.of(
                DuplicateDataException.class, HttpStatus.CONFLICT,
                AuthenticationException.class, HttpStatus.UNAUTHORIZED,
                NotFoundException.class, HttpStatus.NOT_FOUND,
                BadRequestException.class, HttpStatus.BAD_REQUEST
        );
    }
}