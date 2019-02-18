package com.jdp.irs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jdp.irs.entity.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, String> {}
