package com.co.sofka.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AcepTyCDto {
    private String tipoDocumento;
    @NonNull
    private String documento;
    private Integer versionAcep;
}
