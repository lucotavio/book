package br.com.luciano.library.controller;

import br.com.luciano.library.dto.BookDTO;
import br.com.luciano.library.model.Book;
import br.com.luciano.library.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private ModelMapper mapper;

    public BookController(BookService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id){
        Book book = service.findById(id);
        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        return ResponseEntity.ok().body(bookDTO);
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> findById(){
        List<Book> books = service.findAll();
        List<BookDTO> dtos = books.stream()
                .map(book -> mapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<BookDTO> save(@RequestBody BookDTO dto){
        Book book = mapper.map(dto, Book.class);
        book = service.saveOrUpdate(book);
        dto = mapper.map(book, BookDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }





    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO dto){
        Book book = mapper.map(dto, Book.class);
        book = service.saveOrUpdate(book);
        dto = mapper.map(book, BookDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BookDTO> deleteById(@PathVariable Long id){
        System.out.println(id);
        return null;
    }
}
