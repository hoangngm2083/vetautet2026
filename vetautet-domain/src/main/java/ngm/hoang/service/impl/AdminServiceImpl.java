package ngm.hoang.service.impl;

import ngm.hoang.model.Admin;
import ngm.hoang.model.BaseModel;
import ngm.hoang.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public Admin create(String username, String passwordHash) {
        return Admin.builder()
                .id(BaseModel.genId())
                .username(username)
                .passwordHash(passwordHash).build();
    }
}
