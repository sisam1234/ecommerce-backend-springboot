package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
