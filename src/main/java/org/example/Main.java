package org.example;

import org.example.Controller.LibraryController;
import org.example.Repository.DAO.AuthorDAO;
import org.example.Repository.Entities.AuthorEntity;
import org.example.Service.AuthorService;
import org.example.Service.Models.Author;

import java.lang.classfile.instruction.SwitchCase;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static AuthorService authorService = new AuthorService();

    public static void testing(){
        //AuthorEntity a = new AuthorEntity();
        //a.setAuthor("Agatha Christie");
        try{
            //System.out.println(authorService.createEntity(a));
            List<Author> authors= authorService.getAllModels();
            for(Author author : authors){
                System.out.println(author.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final Scanner sc = new Scanner(System.in);
    static char promptForInput(String menu, String valid){
        boolean v=false;
        int i=0;
        String in = "";
        String validChar="";
        System.out.print(menu);
        do {
            in = sc.nextLine();
            if(in.length()==1)
            {
                /*
                Convert this to Java if multiple cases of letters are going to be used in the menu:
                while (i < (int)(strlen(validIn)))
                {
                    if (isalpha(validIn[i]))
                    {
                        if (isupper(validIn[i]))
                        {
                            input = toupper(input);
                        }
                        else
                        {
                            input = tolower(input);
                        }
                    }
                    if (validIn[i] == input)
                    {
                        valid = true;
                    }
                    i++;
                }
                */
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
    static void main() {
        LibraryController controller = new LibraryController();
        controller.mainMenu();

        final String MAIN_MENU = "1. Existing customer\n2. New customer\n3. Books\nX. Exit\nChoice:";
        final String VALID_MAIN_MENU = "123X";
        final String CUSTOMER_MENU = "1. Customer info\n2. Checkout book\n3. Pay late fee\n4. Return book\nX. Main menu\nChoice:";
        final String VALID_CUSTOMER_MENU = "1234X";
        final String BOOKS_MENU = "1. Lookup book\n2. Checkout book\n3. Return book\n4. Add book to library\nX. Main menu\nChoice:";
        final String VALID_BOOKS_MENU = "1234X";
        final String LOOKUP_BOOK_MENU = "1. Search by Title\n2. Search by Author\n3. Search by genre\nX. Back to books menu\nChoice:";
        final String VALID_LOOKUP_BOOK_MENU = "123X";
        char in = ' ';
        /*
        Book book = new Book("12345", "this is a good title", "i wrote it", 2);
        Book book2 = new Book("123456", "this is a good title too", "i wrote it again", 5);
        System.out.println(book);
        System.out.println(book2);
        LibraryMember member = new LibraryMember("jim");
        LibraryMember member2 = new LibraryMember("john");
        LibraryMember member3 = new LibraryMember("jane");

        System.out.println(member);
        System.out.println(member2);
        System.out.println(member3);
        */
        testing();
        in = promptForInput(MAIN_MENU,VALID_MAIN_MENU);
        while(in!=VALID_MAIN_MENU.charAt(VALID_MAIN_MENU.length()-1))
        {
            switch(in){
                case '1':
                    System.out.println("login prompt/method");
                    in = promptForInput(CUSTOMER_MENU, VALID_CUSTOMER_MENU);
                    while(in!=VALID_CUSTOMER_MENU.charAt(VALID_CUSTOMER_MENU.length()-1)) {
                        switch(in){
                            case '1':
                                System.out.println("Customer info menu/method call");
                                break;
                            case '2':
                                System.out.println("Checkout book menu/method");
                                break;
                            case '3':
                                System.out.println("Pay late fee menu/method");
                                break;
                            case '4':
                                System.out.println("Return book menu/method");
                                break;
                        }
                        in = promptForInput(CUSTOMER_MENU, VALID_CUSTOMER_MENU);
                    }
                    break;
                case '2':
                    System.out.println("this would be the new customer creation menu/method call");
                    break;
                case '3':
                    in = promptForInput(BOOKS_MENU, VALID_BOOKS_MENU);
                    while(in!=VALID_BOOKS_MENU.charAt(VALID_BOOKS_MENU.length()-1)) {
                        switch(in){
                            case '1':
                                in = promptForInput(LOOKUP_BOOK_MENU, VALID_LOOKUP_BOOK_MENU);
                                while(in!=VALID_LOOKUP_BOOK_MENU.charAt(VALID_LOOKUP_BOOK_MENU.length()-1)){
                                    switch(in){
                                        case '1':
                                            System.out.println("Search by title menu/method call");
                                            break;
                                        case '2':
                                            System.out.println("Search by author menu/method call");
                                            break;
                                        case '3':
                                            System.out.println("Search by genre menu/method call");
                                            break;
                                    }
                                    in = promptForInput(LOOKUP_BOOK_MENU, VALID_LOOKUP_BOOK_MENU);
                                }
                                break;
                            case '2':
                                System.out.println("Checkout book menu/method");
                                break;
                            case '3':
                                System.out.println("Return book menu/method");
                                break;
                            case '4':
                                System.out.println("Add book to library menu/method");
                                break;
                        }
                        in = promptForInput(BOOKS_MENU, VALID_BOOKS_MENU);
                    }
                    break;
                default:
                    System.out.println("H O W");
                    System.out.println(in);
                    break;
            }
            in = promptForInput(MAIN_MENU,VALID_MAIN_MENU);
        }
        sc.close();
    }
}
