package ngm.hoang.service;

import ngm.hoang.model.Admin;

public interface AdminService {
    Admin create(String username, String passwordHash);
}
