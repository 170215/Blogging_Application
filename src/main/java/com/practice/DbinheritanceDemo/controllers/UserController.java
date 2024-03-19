package com.practice.DbinheritanceDemo.controllers;


import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.ApiResponse;
import com.practice.DbinheritanceDemo.payloads.UserDto;
import com.practice.DbinheritanceDemo.repositories.UserRepository;
import com.practice.DbinheritanceDemo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.practice.DbinheritanceDemo.services.UserServicesImpl.userToDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping ("/")
    public ResponseEntity<UserDto> createTheUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto =userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/{user_id}")
    public ResponseEntity<UserDto> updateTheUser(@Valid @RequestBody UserDto userDto,@PathVariable("user_id") Integer uid) throws ResourceNotFoundException {

        UserDto updateUserDto = userService.updateUser(userDto,uid);
        return ResponseEntity.ok(updateUserDto);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteTheUser(@PathVariable("userId") Integer id) throws ResourceNotFoundException {
        userService.deleteUser(id);

        return new ResponseEntity(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllTheUsers(){
        List<UserDto> userDtos= userService.getAllUsers();
        if(userDtos.size()<=0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(userDtos));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getTheUserById(@PathVariable("user_id") Integer id) throws ResourceNotFoundException {
        UserDto userDto=userService.getUserById(id);
        if(userDto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(userDto));
    }
}

