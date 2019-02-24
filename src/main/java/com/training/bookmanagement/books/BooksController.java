package com.training.bookmanagement.books;

import com.training.bookmanagement.util.common.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books/v1")
public class BooksController extends AbstractBaseController {

    @Autowired
    private BooksService service;

    @GetMapping("/get_all_books")
    public ResponseEntity<Iterable<Books>> getAllBooks(){
        return ok(service.getAllBooks());
    }

    @PostMapping("/save_books")
    public ResponseEntity<Books> saveBooks(@RequestBody Books books){
        return ok(service.saveBooks(books));
    }
}
