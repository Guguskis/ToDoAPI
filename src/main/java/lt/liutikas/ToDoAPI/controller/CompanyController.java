package lt.liutikas.ToDoAPI.controller;

import lt.liutikas.ToDoAPI.exception.DuplicateEntityException;
import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.Company;
import lt.liutikas.ToDoAPI.service.CompanyService.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Company company) {
        try {
            service.create(company);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Company company) {
        try {
            service.update(company);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username) {
        try {
            service.delete(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
