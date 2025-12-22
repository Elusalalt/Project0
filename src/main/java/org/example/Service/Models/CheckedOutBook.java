package org.example.Service.Models;

import java.time.LocalDate;

public class CheckedOutBook {
    private Integer id;
    private Book book;
    private LibraryMember libraryMember;
    private LocalDate checkedOutDate;
    private Double fees;

    public CheckedOutBook(){}

    public CheckedOutBook(Integer id, Book book, LibraryMember libraryMember, LocalDate checkedOutDate, Double fees) {
        this.id = id;
        this.book = book;
        this.libraryMember = libraryMember;
        this.checkedOutDate = checkedOutDate;
        this.fees = fees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public LocalDate getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(LocalDate checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "CheckedOutBook{" +
                "id=" + id +
                ", book=" + book +
                ", libraryMember=" + libraryMember +
                ", checkedOutDate=" + checkedOutDate +
                ", fees=" + fees +
                '}';
    }
}
