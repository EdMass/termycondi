package com.co.sofka.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "aceptyc")
public class AcepTyCEntity {
    private String tipoDocumento;
    @NonNull
    private String documento;
    private Integer versionAcep;
    private LocalDateTime fechaAcep;
}
