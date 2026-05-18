package com.prueba.pruebaExamen.dto;

import java.util.UUID;

public record ProductUpdateWrapper(
        ProductRq productRq,
        AdminAuthRq auth
){}