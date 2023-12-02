package com.api.semestralProjectVajko;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class YourEntityController {
    private final YourEntityRepository yourEntityRepository;

    public YourEntityController(YourEntityRepository yourEntityRepository) {
        this.yourEntityRepository = yourEntityRepository;
    }

    @GetMapping("/entities")
    public List<YourEntity> getAllEntities() {
        return yourEntityRepository.findAll();
    }

    // Add other endpoints as needed
}
