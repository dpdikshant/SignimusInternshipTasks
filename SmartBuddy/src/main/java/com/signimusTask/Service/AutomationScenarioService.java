package com.signimusTask.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.signimusTask.Repository.AutomationScenariosRepository;
import com.signimusTask.Repository.DeviceRepository;
import com.signimusTask.entity.AutomationScenarioEntity;
import com.signimusTask.entity.Device;

@Service
public class AutomationScenarioService {

	@Autowired
	private AutomationScenariosRepository scenarioRepository;
	@Autowired
	private DeviceRepository deviceRepository;
	
	public AutomationScenarioEntity createScenario(AutomationScenarioEntity scenario)
	{
		return scenarioRepository.save(scenario);
	}
	
	public List<AutomationScenarioEntity> getAllScenarios()
	{
		return scenarioRepository.findAll();
	}
	
	public Optional<AutomationScenarioEntity> getScenarioById(Long Id)
	{
		return scenarioRepository.findById(Id);
	}
	
	public AutomationScenarioEntity updateScenario(Long Id, AutomationScenarioEntity updatedScenario)
	{
		AutomationScenarioEntity scenario = scenarioRepository.findById(Id).orElseThrow(()-> new RuntimeException("Scenario not found"));
		scenario.setName(updatedScenario.getName());
		scenario.setDescription(updatedScenario.getDescription());
		scenario.setDeviceIds(updatedScenario.getDeviceIds());
		scenario.setActions(updatedScenario.getActions());
		return scenarioRepository.save(scenario);
	}
	public void deleteScenario(Long id)
	{
		scenarioRepository.deleteById(id);
	}
	
	public void executeScenario(Long id)
	{
	   AutomationScenarioEntity scenario = scenarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Scenario not found"));
	   List<Long> deviceIds = scenario.getDeviceIds();
	   List<String> actions =scenario.getActions();
	   
	   for(int i=0; i<deviceIds.size();i++)
	   {
		   Long deviceId = deviceIds.get(i);
		   String action = actions.get(i);
		   
		   Device device = deviceRepository.findById(deviceId).orElseThrow(()-> new RuntimeException("Device not found"));
		    switch(action.toUpperCase())
		    {
		    case "TURN_ON": device.setStatus("ON");
		    break;
		    case "TURN_OFF": device.setStatus("OFF");
		    break;
		    default: throw new IllegalArgumentException("Invalid action: "+ action);
		    }
		    
		    deviceRepository.save(device);
	   }
	}
}
