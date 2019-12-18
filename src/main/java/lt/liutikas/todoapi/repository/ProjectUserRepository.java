package lt.liutikas.todoapi.repository;

import lt.liutikas.todoapi.model.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    @Query("select x from project_user x where x.userId = :id")
    List<ProjectUser> findByUserId(@Param("id") long id);

    @Query("select x from project_user x where x.projectId = :id")
    List<ProjectUser> findByProjectId(@Param("id") long id);
}
