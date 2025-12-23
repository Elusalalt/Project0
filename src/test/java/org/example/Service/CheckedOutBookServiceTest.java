package org.example.Service;

import org.example.Repository.DAO.CheckedOutBookDAO;
import org.example.Repository.Entities.BookEntity;
import org.example.Repository.Entities.CheckedOutBookEntity;
import org.example.Repository.Entities.LibraryMemberEntity;
import org.example.Service.Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckedOutBookServiceTest {

    @Mock
    private CheckedOutBookDAO checkedOutBookDAO;

    @Mock
    private LibraryMemberService libraryMemberService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private CheckedOutBookService checkedOutBookService;

    private CheckedOutBookEntity checkedOutBookEntity;
    private CheckedOutBook checkedOutBook;
    private Author author;
    private Genre genre;
    private Book book;
    private LibraryMember libraryMember;
    @BeforeEach
    void Setup(){
        author = new Author();
        author.setAuthorName("Test Author");
        author.setId(1);

        genre= new Genre();
        genre.setGenre("Test Genre");
        genre.setId(1);

        book = new Book();
        book.setAuthor(author);
        book.setNumberCheckedOut(1);
        book.setGenre(genre);
        book.setId(1);
        book.setTotalOwnedByLibrary(2);
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");

        libraryMember = new LibraryMember();
        libraryMember.setName("Test Member");
        libraryMember.setId(1);
        libraryMember.setFeesOwed(0.0);

        checkedOutBook = new CheckedOutBook();
        checkedOutBook.setFees(0.0);
        checkedOutBook.setBook(book);
        checkedOutBook.setId(1);
        checkedOutBook.setCheckedOutDate(LocalDate.now());
        checkedOutBook.setLibraryMember(libraryMember);

    }
    // -----------------------------
    // calculateFeesOwedOnCheckedOutBook
    // -----------------------------


    @Test
    void calculateFees_overdue_setsFeesAndUpdatesEntity() throws SQLException {
        CheckedOutBook cob = buildCheckedOutBook(1, 20); // 6 overdue days

        when(checkedOutBookDAO.updateById(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Double fees = checkedOutBookService.calculateFeesOwedOnCheckedOutBook(cob);

        assertEquals(15.0, fees);
        verify(checkedOutBookDAO).updateById(checkedOutBookEntity);
    }

    // -----------------------------
    // validateCheckoutRequest
    // -----------------------------

    @Test
    void validateCheckoutRequest_memberHasFees_returnsFalse() {
        LibraryMember member = new LibraryMember();
        member.setId(1);
        member.setFeesOwed(5.0);

        Book book = new Book();
        book.setId(1);
        book.setTitle("Test");

        when(bookService.availableForCheckout(book)).thenReturn(true);
        when(bookService.getModelByBookName("Test")).thenReturn(Optional.of(book));

        assertFalse(checkedOutBookService.validateCheckoutRequest(member, book));
    }
    // -----------------------------
    // checkOutABook
    // -----------------------------

    @Test
    void checkOutABook_validationFails_returnsEmpty() {
        LibraryMember member = new LibraryMember();
        member.setId(1);
        member.setFeesOwed(50.0);

        Book book = new Book();
        book.setId(1);

        Optional<CheckedOutBook> result =
                checkedOutBookService.checkOutABook(book, member);

        assertTrue(result.isEmpty());
        verifyNoInteractions(checkedOutBookDAO);
    }

    @Test
    void checkOutABook_success_createsRecord() throws SQLException {
        LibraryMember member = new LibraryMember();
        member.setId(1);
        member.setFeesOwed(0.0);

        Book book = new Book();
        book.setId(1);
        book.setTitle("Test");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        bookEntity.setNumberCheckedOut(0);

        when(bookService.availableForCheckout(book)).thenReturn(true);
        when(bookService.getModelByBookName("Test")).thenReturn(Optional.of(book));
        when(bookService.getEntityById(1)).thenReturn(Optional.of(bookEntity));
        when(checkedOutBookDAO.create(any())).thenReturn(99);

        Optional<CheckedOutBook> result =
                checkedOutBookService.checkOutABook(book, member);

        assertTrue(result.isPresent());
        assertEquals(99, result.get().getId());
        verify(checkedOutBookDAO).create(any());
        verify(bookService).updateEntity(eq(1), any(BookEntity.class));
    }

    // -----------------------------
    // returnBook
    // -----------------------------

    @Test
    void returnBook_success_updatesAndDeletes() throws SQLException {
        CheckedOutBook cob = buildCheckedOutBook(10, 20);

        LibraryMemberEntity memberEntity = new LibraryMemberEntity();
        memberEntity.setId(1);
        memberEntity.setFeesOwed(0.0);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        bookEntity.setNumberCheckedOut(1);

        when(libraryMemberService.getEntityById(1)).thenReturn(Optional.of(memberEntity));
        when(bookService.getEntityById(1)).thenReturn(Optional.of(bookEntity));
        when(checkedOutBookDAO.deleteById(10)).thenReturn(true);

        assertTrue(checkedOutBookService.returnBook(cob));

        verify(libraryMemberService).updateEntity(eq(1), any());
        verify(bookService).updateEntity(eq(1), any());
        verify(checkedOutBookDAO).deleteById(10);
    }

    // -----------------------------
    // Helpers
    // -----------------------------

    private CheckedOutBook buildCheckedOutBook(int id, int daysAgo) {
        LibraryMember member = new LibraryMember();
        member.setId(1);
        member.setFeesOwed(0.0);

        Book book = new Book();
        book.setId(1);

        CheckedOutBook cob = new CheckedOutBook();
        cob.setId(id);
        cob.setBook(book);
        cob.setLibraryMember(member);
        cob.setFees(0.0);
        cob.setCheckedOutDate(LocalDate.now().minusDays(daysAgo));

        return cob;
    }
}
