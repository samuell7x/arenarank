package com.envwrd.arenarank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TournamentStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.status = TournamentStatus.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void start() {
        if (this.status != TournamentStatus.OPEN) {
            throw new IllegalArgumentException("Apenas torneios abertos podem ser iniciados");
        }

        this.status = TournamentStatus.IN_PROGRESS;
    }

    public void finish() {
        if (this.status != TournamentStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Apenas torneios em andamento podem ser finalizados");
        }

        this.status = TournamentStatus.FINISHED;
    }
}