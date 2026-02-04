package org.example.service;


import org.example.ireposotory.UsersRepository;
import org.exemple.data.User;


public class UserService {
    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.createUser(user);
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User findById (Integer id){
        return userRepository.findById(id);
    }
}
