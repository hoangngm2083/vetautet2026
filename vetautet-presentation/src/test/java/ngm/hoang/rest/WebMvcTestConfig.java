package ngm.hoang.rest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(GlobalExceptionHandler.class)
public class WebMvcTestConfig {
}