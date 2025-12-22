package org.example.Util;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static Integer getIntInput(String prompt){
        while(true){
            System.out.print(prompt);
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid Input. Please enter a number.");
            }
        }
    }

    public static String getStringInput(String prompt){
        while(true){
            System.out.print(prompt);
            try{
                return scanner.nextLine();
            }catch(RuntimeException e){
                System.out.println("Invalid Input. Please enter a String.");
            }
        }
    }

    public static LocalDate getDateInput(String prompt){
        while(true){
            System.out.println(prompt);
            try{
                Integer year = getIntInput("Year: ");
                Integer month = getIntInput("Month: ");
                Integer day  = getIntInput("Day: ");
                return LocalDate.of(year,month,day);
            }catch(NumberFormatException e){
                System.out.println("Invalid Input. Please enter a number.");
            }
        }
    }
}
