package com.envwrd.arenarank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequest {

    @NotBlank(message = "O nome do time é obrigatório")
    @Size(min = 3, max = 60, message = "O nome do time deve ter entre 3 e 60 caracteres")
    private String name;

    @NotBlank(message = "A tag do time é obrigatória")
    @Size(min = 2, max = 8, message = "A tag deve ter entre 2 e 8 caracteres")
    private String tag;
}