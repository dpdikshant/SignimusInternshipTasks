function toggleDeviceStatus(deviceId, status) {
    fetch(`/api/devices/${deviceId}/status?status=${status}`, {
        method: 'PUT'
    }).then(response => {
        if (response.ok) {
            alert("Device status updated!");
            location.reload();
        }
    });
}
// Mock data for initial scenarios
const scenarios = [
    { id: 1, name: "Good Morning", actions: ["Turn on lights", "Set thermostat to 22°C"] },
    { id: 2, name: "Leaving Home", actions: ["Turn off lights", "Activate security system"] }
];

// Display scenarios on load
document.addEventListener("DOMContentLoaded", () => {
    displayScenarios();
});

// Function to display all scenarios
function displayScenarios() {
    const scenarioList = document.getElementById("scenarioList");
    scenarioList.innerHTML = "";
    
    scenarios.forEach(scenario => {
        const scenarioDiv = document.createElement("div");
        scenarioDiv.className = "scenario";
        scenarioDiv.innerHTML = `
            <h4>${scenario.name}</h4>
            <p>Actions:</p>
            <ul>
                ${scenario.actions.map(action => `<li>${action}</li>`).join('')}
            </ul>
            <button onclick="activateScenario(${scenario.id})">Activate</button>
            <button onclick="deleteScenario(${scenario.id})">Delete</button>
        `;
        scenarioList.appendChild(scenarioDiv);
    });
}

// Function to add a new scenario
function addScenario() {
    const name = document.getElementById("scenarioName").value;
    const device = document.getElementById("deviceSelect").value;
    const actionDetails = document.getElementById("actionDetails").value;
    
    if (!name || !actionDetails) {
        alert("Please fill in all fields");
        return;
    }

    const newScenario = {
        id: Date.now(),
        name,
        actions: [`${device} - ${actionDetails}`]
    };

    scenarios.push(newScenario);
    displayScenarios();

    // Clear form fields
    document.getElementById("addScenarioForm").reset();
}

// Function to activate a scenario
function activateScenario(id) {
    const scenario = scenarios.find(s => s.id === id);
    alert(`Activating scenario: ${scenario.name}`);

    // API request to activate the scenario on the backend
    fetch(`/api/scenarios/${id}/activate`, {
        method: "POST"
    }).then(response => {
        if (response.ok) {
            alert("Scenario activated successfully!");
        } else {
            alert("Failed to activate scenario.");
        }
    });
}

// Function to delete a scenario
function deleteScenario(id) {
    const index = scenarios.findIndex(s => s.id === id);
    if (index !== -1) {
        scenarios.splice(index, 1);
        displayScenarios();

        // API request to delete the scenario from the backend
        fetch(`/api/scenarios/${id}`, {
            method: "DELETE"
        }).then(response => {
            if (response.ok) {
                alert("Scenario deleted successfully!");
            } else {
                alert("Failed to delete scenario.");
            }
        });
    }
}
