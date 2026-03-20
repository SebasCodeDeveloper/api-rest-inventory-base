package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.DtoUser;
import com.prueba.pruebaExamen.dto.GetOrderByEmailRq;
import com.prueba.pruebaExamen.entity.Order;
import com.prueba.pruebaExamen.entity.User;
import com.prueba.pruebaExamen.exception.BusinessErrorType;
import com.prueba.pruebaExamen.exception.UserException;
import com.prueba.pruebaExamen.repository.OrderRepository;
import com.prueba.pruebaExamen.repository.UserRepository;
import com.prueba.pruebaExamen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrderRepository  orderRepository;

    /**
     * Procesa la creación de un nuevo usuario con validación de unicidad de email.
     */
    @Override
    public DtoUser create(@NonNull DtoUser request) {

        // Garantizar que el correo no esté duplicado en el sistema
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(
                    "El usuario " + request.getEmail() + " ya esta registrado", BusinessErrorType.EMAIL_IN_USE);
        }

        //Convierte el DTO de entrada a la Entidad JPA
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
    public List<DtoUser> findAll() {
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
     * Buscar usuario por su email (email).
     */
    @Override
    @Transactional(readOnly = true)
    public List<DtoUser> getByEmail(GetOrderByEmailRq request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> List.of(toRs(user)))
                .orElseThrow(() -> new UserException(
                        "No se encontraron registros para el email: " + request.getEmail(),
                        BusinessErrorType.NOT_FOUND
                ));
    }


    /**
     * Busca un usuario mediante su identificador técnico (UUID).
     */
    @Override
    public DtoUser findById(UUID id) {
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
    @Transactional
    public void delete(UUID id) {
        //Buscamos el usuario o lanzamos excepción si no existe
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new  UserException("El usuario no existe",  BusinessErrorType.NOT_FOUND));
        userRepository.delete(user);

        Order order = orderRepository.findByUserId(user.getId());
        if (order != null) {
            throw new UserException("Usuario asociado a una orden.",BusinessErrorType.CONFLICT);
        }
    }

    /**
     * Actualiza un usuario existente, permitiendo cambios de email con validación de conflictos.
     */
    @Override
    public DtoUser update(UUID id, DtoUser request) {

        // Verifica existencia previa del recurso
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        "El usuario no existe",
                        BusinessErrorType.NOT_FOUND
                ));

        //Si cambia el email, este no debe existir en otro registro
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
    private DtoUser toRs(@NonNull User user) {
        DtoUser rs = new DtoUser();
        rs.setId(user.getId());
        rs.setName(user.getName());
        rs.setEmail(user.getEmail());
        rs.setAge(user.getAge());

        return rs;
    }
}