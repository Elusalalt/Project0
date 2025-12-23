package org.example.Repository.DAO;

import org.example.Repository.Entities.CheckedOutBookEntity;
import org.example.Util.ConnectionHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CheckedOutBookDAO implements DAOInterface<CheckedOutBookEntity>{                   //LOTS OF STUFF TO DO HERE STILL
    private Connection connection = ConnectionHandler.getConnection();


    @Override
    public Integer create(CheckedOutBookEntity checkedOutBookEntity) throws SQLException {
        String sql = "INSERT INTO checkedOutBook (book_id,libraryMember_id,dateCheckedOut,fees) VALUES (?,?,?,?) RETURNING id"; //update with fk stuff

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, checkedOutBookEntity.getBookID());
            stmt.setInt(2,checkedOutBookEntity.getLibraryMemberID());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setInt(4,0);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;

    }

    @Override
    public Optional<CheckedOutBookEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM checkedOutBook WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    CheckedOutBookEntity checkedOutBookEntity = new CheckedOutBookEntity();
                    checkedOutBookEntity.setId(rs.getInt("id"));
                    checkedOutBookEntity.setBookID(rs.getInt("book_id")); //here
                    checkedOutBookEntity.setLibraryMemberID(rs.getInt("libraryMember_id"));
                    checkedOutBookEntity.setCheckedOutDate(rs.getDate("dateCheckedOut").toLocalDate());
                    checkedOutBookEntity.setFees(rs.getDouble("fees"));
                    return Optional.of(checkedOutBookEntity);
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public List<CheckedOutBookEntity> findAll() throws SQLException {
        List<CheckedOutBookEntity> checkedOutBooks = new ArrayList<>();

        String sql = "SELECT * FROM checkedOutBook";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                CheckedOutBookEntity checkedOutBookEntity = new CheckedOutBookEntity();
                checkedOutBookEntity.setId(rs.getInt("id"));
                checkedOutBookEntity.setBookID(rs.getInt("book_id"));
                checkedOutBookEntity.setLibraryMemberID(rs.getInt("libraryMember_id"));
                checkedOutBookEntity.setCheckedOutDate(rs.getDate("dateCheckedOut").toLocalDate());
                checkedOutBookEntity.setFees(rs.getDouble("fees"));
                checkedOutBooks.add(checkedOutBookEntity);
            }
        }
        return checkedOutBooks;

    }

    @Override
    public CheckedOutBookEntity updateById(CheckedOutBookEntity checkedOutBookEntity) throws SQLException {
        String sql = "UPDATE checkedOutBook SET fees=? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDouble(1,checkedOutBookEntity.getFees());
            stmt.setInt(2,checkedOutBookEntity.getId());
            stmt.executeUpdate();
            return checkedOutBookEntity;
        }
        //return null;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM checkedOutBook WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            stmt.executeUpdate();
            return true;
        }
        //return false;
    }
    public List<CheckedOutBookEntity> findByCheckedOutBookBookID(Integer bookID) throws SQLException{
        String sql = "SELECT * FROM checkedOutBook WHERE book_id = ?";
        List<CheckedOutBookEntity> checkedOutBookEntities = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, bookID);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    CheckedOutBookEntity checkedOutBookEntity = new CheckedOutBookEntity();
                    checkedOutBookEntity.setId(rs.getInt("id"));
                    checkedOutBookEntity.setBookID(rs.getInt("book_id"));
                    checkedOutBookEntity.setLibraryMemberID(rs.getInt("libraryMember_id"));
                    checkedOutBookEntity.setCheckedOutDate(rs.getDate("dateCheckedOut").toLocalDate());
                    checkedOutBookEntity.setFees(rs.getDouble("fees"));
                    checkedOutBookEntities.add(checkedOutBookEntity);
                }
            }
        }
        return checkedOutBookEntities;
    }

    public List<CheckedOutBookEntity> findByCheckedOutBookByLibraryMemberID(Integer libraryMemberID) throws SQLException{
        String sql = "SELECT * FROM checkedOutBook WHERE libraryMember_id = ?";
        List<CheckedOutBookEntity> checkedOutBookEntities = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, libraryMemberID);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    CheckedOutBookEntity checkedOutBookEntity = new CheckedOutBookEntity();
                    checkedOutBookEntity.setId(rs.getInt("id"));
                    checkedOutBookEntity.setBookID(rs.getInt("book_id"));
                    checkedOutBookEntity.setLibraryMemberID(rs.getInt("libraryMember_id"));
                    checkedOutBookEntity.setCheckedOutDate(rs.getDate("dateCheckedOut").toLocalDate());
                    checkedOutBookEntity.setFees(rs.getDouble("fees"));
                    checkedOutBookEntities.add(checkedOutBookEntity);
                }
            }
        }
        return checkedOutBookEntities;
    }

}
