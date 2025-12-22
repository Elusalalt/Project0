package org.example.Repository.DAO;

import org.example.Repository.Entities.BookEntity;
import org.example.Util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO implements DAOInterface<BookEntity>{
    private Connection connection = ConnectionHandler.getConnection();


    @Override
    public Integer create(BookEntity bookEntity) throws SQLException {
        String sql = "INSERT INTO book (title, dewey, author_id, genre_id, publishDate, totalOwned, numberCheckedOut) VALUES (?,?,?,?,?,?,?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, bookEntity.getTitle());
            stmt.setString(2, bookEntity.getDewey());
            stmt.setInt(3,bookEntity.getAuthorID());
            stmt.setInt(4, bookEntity.getGenreID());
            stmt.setDate(5, Date.valueOf(bookEntity.getPublishDate()));
            stmt.setInt(6, bookEntity.getTotalOwnedByLibrary());
            stmt.setInt(7, bookEntity.getNumberCheckedOut());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;

    }

    @Override
    public Optional<BookEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM book WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    BookEntity bookEntity = new BookEntity();
                    bookEntity.setId(rs.getInt("id"));
                    bookEntity.setAuthorID(rs.getInt("author_id"));
                    bookEntity.setGenreID(rs.getInt("genre_id"));
                    bookEntity.setTitle(rs.getString("title"));
                    bookEntity.setDewey(rs.getString("dewey"));
                    bookEntity.setNumberCheckedOut(rs.getInt("totalOwned"));
                    bookEntity.setPublishDate(rs.getDate("publishDate").toLocalDate());
                    bookEntity.setNumberCheckedOut(rs.getInt("numberCheckedOut"));
                    return Optional.of(bookEntity);
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public List<BookEntity> findAll() throws SQLException {
        List<BookEntity> bookEntities = new ArrayList<>();

        String sql = "SELECT * FROM book";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                BookEntity bookEntity = new BookEntity();
                bookEntity.setId(rs.getInt("id"));
                bookEntity.setAuthorID(rs.getInt("author_id"));
                bookEntity.setGenreID(rs.getInt("genre_id"));
                bookEntity.setTitle(rs.getString("title"));
                bookEntity.setDewey(rs.getString("dewey"));
                bookEntity.setNumberCheckedOut(rs.getInt("totalOwned"));
                bookEntity.setPublishDate(rs.getDate("publishDate").toLocalDate());
                bookEntity.setNumberCheckedOut(rs.getInt("numberCheckedOut"));
                bookEntities.add(bookEntity);
            }
        }
        return bookEntities;

    }

    @Override
    public BookEntity updateById(BookEntity bookEntity) throws SQLException {
        String sql = "UPDATE checkedOutBook SET totalOwned=?, numberCheckedOut=?,dewey=? WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,bookEntity.getTotalOwnedByLibrary());
            stmt.setInt(2,bookEntity.getNumberCheckedOut());
            stmt.setString(3,bookEntity.getDewey());
            stmt.setInt(4,bookEntity.getId());
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    return bookEntity;
                }

            }
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {                     //IMPLEMENT THIS (or maybe not)
        String sql = "DELETE FROM book WHERE id = ?";
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
    public Optional<BookEntity> findByBookTitle(String title) throws SQLException{
        String sql = "SELECT * FROM book WHERE title = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, title);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    BookEntity bookEntity = new BookEntity();
                    bookEntity.setId(rs.getInt("id"));
                    bookEntity.setAuthorID(rs.getInt("author_id"));
                    bookEntity.setGenreID(rs.getInt("genre_id"));
                    bookEntity.setTitle(rs.getString("title"));
                    bookEntity.setDewey(rs.getString("dewey"));
                    bookEntity.setNumberCheckedOut(rs.getInt("totalOwned"));
                    bookEntity.setPublishDate(rs.getDate("publishDate").toLocalDate());
                    bookEntity.setNumberCheckedOut(rs.getInt("numberCheckedOut"));
                    return Optional.of(bookEntity);
                }
            }
        }
        return Optional.empty();
    }
    public List<BookEntity> findBooksByAuthorID(Integer author_id) throws SQLException{
        String sql = "SELECT * FROM book WHERE author_id = ?";
        List<BookEntity> bookEntities = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, author_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookEntity bookEntity = new BookEntity();
                    bookEntity.setId(rs.getInt("id"));
                    bookEntity.setAuthorID(rs.getInt("author_id"));
                    bookEntity.setGenreID(rs.getInt("genre_id"));
                    bookEntity.setTitle(rs.getString("title"));
                    bookEntity.setDewey(rs.getString("dewey"));
                    bookEntity.setNumberCheckedOut(rs.getInt("totalOwned"));
                    bookEntity.setPublishDate(rs.getDate("publishDate").toLocalDate());
                    bookEntity.setNumberCheckedOut(rs.getInt("numberCheckedOut"));
                    bookEntities.add(bookEntity);

                }
            }
        }
        return bookEntities;

    }

    public List<BookEntity> findBooksByGenreID(Integer genre_id) throws SQLException{
        String sql = "SELECT * FROM book WHERE genre_id = ?";
        List<BookEntity> bookEntities = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, genre_id);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    BookEntity bookEntity = new BookEntity();
                    bookEntity.setId(rs.getInt("id"));
                    bookEntity.setAuthorID(rs.getInt("author_id"));
                    bookEntity.setGenreID(rs.getInt("genre_id"));
                    bookEntity.setTitle(rs.getString("title"));
                    bookEntity.setDewey(rs.getString("dewey"));
                    bookEntity.setNumberCheckedOut(rs.getInt("totalOwned"));
                    bookEntity.setPublishDate(rs.getDate("publishDate").toLocalDate());
                    bookEntity.setNumberCheckedOut(rs.getInt("numberCheckedOut"));
                    bookEntities.add(bookEntity);
                }
            }
        }
        return bookEntities;
    }


}
