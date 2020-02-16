package com.netcracker.db.service;

import com.netcracker.db.entity.Review;
import com.netcracker.db.entity.Route;
import com.netcracker.db.entity.User;
import com.netcracker.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public User createOrUpdateUser(User user) {
        return usersRepository.save(user);
    }

    public User findUserByID(String  userID) {
        return usersRepository.findUsersByUserID(userID);
    }

    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    public void deleteUserByID(String userID) { usersRepository.deleteById(userID);
    }

    public void addReviewToUser(String userID, Review review) {
        User user = findUserByID(userID);

        user.getReviews().add(review);

        createOrUpdateUser(user);
    }

    public void addRouteToUser(String userID, Route route) {
        User user = findUserByID(userID);

        user.getRoutes().add(route);

        createOrUpdateUser(user);
    }
}
