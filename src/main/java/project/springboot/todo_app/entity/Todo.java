package project.springboot.todo_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


// @Data : it includes all getter,setter, NoArgsConstructor ,AllArgsConstructor etc.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {
    @Id
    private String id;
    private String title;
    private String content;

}
