package ngm.hoang.persistence.repository.adapter;


import ngm.hoang.persistence.PersistenceTestConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = PersistenceTestConfig.class)
public class BaseRepositoryTest {
}
