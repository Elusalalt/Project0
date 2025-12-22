package org.example.Repository.Entities;

public class GenreEntity {
    private Integer id;
    private String genre;

    public GenreEntity(Integer id, String genre) {
        this.id = id;
        this.genre = genre;
    }
    public GenreEntity(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
