package com.signimusTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.signimusTask.Service.AutomationScenarioService;
import com.signimusTask.entity.AutomationScenarioEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scenarios")
public class AutomationScenarioController {

    @Autowired
    private AutomationScenarioService scenarioService;

    // Create a new automation scenario
    @PostMapping
    public ResponseEntity<AutomationScenarioEntity> createScenario(@RequestBody AutomationScenarioEntity scenario) {
        AutomationScenarioEntity newScenario = scenarioService.createScenario(scenario);
        return new ResponseEntity<>(newScenario, HttpStatus.CREATED);
    }

    // Get all automation scenarios
    @GetMapping
    public List<AutomationScenarioEntity> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    // Get a specific scenario by ID
    @GetMapping("/{id}")
    public ResponseEntity<AutomationScenarioEntity> getScenarioById(@PathVariable Long id) {
        Optional<AutomationScenarioEntity> scenario = scenarioService.getScenarioById(id);
        return scenario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing scenario
    @PutMapping("/{id}")
    public ResponseEntity<AutomationScenarioEntity> updateScenario(@PathVariable Long id, @RequestBody AutomationScenarioEntity scenario) {
        AutomationScenarioEntity updatedScenario = scenarioService.updateScenario(id, scenario);
        return new ResponseEntity<>(updatedScenario, HttpStatus.OK);
    }

    // Delete a scenario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScenario(@PathVariable Long id) {
        scenarioService.deleteScenario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Execute an automation scenario (trigger device actions)
    @PostMapping("/{id}/execute")
    public ResponseEntity<Void> executeScenario(@PathVariable Long id) {
        scenarioService.executeScenario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}