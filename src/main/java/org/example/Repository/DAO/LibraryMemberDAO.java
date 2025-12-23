package org.example.Repository.DAO;

import org.example.Repository.Entities.LibraryMemberEntity;
import org.example.Util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryMemberDAO implements DAOInterface<LibraryMemberEntity> {
    private Connection connection = ConnectionHandler.getConnection();


    @Override
    public Integer create(LibraryMemberEntity libraryMemberEntity) throws SQLException {                                //MORE NEEDS TO BE ADDED TO THIS
        String sql = "INSERT INTO libraryMember (memberName,fees) VALUES (?,?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, libraryMemberEntity.getName());
            stmt.setDouble(2,0.0);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;

    }

    @Override
    public Optional<LibraryMemberEntity> findById(Integer id) throws SQLException {                                     //MORE SETTERS
        String sql = "SELECT * FROM libraryMember WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    LibraryMemberEntity libraryMemberEntity = new LibraryMemberEntity();
                    libraryMemberEntity.setId(rs.getInt("id"));
                    libraryMemberEntity.setName(rs.getString("memberName"));
                    libraryMemberEntity.setFeesOwed(rs.getDouble("fees"));

                    return Optional.of(libraryMemberEntity);
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public List<LibraryMemberEntity> findAll() throws SQLException {                                                    //MORE SETTERS
        List<LibraryMemberEntity> libraryMembers = new ArrayList<>();

        String sql = "SELECT * FROM libraryMember";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                LibraryMemberEntity libraryMemberEntity = new LibraryMemberEntity();
                libraryMemberEntity.setId(rs.getInt("id"));
                libraryMemberEntity.setName(rs.getString("memberName"));
                libraryMemberEntity.setFeesOwed(rs.getDouble("fees"));
                libraryMembers.add(libraryMemberEntity);
            }
        }
        return libraryMembers;

    }

    @Override
    public LibraryMemberEntity updateById(LibraryMemberEntity libraryMemberEntity) throws SQLException {                             //IMPLEMENT THIS
        String sql = "UPDATE libraryMember SET memberName=?, fees=? WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,libraryMemberEntity.getName());
            stmt.setDouble(2,libraryMemberEntity.getFeesOwed());
            stmt.setInt(3,libraryMemberEntity.getId());
            stmt.executeUpdate();

            return libraryMemberEntity;
        }
        //return null;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {                                                         //IMPLEMENT THIS

        String sql = "DELETE FROM libraryMember WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            stmt.executeUpdate();
            return true;
        }//return false;
    }
    public Optional<LibraryMemberEntity> findByLibraryMemberName(String libraryMemberName) throws SQLException{         //THIS NEEDS MORE WORK (make into a list?)
        String sql = "SELECT * FROM libraryMember WHERE memberName = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, libraryMemberName);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    LibraryMemberEntity libraryMemberEntity = new LibraryMemberEntity();
                    libraryMemberEntity.setId(rs.getInt("id"));
                    libraryMemberEntity.setName(rs.getString("memberName"));

                    return Optional.of(libraryMemberEntity);
                }
            }
        }
        return Optional.empty();
    }

}
