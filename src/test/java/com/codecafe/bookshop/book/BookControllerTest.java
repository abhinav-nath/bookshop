package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.*;
import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.service.BookService;
import com.codecafe.bookshop.error.exception.BookNotFoundException;
import com.codecafe.bookshop.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@WithMockUser
public class BookControllerTest {

    public static final String MUST_NOT_BE_EMPTY = "must not be empty";
    public static final String MUST_BE_GREATER_THAN_0 = "must be greater than 0";
    public static final String MUST_NOT_BE_NULL = "must not be null";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldListAllBooksWhenPresent() throws Exception {
        List<BookView> bookViews = Collections.singletonList(BookView.builder()
                .id(1L)
                .name("Dark Matter")
                .author("Blake Crouch")
                .price(500.00)
                .build());
        when(bookService.fetchAll()).thenReturn(bookViews);

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(bookService, times(1)).fetchAll();
    }

    @Test
    void shouldReturnAnEmptyResponseWhenNoBooksArePresent() throws Exception {
        when(bookService.fetchAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(bookService, times(1)).fetchAll();
    }

    @Test
    void shouldReturnBookDetails() throws Exception {
        Book book = getABook();
        when(bookService.fetchBookDetails(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dark Matter"));

        verify(bookService, times(1)).fetchBookDetails(1L);
    }

    @Test
    void shouldGive404WhenBookNotFound() throws Exception {
        when(bookService.fetchBookDetails(1L)).thenThrow(BookNotFoundException.class);

        mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).fetchBookDetails(1L);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldSuccessfullyDeleteABook() throws Exception {
        mockMvc.perform(delete("/admin/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldGive403WhileAddingBookWhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(delete("/admin/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldGive403WhileDeletingBookWhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldGive400WhenNameIsEmptyOrNull() throws Exception {
        AddBookRequest request = new AddBookRequestTestBuilder().withName("").build();
        validateBadRequest(request, "name", MUST_NOT_BE_EMPTY);

        verify(bookService, times(0)).addBook(any());

        request = new AddBookRequestTestBuilder().withName(null).build();
        validateBadRequest(request, "name", MUST_NOT_BE_EMPTY);

        verify(bookService, times(0)).addBook(any());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldGive400WhenAuthorIsEmptyOrNull() throws Exception {
        AddBookRequest request = new AddBookRequestTestBuilder().withAuthor("").build();
        validateBadRequest(request, "author", MUST_NOT_BE_EMPTY);

        verify(bookService, times(0)).addBook(any());

        request = new AddBookRequestTestBuilder().withAuthor(null).build();
        validateBadRequest(request, "author", MUST_NOT_BE_EMPTY);

        verify(bookService, times(0)).addBook(any());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldGive400WhenIsbnIsEmptyOrNull() throws Exception {
        AddBookRequest request = new AddBookRequestTestBuilder().withIsbn("").build();
        validateBadRequest(request, "isbn", MUST_NOT_BE_EMPTY);

        verify(bookService, times(0)).addBook(any());

        request = new AddBookRequestTestBuilder().withIsbn(null).build();
        validateBadRequest(request, "isbn", MUST_NOT_BE_EMPTY);

        verify(bookService, times(0)).addBook(any());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldGive400WhenPriceIsZeroNegativeOrNull() throws Exception {
        AddBookRequest request = new AddBookRequestTestBuilder().withPrice(0.0).build();
        validateBadRequest(request, "price", MUST_BE_GREATER_THAN_0);

        verify(bookService, times(0)).addBook(any());

        request = new AddBookRequestTestBuilder().withPrice(-300.00).build();
        validateBadRequest(request, "price", MUST_BE_GREATER_THAN_0);

        verify(bookService, times(0)).addBook(any());

        request = new AddBookRequestTestBuilder().withPrice(null).build();
        validateBadRequest(request, "price", MUST_NOT_BE_NULL);

        verify(bookService, times(0)).addBook(any());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldCreateBookWhenInputIsValid() throws Exception {
        AddBookRequest request = new AddBookRequestTestBuilder().build();

        Book book = getABook();
        when(bookService.addBook(any())).thenReturn(book);

        mockMvc.perform(post("/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).addBook(any());
    }

    private void validateBadRequest(AddBookRequest request, String fieldName, String expectedMessage) throws Exception {
        mockMvc.perform(post("/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors", Matchers.hasKey(fieldName)))
                .andExpect(jsonPath("$.errors", Matchers.hasValue(expectedMessage)));
    }

    private Book getABook() {
        return Book.builder()
                .id(1L)
                .name("Dark Matter")
                .author("Blake Crouch")
                .price(300.00)
                .publicationYear(2016)
                .isbn("1101904224")
                .booksCount(1)
                .averageRating(4.5)
                .build();
    }

}