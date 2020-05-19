package com.fruitcrops07.gateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitcrops07.gateway.models.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

}
