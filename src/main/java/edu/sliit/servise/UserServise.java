package edu.sliit.servise;

import edu.sliit.dto.GetUserDto;
import edu.sliit.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserServise {
     ResponseEntity<String> addUser(UserDto dto);
     List<GetUserDto> getUsers(String username, String password);
     List<GetUserDto> getUser(String username);
     ResponseEntity<String> UpdateStauts (String userid,String status);
     ResponseEntity<String> UpdatePoints (String userid,Number points);
     String getStauts(String userid);
     Number getPoints(String userid);

}
