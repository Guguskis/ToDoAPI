package lt.liutikas.ToDoAPI.repository;

import lt.liutikas.ToDoAPI.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String username);

}
