package org.example.Service;

import org.example.Repository.DAO.GenreDAO;
import org.example.Repository.Entities.GenreEntity;
import org.example.Service.Models.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreService implements ServiceInterface<GenreEntity, Genre>{

    private GenreDAO genreDAO = new GenreDAO();

    public GenreService(){}

    @Override
    public Integer createEntity(GenreEntity genreEntity) {
        try{
            Integer newId = genreDAO.create(genreEntity);
            return newId;
        }catch(SQLException e){
            //e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<GenreEntity> getEntityById(Integer id) {
        try{
            Optional<GenreEntity> genreEntity = genreDAO.findById(id);
            if(genreEntity.isEmpty()){
                throw new RuntimeException("Genre not found");
            }

            return genreEntity;
        }catch(SQLException | RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<GenreEntity> getAllEntities() {
        try{
            List<GenreEntity> genreEntities = genreDAO.findAll();
            return genreEntities;
        }catch (SQLException e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public GenreEntity updateEntity(Integer id, GenreEntity newEntity) {
        return null;
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try{
            return genreDAO.deleteById(id);
        }catch(SQLException e){
            System.out.println("unable to delete, SQL exception");
            //e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Genre> convertEntityToModel(GenreEntity genreEntity) {

        Genre genre = new Genre();
        genre.setId(genreEntity.getId());
        genre.setGenre(genreEntity.getGenre());

        return Optional.of(genre);
    }

    @Override
    public Optional<Genre> getModelById(Integer id) {
        Optional<GenreEntity> genreEntity = getEntityById(id);
        try{
            if(genreEntity.isPresent()){
                Optional<Genre> genre = convertEntityToModel(genreEntity.get());
                if(genre.isPresent()){
                    return genre;
                }else{
                    throw new RuntimeException("GenreEntity conversion failed");
                }
            }else{
                throw new RuntimeException("GenreEntity not found");
            }

        }catch(RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Genre> getModelByGenreName(String genreName) {
        Optional<GenreEntity> genreEntity = getEntityByGenreName(genreName);
        try{
            if(genreEntity.isPresent()){
                Optional<Genre> genre = convertEntityToModel(genreEntity.get());
                if(genre.isPresent()){
                    return genre;
                }else{
                    throw new RuntimeException("GenreEntity conversion failed");
                }
            }else{
                throw new RuntimeException("GenreEntity not found");
            }
        }catch(RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<GenreEntity> getEntityByGenreName(String genreName) {
        try{
            Optional<GenreEntity> genreEntity = genreDAO.findByGenreName(genreName);
            return genreEntity;
        }catch (SQLException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Genre> getAllModels() {
        List<GenreEntity> genreEntities = getAllEntities();
        List<Genre> genres = new ArrayList<>();
        for(GenreEntity genreEntity : genreEntities){
            Optional<Genre> genre = convertEntityToModel(genreEntity);
            if(genre.isPresent()){
                genres.add(genre.get());
            }
        }
        return genres;
    }

}
