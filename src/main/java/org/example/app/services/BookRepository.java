package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookParameterToFilter;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    //private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed");
        return true;
    }

    public boolean removeItemByParameter(BookToRemove bookToRemove) {
        logger.info("remove books by parameter - " + bookToRemove);
        String sql = null;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if(!bookToRemove.getAuthor().isEmpty()){
            sql = "DELETE FROM books WHERE author = :author";
            parameterSource.addValue("author", bookToRemove.getAuthor());
        }
        if(!bookToRemove.getTitle().isEmpty()){
            sql = sql==null ? "DELETE FROM books WHERE title = :title" : sql + " AND title = :title";
            parameterSource.addValue("title", bookToRemove.getTitle());
        }
        if(bookToRemove.getSize() != null){
            sql = sql==null ? "DELETE FROM books WHERE size = :size" : sql + " AND size = :size";
            parameterSource.addValue("size", bookToRemove.getSize());
        }
        if(sql != null){
            return jdbcTemplate.update(sql, parameterSource) > 0;
        } else {
            return false;
        }
    }

    public List<Book> filter(BookParameterToFilter bookParameterToFilter){
        if (bookParameterToFilter.getValue().isEmpty()){
            return retreiveAll();
        }
        String sql;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        switch (bookParameterToFilter.getParameter()){
            case 1: {
                sql = "SELECT * FROM books WHERE author = :author";
                parameterSource.addValue("author", bookParameterToFilter.getValue());
                break;
            }
            case 2: {
                sql = "SELECT * FROM books WHERE title = :title";
                parameterSource.addValue("title", bookParameterToFilter.getValue());
                break;
            }
            case 3: {
                sql = "SELECT * FROM books WHERE size = :size";
                parameterSource.addValue("size", bookParameterToFilter.getValue());
                break;
            }
            default: {
                 return retreiveAll();
            }
        }
        List<Book> books = jdbcTemplate.query(sql, parameterSource, (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        logger.info("filtered books - " + books);

        return new ArrayList<>(books);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
