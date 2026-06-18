package com.envwrd.arenarank.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TournamentRequest {

    @NotBlank(message = "O nome do torneio é obrigatório")
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    private String name;

    @NotNull(message = "A data de início é obrigatória")
    @FutureOrPresent(message = "A data de início não pode estar no passado")
    private LocalDate startDate;

    @NotNull(message = "A data de término é obrigatória")
    @FutureOrPresent(message = "A data de término não pode estar no passado")
    private LocalDate endDate;
}