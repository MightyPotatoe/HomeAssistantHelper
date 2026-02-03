package pl.potato.hahelper.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.potato.hahelper.dto.PlantDto;

import java.util.Locale;

/**
 * REST controller responsible for generating boolean input templates for plants.
 * <p>
 * This controller exposes an endpoint that, given a {@link PlantDto}, returns
 * a YAML-like template containing boolean states for the plant, such as whether
 * it is watered or currently in the home.
 * </p>
 */
@RestController
@RequestMapping("/api/plants")
@NoArgsConstructor
public class PlantBooleanInputController {

    /**
     * Creates a boolean input template for a plant.
     * <p>
     * Given a {@link PlantDto} containing the plant's name, this method returns
     * a formatted string template that can be used to define boolean states
     * for the plant. The template includes:
     * <ul>
     *     <li>Plant info (name and icon)</li>
     *     <li>Watered status (boolean with icon)</li>
     *     <li>Home storage status (boolean with icon)</li>
     * </ul>
     * </p>
     *
     * @param plantDto the DTO containing the plant's data, must not be null
     * @return a formatted string template representing the boolean inputs for the plant
     */
    @PostMapping("booleanInputTemplate")
    public String createBooleanInput(@RequestBody final PlantDto plantDto) {
        final String template = """
                #--------------%2$s--------------
                %1$s_info:
                    name: "%2$s"
                    icon: "mdi:flower"
                
                %1$s_is_watered:
                    name: "%2$s - watered"
                    icon: "mdi:water"
                
                %1$s_is_in_home:
                    name: "%2$s - home stored"
                    icon: "mdi:home"
                """;

        return String.format(template, plantDto.getPlantName().toLowerCase(Locale.getDefault()), plantDto.getPlantName());
    }

    /**
     * Creates a YAML snippet for a plant's boolean input customization.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the input_boolean configuration
     */
    @PostMapping("booleanCustomization")
    public String createBooleanInputCustomization(@RequestBody final PlantDto plantDto) {
        final String template = """
                input_boolean.%s_info:
                    plant_image: "%s"
                    water_frequency_days: %d
                    minimum_temperature: %d
                """;

        return String.format(template, plantDto.getPlantName().toLowerCase(Locale.getDefault()), plantDto.getImageUrl(), plantDto.getWateringFrequencyDays(), plantDto.getMinimumTemperature());
    }

    /**
     * Generates a YAML snippet for a plant's last watered datetime input.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the datetime input configuration
     */
    @PostMapping("datetimeInput")
    public String createDatetimeInput(@RequestBody final PlantDto plantDto) {
        final String template = """
                %1$s_last_watered:
                    name: "%2$s - Last Watered"
                    has_date: true
                    has_time: true
                """;
        return String.format(template, plantDto.getPlantName().toLowerCase(Locale.getDefault()), plantDto.getPlantName());
    }

    /**
     * Generates a YAML snippet for a plant's temperature warning sensor.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the temperature sensor configuration
     */
    @PostMapping("temperatureSensor")
    public String createTemperatureSensor(@RequestBody final PlantDto plantDto) {
        final String template = """
                #-------------------------------------------------------------------------------
                #                 %1$s
                #-------------------------------------------------------------------------------
                
                - name: "%2$s Temperature Warning"
                  state: >
                        {%% set min_plant_temp = state_attr('input_boolean.%3$s_info', 'minimum_temperature') | float(99) %%}
                        {%% set lowest_temp = states('sensor.lowest_temperature_next_24h') | float(30) %%}
                        {%% set avg_temp = states('sensor.average_temperature_next_24h') | float(30) %%}
                        {%% set is_outside = is_state('input_boolean.%3$s_is_in_home', 'off') %%}
                        {{
                          is_outside and (
                            (lowest_temp < (min_plant_temp - 3)) or (avg_temp < min_plant_temp)
                          )
                        }}
                  attributes:
                    lowest_temp_24h: "{{ states('sensor.lowest_temperature_next_24h') }}"
                    avg_temp_24h: "{{ states('sensor.average_temperature_next_24h') }}"
                    required_minimum: "{{ state_attr('input_boolean.%3$s_info', 'minimum_temperature') }}"
                """;
        return String.format(template,
                plantDto.getPlantName().toUpperCase(Locale.getDefault()),
                plantDto.getPlantName(),
                plantDto.getPlantName().toLowerCase(Locale.getDefault()));
    }

    /**
     * Generates a YAML snippet for a plant's aggregating sensor that combines watering and temperature status.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the aggregating sensor configuration
     */
    @PostMapping("aggregatingSensor")
    public String createAggregatingSensor(@RequestBody final PlantDto plantDto){
        final String template = """
                #-------------------------------------------------------------------------------
                #                %1$s
                #-------------------------------------------------------------------------------
                - name: "%2$s"
                  unique_id: %3$s_status
                  state: >
                        {%% set is_temperature_warning_on = is_state('binary_sensor.%3$s_temperature_warning', 'on') %%}
                        {%% set is_not_watered = is_state('input_boolean.%3$s_is_watered', 'off') %%}
                        {{
                            is_not_watered or is_temperature_warning_on
                        }}
                  attributes:
                    plantImage: "{{ state_attr('input_boolean.%3$s_info', 'plant_image') }}"
                    isWatered: "{{ states('input_boolean.%3$s_is_watered') }}"
                    isInHome: "{{ states('input_boolean.%3$s_is_in_home') }}"
                    temperatureWarning: "{{ states('binary_sensor.%3$s_temperature_warning') }}"
                    lastWatered: "{{ states('input_datetime.%3$s_last_watered') }}"
                    lastWateredString: >
                        {%% from "macro.jinja" import time_since %%}
                        {{ time_since(state_attr('binary_sensor.%3$s', 'lastWatered')) }}
                """;
        return String.format(template,
                plantDto.getPlantName().toUpperCase(Locale.getDefault()),
                plantDto.getPlantName(),
                plantDto.getPlantName().toLowerCase(Locale.getDefault()));
    }

    /**
     * Generates a YAML snippet for an automation that updates a plant's last watered time
     * whenever its watering status changes to "on".
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the watering status change automation
     */
    @PostMapping("wateringStatusChangeAutomation")
    public String createWateringStatusChangeAutomation(@RequestBody final PlantDto plantDto){
        final String template = """
                alias: %1$s - on watered switch change
                description: Save the time when %1$s was watered
                triggers:
                  - entity_id:
                      - input_boolean.%2$s_is_watered
                    to: "on"
                    trigger: state
                conditions: []
                actions:
                  - target:
                      entity_id: input_datetime.%2$s_last_watered
                    data:
                      datetime: "{{ now().strftime('%%Y-%%m-%%d %%H:%%M:%%S') }}"
                    action: input_datetime.set_datetime
                mode: single
                """;
        return String.format(template,
                plantDto.getPlantName(),
                plantDto.getPlantName().toLowerCase(Locale.getDefault()));
    }

    /**
     * Generates a YAML snippet for a dashboard button that toggles a plant's "is in home" status.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the dashboard button configuration
     */
    @PostMapping("isHomeDashboardButton")
    public String createIsInHomeDashboardButton(@RequestBody final PlantDto plantDto){
        final String template = """
                features:
                  - type: custom:service-call
                    entries:
                      - type: toggle
                        entity_id: input_boolean.%1$s_is_in_home
                        tap_action:
                          action: toggle
                          target:
                            entity_id: input_boolean.%1$s_is_in_home
                          data: {}
                        unchecked_icon: mdi:cloud
                        checked_icon: mdi:home
                        thumb: md3-switch
                        autofill_entity_id: false
                        haptics: false
                        allow_list: true
                        check_numeric: true
                        styles: |-
                          :host {
                            --switch-checked-button-state-layer: #03A9F4;
                          }
                type: custom:mushroom-template-card
                secondary: |
                  {%% if is_state('input_boolean.%1$s_is_in_home', 'on') %%}
                    W domu
                  {%% else %%}
                    Poza domem
                  {%% endif %%}
                features_position: inline
                entity: input_boolean.%1$s_is_in_home
                primary: %2$s
                color: blue
                grid_options:
                  columns: 12
                  rows: 1
                multiline_secondary: false
                picture: /local/rocket_lettuce.png
                card_mod:
                  style: |
                    ha-card {
                      background: linear-gradient(to right, rgba(0,0,0,0.1), rgba(75,0,130,0.1));
                      color: white;
                      border-radius: 16px;
                      box-shadow: 0 2px 6px rgba(0,0,0,0.2);
                    }
                
                """;
        return String.format(template,
                plantDto.getPlantName().toLowerCase(Locale.getDefault()),
                plantDto.getDisplayedName());
    }

    /**
     * Generates a YAML snippet for a plant dashboard card using a custom button-card.
     * Displays watering status, last watered time, and plant image.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the plant dashboard card configuration
     */
    @PostMapping("plantDashboardCard")
    public String generatePlantDashboardCard(@RequestBody final PlantDto plantDto){
        final String template = """
                type: custom:button-card
                entity: binary_sensor.%1$s
                tap_action:
                  action: call-service
                  service: input_boolean.toggle
                  service_data:
                    entity_id: input_boolean.%1$s_is_watered
                show_icon: false
                show_name: false
                show_state: false
                custom_fields:
                  last_watered: |
                    [[[
                       const lastWateredString = entity.attributes.lastWateredString;
                       return `Ostatnio podlane: ${lastWateredString}`;
                    ]]]
                  button_name: |
                    [[[
                      return `
                        <div style="display: flex; align-items: center;">
                        <img src="${entity.attributes.plantImage}" style="width:38px;height:38px;margin-right:4px;">
                        <span style="color:white;font-size:24px;">%2$s</span>
                        </div>
                      `;
                    ]]]
                  status: |
                    [[[
                      return entity.attributes.isWatered === 'on'
                      ? `
                        <div style="display: flex; align-items: center;">
                          <b> Podlane </b>
                        </div>
                      `
                      :`
                        <div style="display: flex; align-items: center;">
                          <b> Wymaga podlania! </b>
                        </div>
                      `;
                    ]]]
                styles:
                  card:
                    - min-width: 150px
                    - min-height: 100px
                    - border-radius: 16px
                    - font-size: 24px
                    - color: white
                    - background: |
                        [[[
                          return entity.attributes.isWatered === 'on'
                            ? 'linear-gradient(to right, rgba(76, 175, 80, 0.1), rgba(46, 125, 50, 0.3))'
                            : 'linear-gradient(to right, rgba(255, 112, 67, 0.1), rgba(191, 54, 12, 0.3))';
                        ]]]
                  custom_fields:
                    last_watered:
                      - position: absolute
                      - bottom: 8px
                      - right: 8px
                      - left: 8px
                      - font-size: 12px
                      - color: white
                      - text-align: right
                    button_name:
                      - position: absolute
                      - left: 8px
                      - top: 12px
                      - font-size: 14px
                      - color: white
                    status:
                      - position: absolute
                      - left: 51px
                      - top: 50px
                      - font-size: 14px
                      - color: white
                """;
        return String.format(template,
                plantDto.getPlantName().toLowerCase(Locale.getDefault()),
                plantDto.getDisplayedName());
    }

    /**
     * Generates a YAML snippet for an automation that marks a plant as needing watering
     * based on its watering frequency and the last watered timestamp.
     *
     * @param plantDto the plant data transfer object containing plant details
     * @return YAML string representing the demanding watering status automation
     */
    @PostMapping("setDemandingWateringStatusAutomation")
    public String createSetDemandingWateringStatusAutomation(@RequestBody final PlantDto plantDto){
        final String template = """
                alias: %1$s - set demanding watering status
                description: ""
                triggers:
                  - hours: /1
                    trigger: time_pattern
                conditions:
                  - condition: template
                    value_template: >-
                      {%% set watering_frequency = state_attr('input_boolean.%1$s_info',
                      'water_frequency_days') * 24 %%}
                
                      {%% set last = as_timestamp(states.input_datetime.%1$s_last_watered.state) %%}
                
                      {%% set diff = (as_timestamp(now()) - last) / 3600 %%}
                
                      {{ diff > watering_frequency }}
                actions:
                  - target:
                      entity_id: input_boolean.%1$s_is_watered
                    action: input_boolean.turn_off
                    data: {}
                mode: single
                """;
        return String.format(template, plantDto.getPlantName().toLowerCase(Locale.getDefault()));
    }
}
