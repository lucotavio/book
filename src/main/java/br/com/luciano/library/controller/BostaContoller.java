package br.com.luciano.library.controller;

import br.com.luciano.library.service.BostaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BostaContoller {

    @Autowired
    private BostaService bostaService;

    @Autowired
    private ModelMapper mapper;

    public BostaContoller(BostaService bostaService, ModelMapper mapper) {
        this.bostaService = bostaService;
        this.mapper = mapper;
    }
}
