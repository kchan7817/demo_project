package com.example.demo.controller;

import com.example.demo.dto.FileDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.medel.FileEntity;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/file",
        method = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE
        })
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping("/post")
    public ResponseEntity<?> createTodo(@RequestBody FileDTO dto, @AuthenticationPrincipal String userId) {

        try {
            FileEntity entity = FileDTO.toEntity(dto);
            entity.setId(null);
            entity.setOwner(userId);

            service.store(entity);

            return create_OKresponse();
        } catch(Exception e) {
            return create_BADresponse(e);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        return create_OKresponse(service.retrieve(userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {

        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUSERID(userId);

            return create_OKresponse(service.delete(entity));
        } catch(Exception e) {
            return create_BADresponse(e);
        }
    }

    private ResponseEntity<?> create_OKresponse(List<TodoEntity> entity) {
        List<TodoDTO> dtos = entity.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        response.setLength(dtos.size());
        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<?> create_BADresponse(Exception e) {
        String error = e.getMessage();
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
        return ResponseEntity.badRequest().body(response);
    }
}
