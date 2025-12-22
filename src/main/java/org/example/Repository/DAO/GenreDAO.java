package org.example.Repository.DAO;

import org.example.Repository.Entities.GenreEntity;
import org.example.Util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreDAO implements DAOInterface<GenreEntity>{
    private Connection connection = ConnectionHandler.getConnection();


    @Override
    public Integer create(GenreEntity genreEntity) throws SQLException {
        String sql = "INSERT INTO genre (genre) VALUES (?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, genreEntity.getGenre());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;

    }

    @Override
    public Optional<GenreEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM genre WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    GenreEntity genreEntity = new GenreEntity();
                    genreEntity.setId(rs.getInt("id"));
                    genreEntity.setGenre(rs.getString("genre"));

                    return Optional.of(genreEntity);
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public List<GenreEntity> findAll() throws SQLException {
        List<GenreEntity> genres = new ArrayList<>();

        String sql = "SELECT * FROM genre";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                GenreEntity genreEntity = new GenreEntity();
                genreEntity.setId(rs.getInt("id"));
                genreEntity.setGenre(rs.getString("genre"));
                genres.add(genreEntity);
            }
        }
        return genres;

    }

    @Override
    public GenreEntity updateById(GenreEntity entity) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM genre WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    return true;
                }
            }
        }
        return false;
    }
    public Optional<GenreEntity> findByGenreName(String genreName) throws SQLException{
        String sql = "SELECT * FROM genre WHERE genre = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, genreName);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    GenreEntity genreEntity = new GenreEntity();
                    genreEntity.setId(rs.getInt("id"));
                    genreEntity.setGenre(rs.getString("genre"));

                    return Optional.of(genreEntity);
                }
            }
        }
        return Optional.empty();
    }
}
