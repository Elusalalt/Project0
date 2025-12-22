package org.example.Service.Models;

public class Author {

    private Integer id;
    private String authorName;


    public Author(){}

    public Author(Integer id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }


    public Integer getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
