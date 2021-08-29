package org.example.app.services;


import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks(){
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book){
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByParameter(Book book) {
        return bookRepo.removeItemByParameter(book);
    }

    public List<Book> filterBooks(int parameter){
        return bookRepo.filter(parameter);
    }
}
