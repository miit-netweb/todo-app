package project.springboot.todo_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.springboot.todo_app.entity.Todo;

@Repository
public interface TodoRepo extends JpaRepository<Todo, String> {
}
