package org.example.app.services;


import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookParameterToFilter;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

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

    public boolean removeBookByParameter(BookToRemove bookToRemove) {
        return bookRepo.removeItemByParameter(bookToRemove);
    }

    public List<Book> filterBooks(BookParameterToFilter bookParameterToFilter){
        return bookRepo.filter(bookParameterToFilter);
    }

    private void defaultInit(){
        logger.info("default INIT in book service bean");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book service bean");
    }
}
