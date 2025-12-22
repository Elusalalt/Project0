package org.example.Service;

import org.example.Repository.DAO.CheckedOutBookDAO;
import org.example.Repository.Entities.CheckedOutBookEntity;
import org.example.Service.Models.Book;
import org.example.Service.Models.CheckedOutBook;
import org.example.Service.Models.LibraryMember;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CheckedOutBookService implements ServiceInterface<CheckedOutBookEntity, CheckedOutBook>{
    private final Double FEE_AMOUNT_PER_DAY = 2.50;
    private final Integer AMOUNT_OF_DAYS_BEFORE_FEES_START = 14;
    private CheckedOutBookDAO checkedOutBookDAO = new CheckedOutBookDAO();
    private LibraryMemberService libraryMemberService = new LibraryMemberService();
    private BookService bookService = new BookService();

    public CheckedOutBookService(){}

    @Override
    public Integer createEntity(CheckedOutBookEntity checkedOutBookEntity) {
        try{
            Integer newId = checkedOutBookDAO.create(checkedOutBookEntity);
            return newId;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<CheckedOutBookEntity> getEntityById(Integer id) {
        try{
            Optional<CheckedOutBookEntity> checkedOutBookEntity = checkedOutBookDAO.findById(id);
            if(checkedOutBookEntity.isEmpty()){
                throw new RuntimeException("CheckedOutBook not found");
            }

            return checkedOutBookEntity;
        }catch(SQLException | RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<CheckedOutBookEntity> getAllEntities() {
        try{
            List<CheckedOutBookEntity> checkedOutBookEntities = checkedOutBookDAO.findAll();
            return checkedOutBookEntities;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CheckedOutBookEntity updateEntity(Integer id, CheckedOutBookEntity newEntity) {
        try {
            return checkedOutBookDAO.updateById(newEntity);
        }catch(SQLException e){
            System.out.println("Unable to update member, SQL Exception thrown");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try{
            return checkedOutBookDAO.deleteById(id);
        }catch(SQLException e){
            System.out.println("unable to delete, SQL exception");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<CheckedOutBook> convertEntityToModel(CheckedOutBookEntity checkedOutBookEntity) {

        CheckedOutBook checkedOutBook = new CheckedOutBook();
        checkedOutBook.setId(checkedOutBookEntity.getId());

        try {
            Optional<Book> book= bookService.getModelById(checkedOutBookEntity.getBookID());
            if (book.isPresent())
            {
                checkedOutBook.setBook(book.get());
            }

        }catch(Exception e){
            System.out.println("unable to find book ID associated with this checked out book");
        }
        try {
            Optional<LibraryMember> libraryMember= libraryMemberService.getModelById(checkedOutBookEntity.getLibraryMemberID());
            if (libraryMember.isPresent())
            {
                checkedOutBook.setLibraryMember(libraryMember.get());
            }

        }catch(Exception e){
            System.out.println("unable to find library member associated with this checked out book");
        }
        return Optional.of(checkedOutBook);
    }
    public CheckedOutBookEntity convertModelToEntity(CheckedOutBook checkedOutBook){
        CheckedOutBookEntity checkedOutBookEntity= new CheckedOutBookEntity();
        checkedOutBookEntity.setBookID(checkedOutBook.getBook().getId());
        checkedOutBookEntity.setLibraryMemberID(checkedOutBook.getLibraryMember().getId());
        checkedOutBookEntity.setCheckedOutDate(checkedOutBook.getCheckedOutDate());
        checkedOutBookEntity.setFees(checkedOutBook.getFees());
        return checkedOutBookEntity;
    }

    public List<CheckedOutBook> convertEntitiesToModels(List<CheckedOutBookEntity> checkedOutBookEntities) {
        List<CheckedOutBook> checkedOutBooks = new ArrayList<>();
        for (CheckedOutBookEntity checkedOutBookEntity : checkedOutBookEntities) {
            CheckedOutBook checkedOutBook = new CheckedOutBook();
            checkedOutBook.setId(checkedOutBookEntity.getId());

            try {
                Optional<Book> book = bookService.getModelById(checkedOutBookEntity.getBookID());
                if (book.isPresent()) {
                    checkedOutBook.setBook(book.get());
                }

            } catch (Exception e) {
                System.out.println("unable to find book ID associated with this checked out book. ID: " + checkedOutBookEntity.getBookID());
            }
            try {
                Optional<LibraryMember> libraryMember = libraryMemberService.getModelById(checkedOutBookEntity.getLibraryMemberID());
                if (libraryMember.isPresent()) {
                    checkedOutBook.setLibraryMember(libraryMember.get());
                }

            } catch (Exception e) {
                System.out.println("unable to find library member associated with this checked out book. ID: " + checkedOutBookEntity.getLibraryMemberID());
            }
            checkedOutBooks.add(checkedOutBook);
        }
        return checkedOutBooks;
    }

    @Override
    public Optional<CheckedOutBook> getModelById(Integer id) {
        Optional<CheckedOutBookEntity> checkedOutBookEntity = getEntityById(id);
        try{
            if(checkedOutBookEntity.isPresent()){
                Optional<CheckedOutBook> checkedOutBook = convertEntityToModel(checkedOutBookEntity.get());
                if(checkedOutBook.isPresent()){
                    return checkedOutBook;
                }else{
                    throw new RuntimeException("CheckedOutBookEntity conversion failed");
                }
            }else{
                throw new RuntimeException("CheckedOutBookEntity not found");
            }

        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<CheckedOutBook> getModelByCheckedOutBookName(String checkedOutBookName) {
        List<CheckedOutBookEntity> checkedOutBookEntities = getEntityByCheckedOutBookName(checkedOutBookName);
        List<CheckedOutBook> checkedOutBooks = new ArrayList<CheckedOutBook>();
        try{
            if(!(checkedOutBookEntities.isEmpty())){
                checkedOutBooks = convertEntitiesToModels(checkedOutBookEntities);
                    return checkedOutBooks;
            }else{
                throw new RuntimeException("CheckedOutBookEntity not found");
            }
        }catch(RuntimeException e){
            e.printStackTrace();
            return checkedOutBooks;
        }
    }

    private List<CheckedOutBookEntity> getEntityByCheckedOutBookName(String checkedOutBookName) {
        List<CheckedOutBookEntity> checkedOutBookEntities = new ArrayList<>();
        try{
            Optional<Book> book = bookService.getModelByBookName(checkedOutBookName);
            if (book.isPresent()) {

                return checkedOutBookDAO.findByCheckedOutBookBookID(book.get().getId());
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        return checkedOutBookEntities;
    }
    public List<CheckedOutBook> getModelByCheckedOutBookByLibraryMember(LibraryMember libraryMember) {
        List<CheckedOutBookEntity> checkedOutBookEntities = getEntityByCheckedOutBookByLibraryMemberID(libraryMember.getId());
        List<CheckedOutBook> checkedOutBooks = new ArrayList<CheckedOutBook>();
        try{
            if(!(checkedOutBookEntities.isEmpty())){
                checkedOutBooks = convertEntitiesToModels(checkedOutBookEntities);
                return checkedOutBooks;
            }else{
                //throw new RuntimeException("CheckedOutBookEntity not found");
            }
        }catch(RuntimeException e){
            e.printStackTrace();
            return checkedOutBooks;
        }
        return checkedOutBooks;
    }
    private List<CheckedOutBookEntity> getEntityByCheckedOutBookByLibraryMemberID(Integer libraryMemberID) {
        List<CheckedOutBookEntity> checkedOutBookEntities = new ArrayList<>();
        try{
            return checkedOutBookDAO.findByCheckedOutBookByLibraryMemberID(libraryMemberID);
        }catch (SQLException e){
            e.printStackTrace();

        }
        return checkedOutBookEntities;
    }
    public List<CheckedOutBook> getAllModels() {
        List<CheckedOutBookEntity> checkedOutBookEntities = getAllEntities();
        List<CheckedOutBook> checkedOutBooks = new ArrayList<>();
        for(CheckedOutBookEntity checkedOutBookEntity : checkedOutBookEntities){
            Optional<CheckedOutBook> checkedOutBook = convertEntityToModel(checkedOutBookEntity);
            if(checkedOutBook.isPresent()){
                checkedOutBooks.add(checkedOutBook.get());
            }
        }
        return checkedOutBooks;
    }
    public Double calculateFeesOwedOnCheckedOutBook(CheckedOutBook checkedOutBook){
        Double feesOwed=0.0;
        LocalDate today=LocalDate.now();
        Long daysCheckedOut = ChronoUnit.DAYS.between(checkedOutBook.getCheckedOutDate(),today);
        daysCheckedOut-=AMOUNT_OF_DAYS_BEFORE_FEES_START;
        if (daysCheckedOut<=0){
            return feesOwed;
        }else{
            feesOwed = daysCheckedOut*FEE_AMOUNT_PER_DAY;
        }
        checkedOutBook.setFees(feesOwed);
        ;
        updateEntity(checkedOutBook.getId(),convertModelToEntity(checkedOutBook));
        return checkedOutBook.getFees();
    }
    public boolean validateCheckoutRequest(LibraryMember libraryMember, Book book){
        List<CheckedOutBook> checkedOutBooks = new ArrayList<>();

        try{
            checkedOutBooks=getModelByCheckedOutBookName(book.getTitle());
            if(bookService.availableForCheckout(book))
            {
                checkedOutBooks = getModelByCheckedOutBookByLibraryMember(libraryMember);
                if(checkedOutBooks.size()>=3){
                    return false;
                }
                if(libraryMember.getFeesOwed()>0)
                {
                    return false;
                }
                for (CheckedOutBook checkedOutBook:checkedOutBooks)
                {
                    if (calculateFeesOwedOnCheckedOutBook(checkedOutBook)>0){
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("error validating if the checkout request is valid");
        }
        return false;
    }
    public Optional<CheckedOutBook> checkOutABook(Book book, LibraryMember libraryMember){
        CheckedOutBook checkedOutBook= new CheckedOutBook();
        if(!(validateCheckoutRequest(libraryMember,book))){
            return Optional.empty();
        }
        checkedOutBook.setFees(0.0);
        checkedOutBook.setLibraryMember(libraryMember);
        checkedOutBook.setBook(book);
        checkedOutBook.setCheckedOutDate(LocalDate.now());
        checkedOutBook.setId(createEntity(convertModelToEntity(checkedOutBook)));
        return Optional.of(checkedOutBook);

    }

    public boolean returnBook(CheckedOutBook checkedOutBook){
        try{
            Double fees = calculateFeesOwedOnCheckedOutBook(checkedOutBook);
            checkedOutBook.getLibraryMember().setFeesOwed(checkedOutBook.getLibraryMember().getFeesOwed()+fees);
            deleteEntity(checkedOutBook.getId());

        }catch (Exception e){
            System.out.println("failed to delete book");
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
