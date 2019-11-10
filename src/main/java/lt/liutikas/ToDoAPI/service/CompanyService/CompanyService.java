package lt.liutikas.ToDoAPI.service.CompanyService;

import lt.liutikas.ToDoAPI.exception.DuplicateEntityException;
import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    void create(Company company) throws DuplicateEntityException;

    void update(Company company) throws EntityNotFoundException;

    void delete(String username) throws EntityNotFoundException;
}
