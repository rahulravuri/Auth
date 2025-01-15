package com.example.BookMyShow.Auth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BookMyShow.Auth.Models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {

    Optional<UserModel> findById(Integer id);
    
    Optional<UserModel> findByEmailid(String Email);
}