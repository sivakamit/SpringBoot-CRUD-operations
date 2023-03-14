package com.example.CRUDOperations.controller;

import com.example.CRUDOperations.model.Book;
import com.example.CRUDOperations.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookrepo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try{
            List<Book> bookList = new ArrayList<>();
            bookrepo.findAll().forEach(bookList::add);

            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getAllBooksById(@PathVariable Long id) {
        try{
            Optional<Book> bookData = bookrepo.findById(id);

            if(bookData.isPresent()){
                return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try{
            Book bookObj = bookrepo.save(book);

            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
        try{
            Optional<Book> oldBookData = bookrepo.findById(id);

            if(oldBookData.isPresent()){
                Book updatedBook = oldBookData.get();
                updatedBook.setTitle(newBookData.getTitle());
                updatedBook.setAuthor(newBookData.getAuthor());
                Book bookObj = bookrepo.save(updatedBook);
                return new ResponseEntity<>(bookObj, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookbyId(@PathVariable Long id) {
        bookrepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
