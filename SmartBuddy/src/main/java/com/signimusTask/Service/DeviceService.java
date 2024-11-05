package com.signimusTask.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.signimusTask.Repository.DeviceRepository;
import com.signimusTask.entity.Device;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    // Retrieve all devices
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // Retrieve a specific device by its ID
    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    // Add a new device
    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    // Update an existing device's status
    public Device updateDeviceStatus(Long id, String status) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
        device.setStatus(status);
        return deviceRepository.save(device);
    }

    // Delete a device by its ID
    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}
