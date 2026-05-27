package ngm.hoang.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ngm.hoang.persistence.repository.jpa")
@EntityScan(basePackages = "ngm.hoang.persistence.entity")
public class JpaTestConfig {
}