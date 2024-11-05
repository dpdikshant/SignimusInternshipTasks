package com.signimusTask.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.signimusTask.entity.Device;
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
