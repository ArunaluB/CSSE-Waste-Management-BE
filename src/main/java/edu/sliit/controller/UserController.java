package edu.sliit.controller;

import edu.sliit.dto.GetUserDto;
import edu.sliit.dto.UserDto;
import edu.sliit.servise.UserServise;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserServise userServise;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser (@RequestBody UserDto dto){
        log.info("User Registration for [{}]", dto);
        return userServise.addUser(dto);
    }

    @GetMapping("getUsers")
    public List<GetUserDto> getUsers(@NotNull @RequestParam String username ,String password ){
         return userServise.getUsers(username,password);
    }

    @GetMapping("getUsers")
    public List<GetUserDto> getUsers(@NotNull @RequestParam String username){
          return  userServise.getUser(username);
    }

    @GetMapping("getstatus")
    public String getStatus (@NotNull @RequestParam String userid){
        return userServise.getStauts(userid);
    }

    @GetMapping("getpoints")
    public Number getPoints (@NotNull @RequestParam String userid){
        return userServise.getPoints(userid);
    }

}
