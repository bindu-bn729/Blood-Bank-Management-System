package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodStockRepository extends JpaRepository<BloodStock, String> {

    BloodStock findByBloodGroup(String bloodGroup);

}