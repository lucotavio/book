package br.com.luciano.library.controller;

import br.com.luciano.library.BookRepository;
import br.com.luciano.library.dto.BookDTO;
import br.com.luciano.library.model.Book;
import br.com.luciano.library.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String BOOK_API = "/api/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService service;

    @MockBean
    private BookRepository repository;

    @MockBean
    private ModelMapper mapper;

    @Test
    public void findById() throws Exception{

        Book book = Book.builder().id(10L).title("Senhor dos aneis").author("J.R.R Tolken").isbn("123123").build();
        BookDTO dto = BookDTO.builder().id(10L).title("Senhor dos aneis").author("J.R.R Tolken").isbn("123123").build();

        given(service.findById(10L)).willReturn(book);
        given(mapper.map(any(Book.class), eq(BookDTO.class))).willReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/books/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value("Senhor dos aneis"))
                .andExpect(jsonPath("author").value("J.R.R Tolken"))
                .andExpect(jsonPath("isbn").value("123123"));


    }

    @Test
    public void findByAll() throws Exception{

        List<Book> books = new ArrayList<>();
        Book book = Book.builder().id(10L).title("Senhor dos aneis").author("J.R.R Tolken").isbn("123123").build();
        books.add(book);
        BookDTO dto = BookDTO.builder().id(10L).title("Senhor dos aneis").author("J.R.R Tolken").isbn("123123").build();

        given(service.findAll()).willReturn(books);
        given(mapper.map(any(Book.class), eq(BookDTO.class))).willReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].title").value("Senhor dos aneis"))
                .andExpect(jsonPath("$[0].author").value("J.R.R Tolken"))
                .andExpect(jsonPath("$[0].isbn").value("123123"));


    }


    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void saveTest ()  throws Exception {

        BookDTO bookDTOBeforeSaved = BookDTO.builder().author("Artur").title("As aventuras").isbn("123123").build();
        BookDTO bookDTOSaved = BookDTO.builder().id(10L).author("Artur").title("As aventuras").isbn("123123").build();
        Book bookBeforeSaved = Book.builder().author("Artur").title("As aventuras").isbn("123123").build();
        Book bookSaved = Book.builder().id(10L).author("Artur").title("As aventuras").isbn("123123").build();
        given(mapper.map(any(BookDTO.class), eq(Book.class))).willReturn(bookBeforeSaved);
        given(service.saveOrUpdate(any(Book.class))).willReturn(bookSaved);
        given(mapper.map(any(Book.class), eq(BookDTO.class))).willReturn(bookDTOSaved);
        //Mockito.when(service.save(Mockito.any(Book.class))).thenReturn(bookSaved);
        String json = new ObjectMapper().writeValueAsString(bookDTOBeforeSaved);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mvc.perform(request)
           .andExpect(status().isCreated())
           .andExpect(jsonPath("id").isNotEmpty())
           .andExpect(jsonPath("title").value("As aventura"))
           .andExpect(jsonPath("author").value("Artur"))
           .andExpect(jsonPath("isbn").value("123123"));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não ouver dados sufucientes para criação do livro")
    public void createInvalidBookTest() {

    }

    @Test
    public void update() throws Exception{

        BookDTO bookDTO = BookDTO.builder().author("Artur").title("As aventuras").isbn("123123").build();
        String json = new ObjectMapper().writeValueAsString(bookDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/books/{id}", 69)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value("As aventuras"))
                .andExpect(jsonPath("author").value("Artur"))
                .andExpect(jsonPath("isbn").value("123123"));
    }





    @Test
    public void delete() throws Exception{

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/books/{id}", 21)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }
}
