package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("UPDATE User u " +
            "SET u.point = u.point + :point " +
            "WHERE u.id = :userId")
    void updateUserPoint(Long userId, Long point);
}
