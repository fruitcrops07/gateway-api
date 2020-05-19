package com.fruitcrops07.gateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitcrops07.gateway.models.Gateway;

public interface GatewayRepository extends JpaRepository<Gateway, String> {

}
