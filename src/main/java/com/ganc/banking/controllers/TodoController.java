package com.ganc.banking.controllers;

import com.ganc.banking.models.Todo;
import com.ganc.banking.repositories.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    private Todo newTodo(@RequestBody Todo todo){
        return this.repository.save(todo);
    }

    @GetMapping
    List<Todo> getTodos(){
        return repository.findAll();
    }

    @GetMapping("/{todoId}")
    public Optional<Todo> getTodo(@PathVariable("todoId") Integer todoId){
        var todo = repository.findById(todoId);
        return todo;
    }

    @PutMapping("/{todoId}")
    public Optional<Todo> updateTodo(@PathVariable("todoId") Integer todoId, @RequestBody Todo updatedTodo){
        return repository.findById(todoId)
                .map(oldTodo -> repository.save(updatedTodo));
    }

    @DeleteMapping("/{todoId}")
    public void delete(@PathVariable("todoId") Integer todoId){
        repository.deleteById(todoId);
    }
}
