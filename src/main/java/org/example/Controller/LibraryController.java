package org.example.Controller;

import org.example.Repository.Entities.LibraryMemberEntity;
import org.example.Service.*;
import org.example.Service.Models.CheckedOutBook;
import org.example.Service.Models.LibraryMember;
import org.example.Util.InputHandler;

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
    private final String BOOKS_MENU = "1. Lookup book\n2. Checkout book\n3. Return book\n4. Add book to library\nX. Main menu\nChoice:";
    private final String VALID_BOOKS_MENU = "1234X";
    private final String LOOKUP_BOOK_MENU = "1. Search by Title\n2. Search by Author\n3. Search by genre\nX. Back to books menu\nChoice:";
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



    private void existingMemberMenu(LibraryMemberEntity libraryMemberEntity){
        char input = promptForInput(CUSTOMER_MENU,VALID_CUSTOMER_MENU);
        while(input!=VALID_CUSTOMER_MENU.charAt(VALID_CUSTOMER_MENU.length()-1)){
            switch (input) {
                case '1':
                    System.out.println(libraryMemberEntity);
                    System.out.println("Currently checked out books: ");
                    List<CheckedOutBook> checkedOutBooks = new ArrayList<>();
                    checkedOutBooks = checkedOutBookService.getModelByCheckedOutBookByLibraryMember(libraryMemberService.convertEntityToModel(libraryMemberEntity).get());
                    if (checkedOutBooks.isEmpty())
                    {
                        System.out.println("no books are checked out by this member");
                    }else{
                        for (CheckedOutBook checkedOutBook : checkedOutBooks){
                            checkedOutBook.setFees(checkedOutBookService.calculateFeesOwedOnCheckedOutBook(checkedOutBook));
                            System.out.println(checkedOutBook);
                        }
                    }
                    break;
                case '2':

                    break;
                case '3':

                    break;
                case '4':

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

                    break;
            }
            input = promptForInput(MAIN_MENU,VALID_MAIN_MENU);
        }
    }

}
