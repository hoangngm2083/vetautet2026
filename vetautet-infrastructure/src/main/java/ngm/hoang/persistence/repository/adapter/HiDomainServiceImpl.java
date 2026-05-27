package ngm.hoang.persistence.repository.adapter;

import ngm.hoang.service.HiDomainService;
import org.springframework.stereotype.Repository;

@Repository
public class HiDomainServiceImpl implements HiDomainService {
    @Override
    public String sayHiFromDomain(String name) {
        return "Say Hello From Repository";
    }
}
