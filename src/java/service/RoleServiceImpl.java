package service;

import dal.roleDao.RoleDAO;
import model.account.Role;
import service.interfaces.AccountRoleService;
import java.util.List;

public class RoleServiceImpl implements AccountRoleService {
    private RoleDAO roleDAO;

    public RoleServiceImpl() {
        this.roleDAO = new RoleDAO();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}