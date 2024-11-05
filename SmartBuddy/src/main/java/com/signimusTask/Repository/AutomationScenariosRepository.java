package com.signimusTask.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.signimusTask.entity.AutomationScenarioEntity;

@Repository
public interface AutomationScenariosRepository extends JpaRepository<AutomationScenarioEntity, Long> {

}
