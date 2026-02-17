package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.entity.User;
import com.prueba.pruebaExamen.exception.BusinessErrorType;
import com.prueba.pruebaExamen.exception.UserException;
import com.prueba.pruebaExamen.dto.UserRq;
import com.prueba.pruebaExamen.dto.UserRs;
import com.prueba.pruebaExamen.repository.UserRepository;
import com.prueba.pruebaExamen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lógica de negocio para la gestión de usuarios.
 * Implementa el desacoplamiento entre controladores y persistencia.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Procesa la creación de un nuevo usuario con validación de unicidad de email.
     */
    @Override
    public UserRs userCreate(@NonNull UserRq request) {

        // Regla de Negocio: Garantizar que el correo no esté duplicado en el sistema
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(request.getEmail() +
                    " El usuario ya esta registrado", BusinessErrorType.EMAIL_IN_USE);
        }

        // Mapeo manual: Convierte el DTO de entrada a la Entidad JPA
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());

        User userSaved = userRepository.save(user);

        return toRs(userSaved);
    }

    /**
     * Recupera todos los usuarios y valida que la lista no sea vacía.
     */
    @Override
    public List<UserRs> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Si el repositorio no retorna datos, se lanza una excepción controlada
        if (users.isEmpty()) {
            throw new UserException("No se encontraron usuarios registrados en el sistema",
                    BusinessErrorType.NOT_FOUND);
        }

        return users.stream()
                .map(this::toRs)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario mediante su identificador técnico (UUID).
     */
    @Override
    public UserRs getUsers(UUID id) {
        return userRepository.findById(id)
                .map(this::toRs)
                .orElseThrow(() -> new UserException(
                        "Usuario no existente en base de datos ", BusinessErrorType.NOT_FOUND
                ));
    }

    /**
     * Realiza el borrado físico del registro tras validar su existencia.
     */
    @Override
    public void delete(UUID id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new  UserException("El usuario no existe",  BusinessErrorType.NOT_FOUND));
        userRepository.delete(user);
    }

    /**
     * Actualiza un usuario existente, permitiendo cambios de email con validación de conflictos.
     */
    @Override
    public UserRs update(UUID id, UserRq request) {

        // Verifica existencia previa del recurso
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        "El usuario no existe",
                        BusinessErrorType.NOT_FOUND
                ));

        // Validación de conflicto: Si cambia el email, este no debe existir en otro registro
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(
                    "El email " + request.getEmail() + " ya pertenece a otro usuario",
                    BusinessErrorType.EMAIL_IN_USE
            );
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());

        User userUpdate = userRepository.save(user);

        return toRs(userUpdate);
    }

    /**
     * Mapper privado para transformar Entidad (JPA) a DTO de Respuesta (RS).
     */
    private UserRs toRs(@NonNull User user) {
        UserRs rs = new UserRs();
        rs.setName(user.getName());
        rs.setEmail(user.getEmail());
        rs.setAge(user.getAge());

        return rs;
    }
}