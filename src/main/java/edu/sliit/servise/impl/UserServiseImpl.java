package edu.sliit.servise.impl;

import edu.sliit.dto.GetUserDto;
import edu.sliit.dto.UserDto;
import edu.sliit.servise.UserServise;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiseImpl implements UserServise {

    @Override
    public ResponseEntity<String> addUser(UserDto dto) {
        return null;
    }

    @Override
    public List<GetUserDto> getUsers(String username, String password) {
        return null;
    }

    @Override
    public List<GetUserDto> getUser(String username) {
        return null;
    }

    @Override
    public ResponseEntity<String> UpdateStauts(String userid, String status) {
        return null;
    }

    @Override
    public ResponseEntity<String> UpdatePoints(String userid, Number points) {
        return null;
    }

    @Override
    public String getStauts(String userid) {
        return null;
    }

    @Override
    public Number getPoints(String userid) {
        return null;
    }
}
