    package com.prueba.pruebaExamen.entity;

    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.JdbcTypeCode;
    import org.hibernate.type.SqlTypes;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;

    /**
     * Entidad que representa la tabla 'users' en la base de datos.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Table(name = "users")
    public class User {

        /**
         * Identificador único de tipo UUID.
         * Se almacena como CHAR en la BD para compatibilidad.
         */
        @Id
        @JdbcTypeCode(SqlTypes.CHAR)
        @GeneratedValue
        private UUID id;

        /**
         * Nombre del usuario con restricción de longitud a nivel BD.
         */
        @Size(max = 50)
        @Column(nullable = false, length = 50)
        private String name;

        /**
         * Correo electrónico único para evitar registros duplicados.
         */
        @Column(nullable = false, unique = true)
        private String email;

        /**
         * Edad del usuario. No permite valores nulos en la persistencia.
         */
        @Column(nullable = false)
        private Integer age;

    }