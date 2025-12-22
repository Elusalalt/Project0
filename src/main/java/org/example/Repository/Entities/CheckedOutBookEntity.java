package org.example.Repository.Entities;



import java.time.LocalDate;

public class CheckedOutBookEntity {
    private Integer id;
    private Integer bookID;
    private Integer libraryMemberID;
    private LocalDate checkedOutDate;
    private Double fees;

    public CheckedOutBookEntity(Integer id, Integer bookID, Integer libraryMemberID, LocalDate checkedOutDate, Double fees) {
        this.id = id;
        this.bookID = bookID;
        this.libraryMemberID = libraryMemberID;
        this.checkedOutDate = checkedOutDate;
        this.fees = fees;
    }

    public CheckedOutBookEntity(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getLibraryMemberID() {
        return libraryMemberID;
    }

    public void setLibraryMemberID(Integer libraryMemberID) {
        this.libraryMemberID = libraryMemberID;
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
}
