package ngm.hoang.service.impl;

import lombok.RequiredArgsConstructor;
import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.model.Admin;
import ngm.hoang.model.BaseModel;
import ngm.hoang.repository.AdminRepository;
import ngm.hoang.service.AdminDomainService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDomainServiceImpl implements AdminDomainService {
    private final AdminRepository adminRepository;
}
