// Global wizard state
const plantData = {
    displayed_name: "",
    plant_name: "",
    water_frequency_days: "",
    minimum_temperature: "",
    image_url: ""
};

function setPlantData(displayedName, plantName, waterFrequency, minTemperature, imageUrl) {
    plantData.displayed_name = displayedName;
    plantData.plant_name = plantName;
    plantData.water_frequency_days = waterFrequency;
    plantData.minimum_temperature = minTemperature;
    plantData.image_url = imageUrl
}

function loadSecondStep(){
    fetch("/html/plants/plants_wizard_step_2.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/booleanInputTemplate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                plantName: plantData.plant_name
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


function loadThirdStep(){
    fetch("/html/plants/plants_wizard_step_3.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/booleanCustomization", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ plantName: plantData.plant_name, wateringFrequencyDays: plantData.water_frequency_days, minimumTemperature: plantData.minimum_temperature, imageUrl: plantData.image_url})
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


function loadFourthStep(){
    fetch("/html/plants/plants_wizard_step_4.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/datetimeInput", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ plantName: plantData.plant_name})
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

function loadFifthStep(){
    fetch("/html/plants/plants_wizard_step_5.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/temperatureSensor", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ plantName: plantData.plant_name})
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

function loadSixthStep(){
    fetch("/html/plants/plants_wizard_step_6.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/aggregatingSensor", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ plantName: plantData.plant_name})
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

function loadSeventhStep(){
    fetch("/html/plants/plants_wizard_step_7.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/wateringStatusChangeAutomation", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ plantName: plantData.plant_name})
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

function loadEightStep(){
    fetch("/html/plants/plants_wizard_step_8.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/isHomeDashboardButton", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ displayedName: plantData.displayed_name, plantName: plantData.plant_name})
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


function loadNinthStep(){
    fetch("/html/plants/plants_wizard_step_9.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/plantDashboardCard", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ displayedName: plantData.displayed_name, plantName: plantData.plant_name})
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

function loadTenthStep(){
    fetch("/html/plants/plants_wizard_step_10.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector('.app-content').innerHTML = html;
        fetch("/api/plants/setDemandingWateringStatusAutomation", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ plantName: plantData.plant_name })
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