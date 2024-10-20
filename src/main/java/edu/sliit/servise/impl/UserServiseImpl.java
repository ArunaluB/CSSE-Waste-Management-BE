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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiseImpl implements UserServise {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> addUser(UserDto dto) {
        long userCount = userRepository.count();
        String userId = "US" + (userCount + 1);
        User mapEntity = modelMapper.map(dto,User.class);
        mapEntity.setUserId(userId);
        userRepository.save(mapEntity);
        return ResponseEntity.ok("User added successfully with ID: " + userId);
    }

    @Override
    public List<GetUserDto> getUsers(String username, String password) {
        List<User> users =  userRepository.findByUsernameAndPassword(username, password);

        return users.stream()
                .map(user -> modelMapper.map(user, GetUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserDto> getUser(String username) {
        List<User> users =  userRepository.findByUsername(username);
        return  users.stream()
                .map(user -> modelMapper.map(user, GetUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> UpdateStauts(String userid, String status) {
        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(status);
            userRepository.save(user);
            return ResponseEntity.ok("Status updated successfully for User ID: " + userid);
        } else {
            return ResponseEntity.status(404).body("User not found with ID: " + userid);
        }

    }

    @Override
    public ResponseEntity<String> UpdatePoints(String userid, Number points) {
        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPoints(points);
            userRepository.save(user);
            return ResponseEntity.ok("Points updated successfully for User ID: " + userid);
        } else {
            return ResponseEntity.status(404).body("User not found with ID: " + userid);
        }
    }

    @Override
    public String getStauts(String userid) {
        Optional<User> userOptional = userRepository.findById(userid);
        return userOptional.map(User::getStatus).orElse("User not found with ID: " + userid);

    }

    @Override
    public Number getPoints(String userid) {
        Optional<User> userOptional = userRepository.findById(userid);
        return userOptional.map(User::getPoints).orElse(null);

    }
}
