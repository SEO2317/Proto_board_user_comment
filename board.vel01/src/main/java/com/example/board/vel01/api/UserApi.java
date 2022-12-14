package com.example.board.vel01.api;

import com.example.board.vel01.base.jwt.JwtTokenProvider;
import com.example.board.vel01.domain.User;

import com.example.board.vel01.repository.UserRepository;
import com.example.board.vel01.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class UserApi {


    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    
    private final UserRepository userRepository;

    @GetMapping("/find")
    public List<User.Response> findAllUser(){
        List<User> users = userService.findAllUser();
        List<User.Response> response = User.Response.toResponseList(users);
        return response;
    }


    @PostMapping("/sign-up")
    public ResponseEntity<User.Response> signUpUser(@RequestBody User.Request request) {
        User user = User.Request.toEntity();

        User savedUser = userService.saveUser(user);
        User.Response response = User.Response.toResponse(savedUser);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<User.Response> signInUser(@RequestBody User.Request request) {

        String token = jwtTokenProvider.makeJwtToken(request);

        User user = userRepository.findByNickName(request.getNickName()); //수정

        User.Response responseData = User.Response.toResponse(user);
        responseData.setToken(token);
        return ResponseEntity.ok().body(responseData);
    }
    
    }
