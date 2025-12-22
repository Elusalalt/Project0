package org.example.Service.Models;

import java.time.LocalDate;

public class Book {
    private Integer id;
    private String dewey;
    private String title;
    private Author author;
    private Genre genre;
    private LocalDate publishDate;
    private Integer totalOwnedByLibrary;
    private Integer numberCheckedOut;

    public Book(){}

    public Book(Integer id, String dewey, String title, Author author, Genre genre, LocalDate publishDate, Integer totalOwnedByLibrary, Integer numberCheckedOut) {
        this.id = id;
        this.dewey = dewey;
        this.title = title;
        this.author = author;
        this.genre = genre;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", dewey='" + dewey + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", publishDate=" + publishDate +
                ", totalOwnedByLibrary=" + totalOwnedByLibrary +
                ", numberCheckedOut=" + numberCheckedOut +
                '}';
    }
}
