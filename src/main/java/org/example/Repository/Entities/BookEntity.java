package org.example.Repository.Entities;

import java.time.LocalDate;

public class BookEntity {
    private Integer id;
    private String dewey;
    private String title;
    private Integer authorID;
    private Integer genreID;
    private LocalDate publishDate;
    private Integer totalOwnedByLibrary;
    private Integer numberCheckedOut;


    public BookEntity(String dewey, String title, Integer authorID, Integer genreID, LocalDate publishDate, Integer totalOwnedByLibrary, Integer id) {
        this.dewey = dewey;
        this.title = title;
        this.authorID = authorID;
        this.publishDate = publishDate;
        this.totalOwnedByLibrary = totalOwnedByLibrary;
        this.numberCheckedOut = 0;
        this.genreID = genreID;
        this.id = id;
    }
    public BookEntity(String dewey, String title, Integer authorID, Integer totalOwnedByLibrary, Integer genreID, Integer id) {
        this.dewey = dewey;
        this.title = title;
        this.authorID = authorID;
        this.totalOwnedByLibrary = totalOwnedByLibrary;
        this.numberCheckedOut = 0;
        this.genreID = genreID;
        this.id = id;
    }
    public BookEntity(){}

    public int amountInStock(){
        return this.totalOwnedByLibrary-this.numberCheckedOut;
    }
    public boolean isInStock(){
        return (amountInStock()>0);
    }
    public boolean checkout(String memberID){
        if (isInStock()){
            //check out the book
            this.numberCheckedOut++;
            return true;
        }
        return false;
    }

    public BookEntity(Integer id, String dewey, String title, Integer authorID, Integer genreID, LocalDate publishDate, Integer totalOwnedByLibrary, Integer numberCheckedOut) {
        this.id = id;
        this.dewey = dewey;
        this.title = title;
        this.authorID = authorID;
        this.genreID = genreID;
        this.publishDate = publishDate;
        this.totalOwnedByLibrary = totalOwnedByLibrary;
        this.numberCheckedOut = numberCheckedOut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDewey() {
        return dewey;
    }

    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGenreID() {
        return genreID;
    }

    public void setGenreID(Integer genreID) {
        this.genreID = genreID;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getTotalOwnedByLibrary() {
        return totalOwnedByLibrary;
    }

    public void setTotalOwnedByLibrary(Integer totalOwnedByLibrary) {
        this.totalOwnedByLibrary = totalOwnedByLibrary;
    }

    public Integer getNumberCheckedOut() {
        return numberCheckedOut;
    }

    public void setNumberCheckedOut(Integer numberCheckedOut) {
        this.numberCheckedOut = numberCheckedOut;
    }

    @Override
    public String toString(){ //complete this later
        return "Book{" +
                "ID=" + id +
                ", Title='" + title +
                ", Author=" + authorID +
                ", In stock='" + amountInStock() +
                ", Owned by library=" + totalOwnedByLibrary +
                '}';

    }
}
