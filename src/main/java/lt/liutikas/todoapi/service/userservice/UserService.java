package lt.liutikas.todoapi.service.userservice;

import lt.liutikas.todoapi.dto.CreatePersonDto;
import lt.liutikas.todoapi.dto.UpdateCompanyDto;
import lt.liutikas.todoapi.dto.UpdatePersonDto;
import lt.liutikas.todoapi.dto.VerifyUserDto;
import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.User;

import java.util.List;

public interface UserService {
    boolean verify(VerifyUserDto user);

    User findUser(String username) throws EntityNotFoundException;

    User findUser(long id) throws EntityNotFoundException;

    void create(CreatePersonDto person) throws DuplicateEntityException;

    void create(Company company) throws DuplicateEntityException;

    List<User> findAll();

    void update(UpdatePersonDto person) throws EntityNotFoundException;

    void update(UpdateCompanyDto company) throws EntityNotFoundException;
}
