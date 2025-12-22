package org.example.Service;

import org.example.Repository.DAO.BookDAO;
import org.example.Repository.Entities.BookEntity;
import org.example.Service.Models.Author;
import org.example.Service.Models.Book;
import org.example.Service.Models.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookService implements ServiceInterface<BookEntity,Book>{

    private BookDAO bookDAO = new BookDAO();
    private AuthorService authorService = new AuthorService();
    private GenreService genreService = new GenreService();

    public BookService(){}

    @Override
    public Integer createEntity(BookEntity bookEntity) {
        try{
            Integer newId = bookDAO.create(bookEntity);
            return newId;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<BookEntity> getEntityById(Integer id) {
        try{
            Optional<BookEntity> bookEntity = bookDAO.findById(id);
            if(bookEntity.isEmpty()){
                throw new RuntimeException("Book not found");
            }

            return bookEntity;
        }catch(SQLException | RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<BookEntity> getAllEntities() {
        try{
            List<BookEntity> bookEntities = bookDAO.findAll();
            return bookEntities;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BookEntity updateEntity(Integer id, BookEntity newEntity) {
        try {
            return bookDAO.updateById(newEntity);
        }catch(SQLException e){
            System.out.println("Unable to update member, SQL Exception thrown");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try{
            return bookDAO.deleteById(id);
        }catch(SQLException e){
            System.out.println("unable to delete, SQL exception");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Book> convertEntityToModel(BookEntity bookEntity) {

        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setTitle(bookEntity.getTitle());
        book.setDewey(bookEntity.getDewey());
        book.setPublishDate(bookEntity.getPublishDate());
        book.setTotalOwnedByLibrary(bookEntity.getTotalOwnedByLibrary());
        book.setNumberCheckedOut(bookEntity.getNumberCheckedOut());
        try{
            Optional<Genre> genre = genreService.getModelById(bookEntity.getGenreID());
            if(genre.isPresent())
            {
                book.setGenre(genre.get());
            }else{
                System.out.println("unable to find genre while converting to model");
            }
        }catch (Exception e){

            e.printStackTrace();
        }
        try{
            Optional<Author> author = authorService.getModelById(bookEntity.getAuthorID());
            if(author.isPresent())
            {
                book.setAuthor(author.get());
            }else{
                System.out.println("unable to find author while converting to model");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return Optional.of(book);
    }

    @Override
    public Optional<Book> getModelById(Integer id) {
        Optional<BookEntity> bookEntity = getEntityById(id);
        try{
            if(bookEntity.isPresent()){
                Optional<Book> book = convertEntityToModel(bookEntity.get());
                if(book.isPresent()){
                    return book;
                }else{
                    throw new RuntimeException("BookEntity conversion failed");
                }
            }else{
                throw new RuntimeException("BookEntity not found");
            }

        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Book> getModelByBookName(String bookName) {
        Optional<BookEntity> bookEntity = getEntityByBookName(bookName);
        try{
            if(bookEntity.isPresent()){
                Optional<Book> book = convertEntityToModel(bookEntity.get());
                if(book.isPresent()){
                    return book;
                }else{
                    throw new RuntimeException("BookEntity conversion failed");
                }
            }else{
                throw new RuntimeException("BookEntity not found");
            }
        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }



    private Optional<BookEntity> getEntityByBookName(String bookName) {
        try{
            Optional<BookEntity> bookEntity = bookDAO.findByBookTitle(bookName);
            return bookEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }


    private List<BookEntity> getEntityByBookAuthor(String author) {
        List<BookEntity> bookEntity = new ArrayList<BookEntity>();
        try{

            bookEntity = bookDAO.findBooksByAuthorID(authorService.getModelByAuthorName(author).get().getId());
            return bookEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return bookEntity;
        }
    }

    private List<BookEntity> getEntityByBookAuthor(Author author) {
        List<BookEntity> bookEntity = new ArrayList<>();
        try{
            bookEntity = bookDAO.findBooksByAuthorID(author.getId());
            return bookEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return bookEntity;
        }
    }

    private List<BookEntity> getEntityByBookGenre(String genre) {
        List<BookEntity> bookEntity = new ArrayList<BookEntity>();
        try{

            bookEntity = bookDAO.findBooksByGenreID(genreService.getModelByGenreName(genre).get().getId());
            return bookEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return bookEntity;
        }
    }

    private List<BookEntity> getEntityByBookGenre(Genre genre) {
        List<BookEntity> bookEntity = new ArrayList<>();
        try{
            bookEntity = bookDAO.findBooksByGenreID(genre.getId());
            return bookEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return bookEntity;
        }
    }


    public List<Book> getAllModels() {
        List<BookEntity> bookEntities = getAllEntities();
        List<Book> books = new ArrayList<>();
        for(BookEntity bookEntity : bookEntities){
            Optional<Book> book = convertEntityToModel(bookEntity);
            if(book.isPresent()){
                books.add(book.get());
            }
        }
        return books;
    }

    public boolean availableForCheckout(Book book){
        return (book.getTotalOwnedByLibrary()>book.getNumberCheckedOut());
    }




}
