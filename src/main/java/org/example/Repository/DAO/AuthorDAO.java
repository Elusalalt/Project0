package org.example.Repository.DAO;

import org.example.Repository.Entities.AuthorEntity;
import org.example.Util.ConnectionHandler;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDAO implements DAOInterface<AuthorEntity> {
    private Connection connection = ConnectionHandler.getConnection();


    @Override
    public Integer create(AuthorEntity authorEntity) throws SQLException {
        String sql = "INSERT INTO author (author) VALUES (?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, authorEntity.getAuthor());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;

    }

    @Override
    public Optional<AuthorEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM author WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    AuthorEntity authorEntity = new AuthorEntity();
                    authorEntity.setId(rs.getInt("id"));
                    authorEntity.setAuthor(rs.getString("author"));

                    return Optional.of(authorEntity);
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public List<AuthorEntity> findAll() throws SQLException {
        List<AuthorEntity> authors = new ArrayList<>();

        String sql = "SELECT * FROM author";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setId(rs.getInt("id"));
                authorEntity.setAuthor(rs.getString("author"));
                authors.add(authorEntity);
            }
        }
        return authors;

    }

    @Override
    public AuthorEntity updateById(AuthorEntity entity) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM author WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            stmt.executeUpdate();
            return true;
        }
        //return false;
    }
    public Optional<AuthorEntity> findByAuthorName(String authorName) throws SQLException{
        String sql = "SELECT * FROM author WHERE author = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, authorName);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    AuthorEntity authorEntity = new AuthorEntity();
                    authorEntity.setId(rs.getInt("id"));
                    authorEntity.setAuthor(rs.getString("author"));

                    return Optional.of(authorEntity);
                }
            }
        }
        return Optional.empty();
    }
}
