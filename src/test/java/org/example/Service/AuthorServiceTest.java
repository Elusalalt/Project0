package org.example.Service;



import org.example.Repository.DAO.AuthorDAO;
import org.example.Repository.Entities.AuthorEntity;
import org.example.Service.Models.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorDAO authorDAO;

    @InjectMocks
    private AuthorService authorService;


    private AuthorEntity testAuthorEntity;
    private Author testAuthorModel;
    @BeforeEach
    void setUp() {
        testAuthorEntity = new AuthorEntity();
        testAuthorEntity.setId(2);
        testAuthorEntity.setAuthor("Stephen King");

        testAuthorModel = new Author();
        testAuthorModel.setId(2);
        testAuthorModel.setAuthorName("Stephen King");


    }


    @Test
    void createEntity_Success_ReturnsNewId() throws SQLException {
        when(authorDAO.create(testAuthorEntity)).thenReturn(100);

        Integer result = authorService.createEntity(testAuthorEntity);

        assertEquals(100, result);

        verify(authorDAO, times(1)).create(testAuthorEntity);
    }

    @Test
    void createEntity_ThrowsSQLException_ReturnsNegativeOne() throws SQLException{
        // Arrange
        when(authorDAO.create(testAuthorEntity)).thenThrow(new SQLException("Database error"));

        // Act
        Integer result = authorService.createEntity(testAuthorEntity);

        // Assert
        assertEquals(-1, result);
        verify(authorDAO, times(1)).create(testAuthorEntity);
    }
    @Test
    void getEntityById() {

    }

    @Test
    void getAllEntities() {
    }

    @Test
    void convertEntityToModel() {
    }

    @Test
    void getModelById() {
    }

    @Test
    void getModelByAuthorName() {
    }

    @Test
    void getAllModels() {
    }
}