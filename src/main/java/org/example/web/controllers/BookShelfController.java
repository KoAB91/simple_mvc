package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model){
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book, Model model){
        if(book.getAuthor().isEmpty() && book.getTitle().isEmpty() && book.getSize()== null){
            model.addAttribute("saveErrorMessage", "One of the parameters must not be empty");
            return this.books(model);
        }
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(Book book, Model model){
        logger.info("delete book: " + book);
        if(bookService.removeBookByParameter(book)){
            return "redirect:/books/shelf";
        }else{
            model.addAttribute("deleteErrorMessage", "No book with such parameter");
            return this.books(model);
        }
    }

    @GetMapping("/filter")
    public String filterBooks(@RequestParam("parameter") int parameter, Model model){
        logger.info("filter books by param: " + parameter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.filterBooks(parameter));
        return "book_shelf";
    }
}
