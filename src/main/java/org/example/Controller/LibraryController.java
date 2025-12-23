package org.example.Controller;

import org.example.Repository.Entities.AuthorEntity;
import org.example.Repository.Entities.GenreEntity;
import org.example.Repository.Entities.LibraryMemberEntity;
import org.example.Service.*;
import org.example.Service.Models.*;
import org.example.Util.InputHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class LibraryController {
    private final AuthorService authorService = new AuthorService();
    private final BookService bookService = new BookService();
    private final GenreService genreService = new GenreService();
    private final LibraryMemberService libraryMemberService = new LibraryMemberService();
    private final CheckedOutBookService checkedOutBookService = new CheckedOutBookService();

    private static final Scanner scanner = new Scanner(System.in);

    private final String MAIN_MENU = "1. Existing customer\n2. New customer\n3. Books\nX. Exit\nChoice:";
    private final String VALID_MAIN_MENU = "123X";
    private final String CUSTOMER_MENU = "1. Customer info\n2. Checkout book\n3. Pay late fee\n4. Return book\nX. Main menu\nChoice:";
    private final String VALID_CUSTOMER_MENU = "1234X";
    private final String CHECKOUT_BOOK_MENU = "1. Check out by Title\n2. Check out by Book ID\nX. Back to member menu\nChoice:";
    private final String VALID_CHECKOUT_BOOK_MENU = "12X";
    private final String BOOKS_MENU = "1. Lookup book\nX. Main menu\nChoice:";
    private final String VALID_BOOKS_MENU = "1X";
    private final String LOOKUP_BOOK_MENU = "1. Search by Title\n2. Search by Author\n3. Search by Genre\nX. Back to books menu\nChoice:";
    private final String VALID_LOOKUP_BOOK_MENU = "123X";

    private char promptForInput(String menu, String valid){
        boolean v=false;
        int i=0;
        String in = "";
        String validChar="";
        System.out.print(menu);
        do {
            in = scanner.nextLine();
            if(in.length()==1)
            {
                if(valid.contains(in)){
                    v=true;
                }
            }
            if(!v){
                System.out.print("Invalid input, please enter: ");
                for(i=0;i<valid.length()-1;i++){
                    System.out.print(valid.charAt(i)+", ");
                }
                System.out.println("or " + valid.charAt(i) + " as input");
                System.out.print("Choice:");
            }
        }while(!v);
        return in.charAt(0);
    }

    private void addBookToLibrary(){
        String title = InputHandler.getStringInput("Input title of the book");
        Optional<Book> book = bookService.getModelByBookName(title);
        if (book.isPresent()){
            Integer numberAdded = InputHandler.getIntInput("Input amount of this book being added: ");
            bookService.addBookToLibrary(title,numberAdded);
        }else{
            String authorName = InputHandler.getStringInput("Input author name: ");
            Optional<Author> author = authorService.getModelByAuthorName(authorName);
            if (author.isEmpty()){
                AuthorEntity newAuthor = new AuthorEntity();
                newAuthor.setAuthor(authorName);
                authorService.createEntity(newAuthor);
            }
            String genreName = InputHandler.getStringInput("Input genre: ");
            Optional<Genre> genre = genreService.getModelByGenreName(genreName);
            if (genre.isEmpty()){
                GenreEntity newGenre = new GenreEntity();
                newGenre.setGenre(genreName);
                genreService.createEntity(newGenre);
            }
            LocalDate publishDate = InputHandler.getDateInput("Input publish date");
            Integer numberAdded = InputHandler.getIntInput("Input amount of this book being added: ");
            bookService.addBookToLibrary(title, genreName, authorName, publishDate, numberAdded);
        }
    }


    private void lookupBookByGenre(){
        String genre = InputHandler.getStringInput("Input genre name: ");
        List<Book> books = bookService.getModelsByBookGenre(genre);
        if(books.isEmpty()){
            System.out.println("Unable to find any books in that genre");
        }else{
            for (Book book : books){
                System.out.println(book);
            }
        }
    }

    private void lookupBookByAuthor(){
        String author = InputHandler.getStringInput("Input author name: ");
        List<Book> books = bookService.getModelsByBookAuthor(author);
        if(books.isEmpty()){
            System.out.println("Unable to find any books by that author");
        }else{
            for (Book book : books){
                System.out.println(book);
            }
        }
    }

    private void lookupBookByTitle(){
        String title = InputHandler.getStringInput("Input book title: ");
        Optional<Book> book = bookService.getModelByBookName(title);
        if(book.isEmpty()){
            System.out.println("Unable to find a book with that title");
        }else{
            System.out.println(book.get());
        }
    }

    private void lookupBookMenu() {
        char input = promptForInput(LOOKUP_BOOK_MENU, VALID_LOOKUP_BOOK_MENU);
        switch (input) {
            case '1':
                lookupBookByTitle();
                break;
            case '2':
                lookupBookByAuthor();
                break;
            case '3':
                lookupBookByGenre();
                break;
        }
    }

    private void booksMenu(){
        char input = promptForInput(BOOKS_MENU,VALID_BOOKS_MENU);
        while(input!=VALID_BOOKS_MENU.charAt(VALID_BOOKS_MENU.length()-1)) {
            switch (input) {
                case '1':
                    lookupBookMenu();
                    break;
                case '2':
                    addBookToLibrary();
                    break;
            }
            input = promptForInput(BOOKS_MENU,VALID_BOOKS_MENU);
        }
    }

    private boolean checkOutBookByBookID(LibraryMemberEntity libraryMemberEntity) {
        Integer id = 1;
        while (!(id==0)) {
            id = InputHandler.getIntInput("Input ID of book you want to check out or 0 to go back to member menu\nBook ID: ");
            Optional<Book> book = bookService.getModelById(id);
            if (book.isEmpty()) {
                System.out.println("A book with the ID of "+ id + " is not owned by the library");
            } else {
                if (checkedOutBookService.validateCheckoutRequest(libraryMemberService.convertEntityToModel(libraryMemberEntity).get(), book.get())) {
                    checkedOutBookService.checkOutABook(book.get(), libraryMemberService.convertEntityToModel(libraryMemberEntity).get());
                    System.out.println("Successfully checked out " + book.get().getTitle());
                    return true;
                } else {
                    if (bookService.availableForCheckout(book.get())) {
                        System.out.println("All copies of " + book.get().getTitle() + " are already checked out");
                    } else {
                        System.out.println("Member has outstanding fees on returned books or has not returned a book that is past due");
                    }
                }
            }
        }
        return false;
    }

    private boolean checkOutBookByTitle(LibraryMemberEntity libraryMemberEntity) {
        String title = "";
        while (!(title.equals("X"))) {

            title = InputHandler.getStringInput("Input title of book you want to check out or X to go back to member menu\nBook title: ");
            if(!(title.equals("X"))) {
                Optional<Book> book = bookService.getModelByBookName(title);
                if (book.isEmpty()) {
                    System.out.println(title + " is not owned by the library");
                } else {
                    if (checkedOutBookService.validateCheckoutRequest(libraryMemberService.convertEntityToModel(libraryMemberEntity).get(), book.get())) {
                        checkedOutBookService.checkOutABook(book.get(), libraryMemberService.convertEntityToModel(libraryMemberEntity).get());
                        System.out.println("Successfully checked out " + title);
                        return true;
                    } else {
                        if (bookService.availableForCheckout(book.get())) {
                            System.out.println("All copies of " + title + " are already checked out");
                        } else {
                            System.out.println("Member has outstanding fees on returned books or has not returned a book that is past due");
                            title = "X";
                        }
                    }
                }
            }
        }
        return false;
    }

    private void checkOutBookMenu(LibraryMemberEntity libraryMemberEntity) {
        char input = promptForInput(CHECKOUT_BOOK_MENU, VALID_CHECKOUT_BOOK_MENU);
        switch (input) {
            case '1':
                checkOutBookByTitle(libraryMemberEntity);
                break;
            case '2':
                checkOutBookByBookID(libraryMemberEntity);
                break;
        }
    }
    private void returnBookMenu(LibraryMemberEntity libraryMemberEntity){
        if (printCheckedOutBooksForMember(libraryMemberEntity)){
            Integer id = InputHandler.getIntInput("Input the checkout ID of the book you would like to return or 0 to return to member menu: ");
            while (id!=0){
                try {
                    Optional<CheckedOutBook> checkedOutBook = checkedOutBookService.getModelById(id);
                    if (checkedOutBook.isEmpty()) {
                        id = InputHandler.getIntInput("Invalid id, please input the checkout id of a book you currently have checked out: ");

                    } else if (checkedOutBook.get().getLibraryMember().getId() != libraryMemberEntity.getId()) {
                        id = InputHandler.getIntInput("Invalid id, please input the checkout id of a book you currently have checked out: ");
                    } else {
                        checkedOutBookService.returnBook(checkedOutBook.get());
                        System.out.println("Successfully returned " + checkedOutBook.get().getBook().getTitle());
                        id = 0;
                        libraryMemberEntity.setFeesOwed(libraryMemberService.getEntityById(libraryMemberEntity.getId()).get().getFeesOwed());
                    }
                }catch(RuntimeException e){
                    System.out.println("Invalid ID");
                }
            }

        }


    }
    private boolean printCheckedOutBooksForMember(LibraryMemberEntity libraryMemberEntity){
        System.out.println("Currently checked out books: ");
        List<CheckedOutBook> checkedOutBooks = new ArrayList<>();
        checkedOutBooks = checkedOutBookService.getModelByCheckedOutBookByLibraryMember(libraryMemberService.convertEntityToModel(libraryMemberEntity).get());
        if (checkedOutBooks.isEmpty())
        {
            System.out.println("no books are checked out by this member");
            return false;
        }else{
            for (CheckedOutBook checkedOutBook : checkedOutBooks){
                checkedOutBook.setFees(checkedOutBookService.calculateFeesOwedOnCheckedOutBook(checkedOutBook));
                System.out.println(checkedOutBook);
            }
        }
        return true;
    }
    private void existingMemberMenu(LibraryMemberEntity libraryMemberEntity){
        char input = promptForInput(CUSTOMER_MENU,VALID_CUSTOMER_MENU);
        while(input!=VALID_CUSTOMER_MENU.charAt(VALID_CUSTOMER_MENU.length()-1)){
            switch (input) {
                case '1':
                    System.out.println(libraryMemberEntity);
                    printCheckedOutBooksForMember(libraryMemberEntity);
                    break;
                case '2':
                    checkOutBookMenu(libraryMemberEntity);
                    break;
                case '3':
                    libraryMemberEntity.setFeesOwed(0.0);
                    libraryMemberService.updateEntity(libraryMemberEntity.getId(), libraryMemberEntity);
                    System.out.println("fees have been payed on books that have been returned");
                    break;
                case '4':
                    returnBookMenu(libraryMemberEntity);
                    break;
            }
            input = promptForInput(CUSTOMER_MENU,VALID_CUSTOMER_MENU);
        }
    }
    private void existingMemberLogin(){
        Integer memberID = InputHandler.getIntInput("Input your member id:");
        Optional<LibraryMemberEntity> libraryMemberEntity = libraryMemberService.getEntityById(memberID);
        while (libraryMemberEntity.isEmpty()){
            System.out.println("Invalid id, please input valid id");
            memberID = InputHandler.getIntInput("Input your member id:");
            libraryMemberEntity = libraryMemberService.getEntityById(memberID);
        }
        existingMemberMenu(libraryMemberEntity.get());
    }

    private void newLibraryMemberMenu(){
        String name = InputHandler.getStringInput("Input your name: ");
        LibraryMemberEntity newMember = new LibraryMemberEntity();
        newMember.setName(name);
        newMember.setFeesOwed(0.0);
        Integer id = libraryMemberService.createEntity(newMember);
        newMember.setId(id);
        System.out.println("Successfully created new member with an ID of: " + id);
        existingMemberMenu(newMember);
    }

    public void mainMenu(){
        char input = ' ';
        input = promptForInput(MAIN_MENU,VALID_MAIN_MENU);
        while(input!=VALID_MAIN_MENU.charAt(VALID_MAIN_MENU.length()-1)){
            switch (input) {
                case '1':
                    existingMemberLogin();
                    break;
                case '2':
                    newLibraryMemberMenu();
                    break;
                case '3':
                    booksMenu();
                    break;
            }
            input = promptForInput(MAIN_MENU,VALID_MAIN_MENU);
        }
    }

}
