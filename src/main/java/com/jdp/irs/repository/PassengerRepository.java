package com.jdp.irs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jdp.irs.entity.PassengerEntity;

public interface PassengerRepository extends JpaRepository<PassengerEntity, Integer> {}
