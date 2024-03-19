package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    //we dont want to give entity in service
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer id) throws ResourceNotFoundException;

    UserDto getUserById(Integer id) throws ResourceNotFoundException;

    void deleteUser(Integer userId) throws ResourceNotFoundException;
    List<UserDto> getAllUsers();

}
