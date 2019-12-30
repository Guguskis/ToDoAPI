package lt.liutikas.todoapi.service.companyservice;

import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    void create(Company company) throws DuplicateEntityException;

    void update(Company company) throws EntityNotFoundException;

    void delete(String username) throws EntityNotFoundException;
}
