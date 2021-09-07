package com.codecafe.bookshop.book;

import com.codecafe.bookshop.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void shouldListAllBooksWhenPresent() throws Exception {
        List<BookView> books = new ArrayList<>();
        BookView bookView = BookView.builder()
                .name("Dark Matter")
                .author("Blake Crouch")
                .price(500.00)
                .build();
        books.add(bookView);
        when(bookService.fetchAll()).thenReturn(books);

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));


        verify(bookService, times(1)).fetchAll();
    }

}