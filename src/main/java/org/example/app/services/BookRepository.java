package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for(Book book : retreiveAll()){
            if(book.getId().equals(bookIdToRemove)){
                logger.info("remove book by id completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByParameter(Book removableBook) {
        boolean bookDeleted = false;
        for(Book book : retreiveAll()){
            if((removableBook.getId() == null || book.getId().equals(removableBook.getId())) &&
                    (removableBook.getAuthor().isEmpty() || book.getAuthor().equals(removableBook.getAuthor())) &&
                    (removableBook.getTitle().isEmpty() || book.getTitle().equals(removableBook.getTitle())) &&
                    (removableBook.getSize() == null || book.getSize().equals(removableBook.getSize()))){
                logger.info("remove book by author completed: " + book);
                repo.remove(book);
                bookDeleted = true;
            }
        }
        return bookDeleted;
    }

    public List<Book> filter(int parameter){
        List sotredBooks = new ArrayList<Book>(repo);
        switch (parameter){
            case 1: {
                Collections.sort(sotredBooks, new AuthorComparator());
                break;
            }
            case 2: {
                Collections.sort(sotredBooks, new TitleComparator());
                break;
            }
            case 3: {
                Collections.sort(sotredBooks, new SizeComparator());
                break;
            }
            default: Collections.sort(sotredBooks, new AuthorComparator());
        }
        return sotredBooks;
    }

    public class AuthorComparator implements Comparator<Book>
    {
        public int compare(Book o1, Book o2)
        {
            return o1.getAuthor().compareTo(o2.getAuthor());
        }
    }

    public class TitleComparator implements Comparator<Book>
    {
        public int compare(Book o1, Book o2)
        {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    }

    public class SizeComparator implements Comparator<Book>
    {
        public int compare(Book o1, Book o2)
        {
            return o1.getSize().compareTo(o2.getSize());
        }
    }
}
