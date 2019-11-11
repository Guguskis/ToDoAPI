package lt.liutikas.todoapi.repository;

import lt.liutikas.todoapi.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByUsername(String username);
}
