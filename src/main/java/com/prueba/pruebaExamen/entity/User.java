    package com.prueba.pruebaExamen.entity;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
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
    @Entity
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

        /**
         * Relación OneToMany.
         * Un usuario puede tener muchas órdenes.
         * El 'mappedBy' debe coincidir con el nombre del campo en la entidad Order.
         */
        // ✅ IMPORTANTE: Debe ser una List
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Order> orders = new ArrayList<>();

    }