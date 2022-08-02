package com.example.demo.dto;

import com.example.demo.medel.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
    private String id;
    private String title;
    private boolean done;

    public BoardDTO(final BoardEntity entity) {
        this.id = entity.getID();
        this.title = entity.getTITLE();
        this.done = entity.isDONE();
    }

    public static BoardEntity toEntity(final BoardDTO dto) {
        return BoardEntity.builder()
                .ID(dto.getId())
                .TITLE(dto.getTitle())
                .DONE(dto.isDone())
                .build();
    }
}
