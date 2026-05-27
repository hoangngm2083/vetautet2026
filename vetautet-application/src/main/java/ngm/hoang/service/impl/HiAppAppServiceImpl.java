package ngm.hoang.service.impl;


import lombok.RequiredArgsConstructor;
import ngm.hoang.service.HiAppService;
import ngm.hoang.service.HiDomainService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HiAppAppServiceImpl implements HiAppService {

    private final HiDomainService hiDomainService;

    @Override
    public String sayHi(String name) {
        return hiDomainService.sayHiFromDomain(name);
    }
}
