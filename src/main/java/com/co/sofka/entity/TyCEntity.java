package com.co.sofka.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "termycond")
public class TyCEntity {
    private String texto;
    private Integer version;
    private LocalDateTime fechaCreacion;
}
