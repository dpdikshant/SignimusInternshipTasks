package com.signimusTask.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/google-assistant")
public class VoiceAssistantController {

    @PostMapping("/control")
    public ResponseEntity<Map<String, Object>> handleVoiceCommand(@RequestBody Map<String, Object> requestPayload) {
        Map<String, Object> response = new HashMap<>();

        // Parse the request from Google Assistant
        String intent = (String) ((Map<String, Object>) requestPayload.get("inputs")).get("intent");

        // Handle commands based on intent
        switch (intent) {
            case "action.devices.commands.OnOff":
                handleOnOffCommand(requestPayload);
                response.put("fulfillmentText", "Device has been turned on/off.");
                break;

            // Handle other intents (like temperature control, security, etc.)
            case "action.devices.commands.ThermostatTemperatureSetpoint":
                handleTemperatureCommand(requestPayload);
                response.put("fulfillmentText", "Thermostat temperature set.");
                break;

            default:
                response.put("fulfillmentText", "Sorry, I didn't understand the command.");
                break;
        }

        return ResponseEntity.ok(response);
    }

    private void handleOnOffCommand(Map<String, Object> requestPayload) {
        // Logic to turn a device on/off based on parsed JSON data
        String deviceId = extractDeviceId(requestPayload);
        boolean on = extractOnOffStatus(requestPayload);

        // Update device status in your database or system
        // Example: deviceService.updateDeviceStatus(deviceId, on);
    }

    private void handleTemperatureCommand(Map<String, Object> requestPayload) {
        // Logic to set the thermostat temperature
        String deviceId = extractDeviceId(requestPayload);
        double temperature = extractTemperature(requestPayload);

        // Update thermostat temperature in your system
        // Example: thermostatService.updateTemperature(deviceId, temperature);
    }

    private String extractDeviceId(Map<String, Object> payload) {
        // Extract device ID from the payload
        // Placeholder logic for actual parsing
        return "sampleDeviceId";
    }

    private boolean extractOnOffStatus(Map<String, Object> payload) {
        // Extract on/off command value from the payload
        // Placeholder logic for actual parsing
        return true;
    }

    private double extractTemperature(Map<String, Object> payload) {
        // Extract temperature value from the payload
        // Placeholder logic for actual parsing
        return 21.0;
    }
}
