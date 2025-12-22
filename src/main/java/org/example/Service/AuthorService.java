package org.example.Service;

import org.example.Service.Models.Author;
import org.example.Repository.DAO.AuthorDAO;
import org.example.Repository.Entities.AuthorEntity;
import java.util.List;
import java.util.Optional;
import java.sql.SQLException;
import java.util.ArrayList;


public class AuthorService implements ServiceInterface<AuthorEntity,Author>{

    private AuthorDAO authorDAO = new AuthorDAO();

    @Override
    public Integer createEntity(AuthorEntity authorEntity) {
        try{
            Integer newId = authorDAO.create(authorEntity);
            return newId;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<AuthorEntity> getEntityById(Integer id) {
        try{
            Optional<AuthorEntity> authorEntity = authorDAO.findById(id);
            if(authorEntity.isEmpty()){
                throw new RuntimeException("Author not found");
            }

            return authorEntity;
        }catch(SQLException | RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<AuthorEntity> getAllEntities() {
        try{
            List<AuthorEntity> authorEntities = authorDAO.findAll();
            return authorEntities;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AuthorEntity updateEntity(Integer id, AuthorEntity newEntity) {
        return null;
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try{
            return authorDAO.deleteById(id);
        }catch(SQLException e){
            System.out.println("unable to delete, SQL exception");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Author> convertEntityToModel(AuthorEntity authorEntity) {

        Author author = new Author();
        author.setId(authorEntity.getId());
        author.setAuthorName(authorEntity.getAuthor());

        return Optional.of(author);
    }

    @Override
    public Optional<Author> getModelById(Integer id) {
        Optional<AuthorEntity> authorEntity = getEntityById(id);
        try{
            if(authorEntity.isPresent()){
                Optional<Author> author = convertEntityToModel(authorEntity.get());
                if(author.isPresent()){
                    return author;
                }else{
                    throw new RuntimeException("AuthorEntity conversion failed");
                }
            }else{
                throw new RuntimeException("AuthorEntity not found");
            }

        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Author> getModelByAuthorName(String authorName) {
        Optional<AuthorEntity> authorEntity = getEntityByAuthorName(authorName);
        try{
            if(authorEntity.isPresent()){
                Optional<Author> author = convertEntityToModel(authorEntity.get());
                if(author.isPresent()){
                    return author;
                }else{
                    throw new RuntimeException("AuthorEntity conversion failed");
                }
            }else{
                throw new RuntimeException("AuthorEntity not found");
            }
        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<AuthorEntity> getEntityByAuthorName(String authorName) {
        try{
            Optional<AuthorEntity> authorEntity = authorDAO.findByAuthorName(authorName);
            return authorEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Author> getAllModels() {
        List<AuthorEntity> authorEntities = getAllEntities();
        List<Author> authors = new ArrayList<>();
        for(AuthorEntity authorEntity : authorEntities){
            Optional<Author> author = convertEntityToModel(authorEntity);
            if(author.isPresent()){
                authors.add(author.get());
            }
        }
        return authors;
    }
}
