// Global wizard state
const plantData = {
    plantName: "",
    water_frequency_days: "",
    minimum_temperature: ""
};

function setPlantData(plantName, waterFrequency, minTemperature) {
    plantData.plantName = plantName;
    plantData.water_frequency_days = waterFrequency;
    plantData.minimum_temperature = minTemperature;
}

function loadSecondStep(){
    fetch("/html/plants/plants_wizard_step_2.html")
        .then(response => response.text())
        .then(html => {
            document.querySelector('.app-content').innerHTML = html;
            fetch("/api/plants/booleanInputController", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    plantName: plantData.plantName
                })
            })
            .then(response => response.text())
            .then(code => {
                // Insert the generated code into the element
                const codeElement = document.getElementById('generatedCode');
                if (codeElement) {
                    codeElement.textContent = code;
                }
            });
        });
}