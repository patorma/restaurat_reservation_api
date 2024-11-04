package com.edteam.restaurant_reservation.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edteam.restaurant_reservation.domain.entity.User;
import com.edteam.restaurant_reservation.domain.enums.Role;
import com.edteam.restaurant_reservation.dto.request.AuthRequestDTO;
import com.edteam.restaurant_reservation.dto.request.SignupRequestDTO;
import com.edteam.restaurant_reservation.dto.response.AuthResponseDTO;
import com.edteam.restaurant_reservation.dto.response.UserProfileResponseDTO;
import com.edteam.restaurant_reservation.exception.BadRequestException;
import com.edteam.restaurant_reservation.exception.ResourceNotFoundException;
import com.edteam.restaurant_reservation.mapper.UserMapper;
import com.edteam.restaurant_reservation.repository.UserRepository;
import com.edteam.restaurant_reservation.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticateManagerBuilder;

  //se recuperan las credenciales en el inicio de sesion
  @Transactional(readOnly = true)
  public AuthResponseDTO signIn(AuthRequestDTO authRequest) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
      authRequest.getEmail(),
      authRequest.getPassword()
    );
    Authentication authentication = authenticateManagerBuilder.getObject().authenticate(authenticationToken);
    //asignamos ese usuario que quiere iniciar sesion a un token en el contexto de
    //spring security
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //creamos el token que se hace en un proceso de inicio de sesion
    String accessToken = tokenProvider.createAccessToken(authentication);
   //se hace busqueda de usuario por correo  para garantizar que sea el correcto
    UserProfileResponseDTO userProfileDTO = findByEmail(authRequest.getEmail());
//con lo anterior retornamos las respuestas
    return userMapper.toAuthResponseDTO(accessToken, userProfileDTO);
  }


//registro usuario
  @Transactional
  public UserProfileResponseDTO signup(SignupRequestDTO signupFormDTO) {
    boolean emailAlreadyExists = userRepository.existsByEmail(signupFormDTO.getEmail());

    if (emailAlreadyExists) {
      throw new BadRequestException("El email ya está siendo usado por otro usuario.");
    }

    User user = userMapper.toUser(signupFormDTO);
    user.setPassword(passwordEncoder.encode(signupFormDTO.getPassword()));
    user.setRole(Role.USER);

    userRepository.save(user);

    return userMapper.toUserProfileResponseDTO(user);
  }


  @Transactional(readOnly = true)
  public UserProfileResponseDTO findByEmail(String email) {
    User user = userRepository.findOneByEmail(email)
      .orElseThrow(ResourceNotFoundException::new);

    return userMapper.toUserProfileResponseDTO(user);
  }

}
