/*
package project.springboot.todo_app.controller;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import project.springboot.todo_app.entity.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class journalWithMapController {

    public Map<ObjectId, Todo> journalEntries = new HashMap<ObjectId, Todo>();

    @GetMapping()
    public List<Todo> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping()
    public Boolean addItem (@RequestBody Todo journal){
        journalEntries.put(journal.getId(),journal);
        return true;
    }

    @PutMapping("{id}")
    public Boolean updateItem(@PathVariable("id") ObjectId id, @RequestBody Todo journal){

        journalEntries.remove(id);
        journalEntries.put(journal.getId(),journal);
        return true;
    }

    @DeleteMapping("{id}")
    public Boolean deleteItem(@PathVariable ObjectId id){
        journalEntries.remove(id);
        return true;
    }

}
*/
