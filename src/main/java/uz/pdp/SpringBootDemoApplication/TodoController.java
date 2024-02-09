package uz.pdp.SpringBootDemoApplication;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.SpringBootDemoApplication.exceptionHandlers.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
public class TodoController {
    static AtomicInteger count = new AtomicInteger(1);
    List<Todo> todos = new ArrayList<>() {{
        add(new Todo(count.getAndIncrement(), "read a book about Rest Api", "HIGH"));
        add(new Todo(count.getAndIncrement(), "read a book about Api Servers", "MEDIUM"));

    }};

    @GetMapping(value = "/todos", consumes = "application/json",
            produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Todo>> getAll() {
        return ResponseEntity.ok(todos);
    }

    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<Todo> get(@PathVariable Integer id) {
        return ResponseEntity.ok(todos.stream().filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("TOdo Not found "))
        );

    }

    @PostMapping(value = "/todos")

    public ResponseEntity<Todo> create(@Valid @RequestBody Todo todo) {
        todo.setId(count.getAndIncrement());
        todos.add(todo);
//        return new ResponseEntity<>(todo, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }


   /* @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> error_404(ItemNotFoundException e, HttpServletRequest re){
        return ResponseEntity
                .status(404)
                .body(Map.of(
                        "error_message",e.getMessage(),
                        "error_code", 404,
                        "error_path",re.getRequestURI(),
                        "timestamp", LocalDateTime.now()
                ));


    }*/
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Todo {

    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    @Size(min = 4,max = 20)
    private String priority;

}