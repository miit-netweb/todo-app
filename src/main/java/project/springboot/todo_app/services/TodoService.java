package project.springboot.todo_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboot.todo_app.entity.Todo;
import project.springboot.todo_app.repository.TodoRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    TodoRepo todoRepo;

    public List<Todo> getAll (){
        return todoRepo.findAll();
    }

    @Transactional
    public void saveTodoEntry(Todo todo){
        Todo savedToDo = todoRepo.save(todo);
    }


    @Transactional
    public boolean deleteEntry(String id){
        try{
                todoRepo.deleteById(id);
                return true;
        } catch (Exception e){
            System.out.println("Error occured while deleting the journal : "+ e);
        }
        return false;
    }

    public Optional<Todo> getByID(String id){
        return todoRepo.findById(id);
    }


}
