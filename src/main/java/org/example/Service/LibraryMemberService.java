package org.example.Service;

import org.example.Repository.DAO.LibraryMemberDAO;
import org.example.Repository.Entities.LibraryMemberEntity;
import org.example.Service.Models.LibraryMember;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryMemberService implements ServiceInterface<LibraryMemberEntity, LibraryMember>{

    private LibraryMemberDAO libraryMemberDAO = new LibraryMemberDAO();

    public LibraryMemberService(){}

    @Override
    public Integer createEntity(LibraryMemberEntity libraryMemberEntity) {
        try{
            Integer newId = libraryMemberDAO.create(libraryMemberEntity);
            return newId;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<LibraryMemberEntity> getEntityById(Integer id) {
        try{
            Optional<LibraryMemberEntity> libraryMemberEntity = libraryMemberDAO.findById(id);
            if(libraryMemberEntity.isEmpty()){
                //throw new RuntimeException("LibraryMember not found");
            }

            return libraryMemberEntity;
        }catch(SQLException | RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<LibraryMemberEntity> getAllEntities() {
        try{
            List<LibraryMemberEntity> libraryMemberEntities = libraryMemberDAO.findAll();
            return libraryMemberEntities;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LibraryMemberEntity updateEntity(Integer id, LibraryMemberEntity newEntity) {
        try {
            return libraryMemberDAO.updateById(newEntity);
        }catch(SQLException e){
            System.out.println("Unable to update member, SQL Exception thrown");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try{
            return libraryMemberDAO.deleteById(id);
        }catch(SQLException e){
            System.out.println("unable to delete, SQL exception");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<LibraryMember> convertEntityToModel(LibraryMemberEntity libraryMemberEntity) {

        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setId(libraryMemberEntity.getId());
        libraryMember.setName(libraryMemberEntity.getName());
        libraryMember.setFeesOwed(libraryMemberEntity.getFeesOwed());

        return Optional.of(libraryMember);
    }

    @Override
    public Optional<LibraryMember> getModelById(Integer id) {
        Optional<LibraryMemberEntity> libraryMemberEntity = getEntityById(id);
        try{
            if(libraryMemberEntity.isPresent()){
                Optional<LibraryMember> libraryMember = convertEntityToModel(libraryMemberEntity.get());
                if(libraryMember.isPresent()){
                    return libraryMember;
                }else{
                    throw new RuntimeException("LibraryMemberEntity conversion failed");
                }
            }else{
                throw new RuntimeException("LibraryMemberEntity not found");
            }

        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<LibraryMember> getModelByLibraryMemberName(String libraryMemberName) {
        Optional<LibraryMemberEntity> libraryMemberEntity = getEntityByLibraryMemberName(libraryMemberName);
        try{
            if(libraryMemberEntity.isPresent()){
                Optional<LibraryMember> libraryMember = convertEntityToModel(libraryMemberEntity.get());
                if(libraryMember.isPresent()){
                    return libraryMember;
                }else{
                    throw new RuntimeException("LibraryMemberEntity conversion failed");
                }
            }else{
                throw new RuntimeException("LibraryMemberEntity not found");
            }
        }catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<LibraryMemberEntity> getEntityByLibraryMemberName(String libraryMemberName) {
        try{
            Optional<LibraryMemberEntity> libraryMemberEntity = libraryMemberDAO.findByLibraryMemberName(libraryMemberName);
            return libraryMemberEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<LibraryMember> getAllModels() {
        List<LibraryMemberEntity> libraryMemberEntities = getAllEntities();
        List<LibraryMember> libraryMembers = new ArrayList<>();
        for(LibraryMemberEntity libraryMemberEntity : libraryMemberEntities){
            Optional<LibraryMember> libraryMember = convertEntityToModel(libraryMemberEntity);
            if(libraryMember.isPresent()){
                libraryMembers.add(libraryMember.get());
            }
        }
        return libraryMembers;
    }



}
