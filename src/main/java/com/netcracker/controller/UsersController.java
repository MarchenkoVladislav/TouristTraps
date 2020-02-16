package com.netcracker.controller;

import com.netcracker.db.entity.Review;
import com.netcracker.db.entity.Route;
import com.netcracker.db.entity.User;
import com.netcracker.db.service.UsersService;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping(name ="findAllUsers", value = "")
    public List<User> findAllUsers() {
        return usersService.findAllUsers();
    }

    @GetMapping(name = "findUserByID", value = "/{userID}")
    public User findUserByID(@PathVariable String userID) {
        User user = usersService.findUserByID(userID);
        if (user == null) {
            throw new NotFoundException();
        }
        else return user;
    }

    @DeleteMapping(name ="deleteUserByID",value = "/{userID}")
    public void deleteUserByID(@PathVariable String userID) {
        if (usersService.findUserByID(userID) == null) {
            throw new NotFoundException();
        }
        else usersService.deleteUserByID(userID);
    }

    @PutMapping(name = "addReviewToUser", value = "/{userID}/addReview")
    public void addReviewToUser(@PathVariable String userID, @RequestBody Review review) {
        if (usersService.findUserByID(userID) == null) {
            throw new NotFoundException();
        }
        else usersService.addReviewToUser(userID, review);
    }

    @PutMapping(name ="addRouteToUser",value = "/{userID}/addRoute")
    public void addRouteToUser(@PathVariable String userID, @RequestBody Route route) {
        if (usersService.findUserByID(userID) == null) {
            throw new NotFoundException();
        }
        else usersService.addRouteToUser(userID, route);
    }

}
