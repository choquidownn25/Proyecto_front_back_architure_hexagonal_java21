package org.example.ireposotory;


import org.exemple.data.User;

public interface UsersRepository {
    public User createUser(User user);
    public User findByEmail(String email);
    public User findById(Integer id);

}
