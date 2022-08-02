package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dto.FileDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.medel.FileEntity;
import com.example.demo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@ModelAttribute MultipartFile file, @AuthenticationPrincipal String userId) {
        String message = "";
        try {
            storageService.store(file, userId);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();

            List<String> list = new ArrayList<>();
            list.add(message);
            return create_OKresponse(list);
        } catch (Exception e) {
            List<String> list = new ArrayList<>();
            list.add("failed to upload file");
            return create_BADresponse(list);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListFiles(@AuthenticationPrincipal String userId) {
        List<FileDTO> files = storageService.getAllFiles(userId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return new FileDTO(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        ResponseDTO<FileDTO> response = ResponseDTO.<FileDTO>builder().data(files).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id, @AuthenticationPrincipal String userId) {
        FileEntity fileDB = storageService.getFile(id, userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    private ResponseEntity<?> create_OKresponse(List<String> entity) {
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(entity).build();
        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<?> create_BADresponse(List<String> entity) {
        List<String> dtos = entity.stream().map(String::new).collect(Collectors.toList());
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(dtos).build();
        return ResponseEntity.badRequest().body(response);
    }
}