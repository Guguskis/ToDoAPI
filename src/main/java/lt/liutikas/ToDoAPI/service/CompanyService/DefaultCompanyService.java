package lt.liutikas.ToDoAPI.service.CompanyService;

import lt.liutikas.ToDoAPI.exception.DuplicateEntityException;
import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.Company;
import lt.liutikas.ToDoAPI.repository.CompanyRepository;
import lt.liutikas.ToDoAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCompanyService implements CompanyService {
    @Autowired
    private CompanyRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(Company company) throws DuplicateEntityException {
        if (exists(company.getUsername())) {
            throw new DuplicateEntityException("username is taken");
        } else {
            repository.save(company);
        }
    }

    private boolean exists(String username) {
        try {
            find(username);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    private Company find(String username) throws EntityNotFoundException {
        Company company = repository.findByUsername(username);

        if (company == null) {
            throw new EntityNotFoundException("company not found");
        }

        return company;
    }

    @Override
    public void update(Company company) throws EntityNotFoundException {
        Company existingCompany = find(company.getUsername());
        company.setId(existingCompany.getId());
        userRepository.save(company);
        repository.save(company);
    }

    @Override
    public void delete(String username) throws EntityNotFoundException {
        Company companyToDelete = find(username);
        repository.delete(companyToDelete);
    }
}
