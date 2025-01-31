package com.odeon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String departureCity;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String arrivalCity;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotEmpty
    @Column(unique = true)
    private String flightNumber;

    @NotNull
    private Integer capacity;

    @NotNull
    private Double price;
} 
