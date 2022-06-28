package com.example.demo.dto;

import com.example.demo.medel.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDTO {
    private String id;
    private String name;
    private String type;
    private String owner;
    @Lob
    private byte[] data;

    public FileDTO(final FileEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.type = entity.getType();
        this.data = entity.getData();
        this.owner = entity.getOwner();
    }

    public static FileEntity toEntity(final FileDTO dto) {
        return FileEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .owner(dto.getOwner())
                .build();
    }
}
