package project.springboot.todo_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.springboot.todo_app.entity.Todo;
import project.springboot.todo_app.services.TodoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    TodoService todoService;


    @GetMapping("/getall")
    public ResponseEntity<List<Todo>> getAllJournalForUser(){
        return new ResponseEntity<>(todoService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Todo> saveEntry(@RequestBody Todo todo){
        try {
            todoService.saveTodoEntry(todo);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(todo,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){

            Optional<Todo> journal = todoService.getByID(id);
            System.out.println(journal);
            if(journal.isPresent()){
                return new ResponseEntity<>(journal.get(),HttpStatus.OK);
            }
        return new ResponseEntity<>("No Entry was found",HttpStatus.NOT_FOUND);
    }


//    ResponseEntity<?>  =>  this means that you can return object of any class...

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJournal(
            @PathVariable("id") String id,
            @RequestBody Todo newJournal){

        Todo old = todoService.getByID(id).orElse(null);
        System.out.println("old : "+old);
        if(old != null){
            old.setTitle(newJournal.getTitle()!=null && !newJournal.getTitle().equals("") ? newJournal.getTitle() : old.getTitle() );
            old.setContent(newJournal.getContent() !=null && !newJournal.getContent().equals("") ? newJournal.getContent() : old.getContent());
            todoService.saveTodoEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>("todo not found",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable("id") String id) {

        Optional<Todo> old = todoService.getByID(id);
        if(old.isEmpty()){
            return new ResponseEntity<>("todo with this id doesn't exist",HttpStatus.NOT_FOUND);
        }
        boolean removed =  todoService.deleteEntry(id);
        if(removed){
            return new ResponseEntity<>("Deleted todo",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
