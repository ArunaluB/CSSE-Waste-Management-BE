package edu.sliit.servise.impl;

import edu.sliit.document.User;
import edu.sliit.dto.GetUserDto;
import edu.sliit.dto.UserDto;
import edu.sliit.repository.UserRepository;
import edu.sliit.servise.UserServise;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiseImpl implements UserServise {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> addUser(UserDto dto) {
        User mapEntity = modelMapper.map(dto,User.class);
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
