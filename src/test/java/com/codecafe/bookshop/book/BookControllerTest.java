package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.*;
import com.codecafe.bookshop.book.persistence.BookEntity;
import com.codecafe.bookshop.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@WithMockUser
public class BookControllerTest {

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
        ListBooksResponse listBooksResponse = new ListBooksResponse(1, bookViews);
        when(bookService.createFrom(bookViews)).thenReturn(listBooksResponse);

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(1)));


        verify(bookService, times(1)).fetchAll();
    }

    @Test
    void shouldReturnAnEmptyResponseWhenNoBooksArePresent() throws Exception {
        when(bookService.fetchAll()).thenReturn(new ArrayList<>());
        when(bookService.createFrom(any())).thenReturn(new ListBooksResponse(0, new ArrayList<>()));

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(0)));

        verify(bookService, times(1)).fetchAll();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldRespond403WhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldCreateBookWhenInputIsValid() throws Exception {
        List<Book> books = Collections.singletonList(Book.builder()
                .name("Dark Matter")
                .author("Blake Crouch")
                .price(500.00)
                .publicationYear(2000)
                .isbn("2343242243")
                .build());
        AddBooksRequest addBooksRequest = new AddBooksRequest(books);

        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity bookEntity = BookEntity.builder()
                .id(1L)
                .name("Dark Matter")
                .author("Blake Crouch")
                .price(500.00)
                .publicationYear(2000)
                .isbn("2343242243")
                .booksCount(1)
                .build();
        bookEntities.add(bookEntity);
        when(bookService.addBooks(addBooksRequest)).thenReturn(bookEntities);

        AddBooksResponse addBooksResponse = new AddBooksResponse(books);
        when(bookService.toResponse(bookEntities)).thenReturn(addBooksResponse);

        mockMvc.perform(post("/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addBooksRequest)))
                .andExpect(status().isCreated());

    }

}