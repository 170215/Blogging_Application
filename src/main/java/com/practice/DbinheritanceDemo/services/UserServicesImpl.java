package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.UserDto;
import com.practice.DbinheritanceDemo.repositories.UserRepository;
import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserService{

    UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServicesImpl(UserRepository userRepository,ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public UserDto createUser(UserDto user) {
        User user1 =modelMapper.map(user,User.class);
//                dtoToUser(user);

        user1=userRepository.save(user1);

       return userToDto(user1);
    }
    public User dtoToUser(UserDto user){
        User user1 =new User();
        user1.setId(user.getId());
        user1.setName(user.getName());
        user1.setAbout(user.getAbout());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        return user1;
    }
    public static UserDto userToDto(User user){
        UserDto user1 =new UserDto();
        user1.setId(user.getId());
        user1.setName(user.getName());
        user1.setAbout(user.getAbout());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        return user1;
    }
    @Override
    public UserDto updateUser(UserDto user, Integer id) throws ResourceNotFoundException {

        User user1= userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));

        user1.setName(user.getName());
        user1.setAbout(user.getAbout());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        User updatedUser = userRepository.save(user1);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer id) throws ResourceNotFoundException {

        User user1= userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));

        return userToDto(user1);
    }

    @Override
    public void deleteUser(Integer id) throws ResourceNotFoundException {
        User user1= userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
        userRepository.delete(user1);

    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users= userRepository.findAll();
        List<UserDto> userDtos=users.stream().map(user->userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }
}
