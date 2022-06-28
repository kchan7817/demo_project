package com.example.demo.service;

import com.example.demo.medel.FileEntity;
import com.example.demo.medel.TodoEntity;
import com.example.demo.persistence.FileRepository;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class FileService {

    @Autowired
    private FileRepository file_repository;

    @Autowired
    private UserRepository user_repository;

    private void validate(final FileEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getId() == null) {
            log.warn("Unknown Id.");
            throw new RuntimeException("Unknown id");
        }

        if(entity.getType() == null) {
            log.warn("Unknown type.");
            throw new RuntimeException("Unknown type");
        }

        if(user_repository.getById(entity.getOwner()) == null) {
            log.warn("Unknown owner.");
            throw new RuntimeException("Unknown owner");
        }

        if(entity.getData() == null) {
            log.warn("Unknown data.");
            throw new RuntimeException("Unknown data");
        }
    }

    public void store(final FileEntity entity) {
        // Valdiations
        validate(entity);

        file_repository.save(entity);

        log.info("File Entity Id : {} is saved.", entity.getId());

        return;
    }


}
