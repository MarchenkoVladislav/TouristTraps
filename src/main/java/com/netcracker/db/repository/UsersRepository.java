package com.netcracker.db.repository;

import com.netcracker.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {

    User findUsersByUserID(String userID);

}
