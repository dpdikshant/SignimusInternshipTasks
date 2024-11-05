package com.signimusTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.signimusTask.Service.DeviceService;
import com.signimusTask.config.MqttGateway;
import com.signimusTask.entity.Device;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    
    private final MqttGateway mqttGateway;
    
    public DeviceController(MqttGateway mqttGateway) {
		super();
		this.mqttGateway = mqttGateway;
	}

	// Get all devices
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    // Get a specific device by ID
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> device = deviceService.getDeviceById(id);
        return device.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new device
    @PostMapping
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        Device newDevice = deviceService.addDevice(device);
        return new ResponseEntity<>(newDevice, HttpStatus.CREATED);
    }

    // Update the status of a device
    @PutMapping("/{id}/status")
    public ResponseEntity<Device> updateDeviceStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Device updatedDevice = deviceService.updateDeviceStatus(id, status);
            return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a device by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/light/{state}")
    public String controlLight(@PathVariable String state) {
        mqttGateway.sendToMqtt("light:" + state, "smarthome/devices/light");
        return "Light turned " + state;
    }

    @PostMapping("/thermostat/{temperature}")
    public String setThermostat(@PathVariable int temperature) {
        mqttGateway.sendToMqtt("thermostat:" + temperature, "smarthome/devices/thermostat");
        return "Thermostat set to " + temperature + "°C";
    }

    @PostMapping("/door/{state}")
    public String controlDoor(@PathVariable String state) {
        mqttGateway.sendToMqtt("door:" + state, "smarthome/devices/door");
        return "Door is " + state;
    }
}