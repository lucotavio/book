package br.com.luciano.library.service;

import br.com.luciano.library.repository.BookRepository;
import br.com.luciano.library.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

     public BookService(BookRepository repository) {
          this.repository = repository;
     }

     private BookRepository repository;

     public Book findById(Long id){
          return repository.findById(id).orElseThrow( () ->new RuntimeException("Book notFound"));
     }

     public List<Book> findAll(){
          return repository.findAll();
     }

     public Book saveOrUpdate(Book book){
          return repository.save(book);
     }

     public boolean deleteById(Long id){
          try{
               repository.deleteById(id);
               return true;
          }catch(EmptyResultDataAccessException ex){
               return false;
          }
     }
}
