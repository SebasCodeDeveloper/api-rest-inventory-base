package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminAuthRq(

        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {}