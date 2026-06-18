package com.envwrd.arenarank.dto.request;

import com.envwrd.arenarank.domain.PlayerRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRequest {

    @NotBlank(message = "O nickname é obrigatório")
    @Size(min = 3, max = 40, message = "O nickname deve ter entre 3 e 40 caracteres")
    private String nickname;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;

    @NotNull(message = "A função do jogador é obrigatória")
    private PlayerRole role;
}