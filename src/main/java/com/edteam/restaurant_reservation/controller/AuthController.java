package com.edteam.restaurant_reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.edteam.restaurant_reservation.dto.request.AuthRequestDTO;
import com.edteam.restaurant_reservation.dto.request.SignupRequestDTO;
import com.edteam.restaurant_reservation.dto.response.AuthResponseDTO;
import com.edteam.restaurant_reservation.dto.response.UserProfileResponseDTO;
import com.edteam.restaurant_reservation.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponseDTO> signIn(@RequestBody AuthRequestDTO authRequest) {
    AuthResponseDTO authResponse = userService.signIn(authRequest);
    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<UserProfileResponseDTO> register(@RequestBody @Validated SignupRequestDTO signupRequestDTO) {
    UserProfileResponseDTO userProfileResponseDTO = userService.signup(signupRequestDTO);
    return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.CREATED);
  }

}