package pl.potato.hahelper.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.potato.hahelper.dto.PlantDto;

/**
 * REST controller responsible for generating boolean input templates for plants.
 * <p>
 * This controller exposes an endpoint that, given a {@link PlantDto}, returns
 * a YAML-like template containing boolean states for the plant, such as whether
 * it is watered or currently in the home.
 * </p>
 */
@RestController
@RequestMapping("/api/plants/booleanInputController")
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
    @PostMapping
    public String createBooleanInput(@RequestBody final PlantDto plantDto){
        final String template = """
                #--------------%1$s--------------
                %1$s_info:
                    name: "%1$s"
                    icon: "mdi:flower"
                
                %1$s_is_watered:
                    name: "%1$s - watered"
                    icon: "mdi:water"
                
                %1$s_is_in_home:
                    name: "%1$s - home stored"
                    icon: "mdi:home"
                """;

        return String.format(template, plantDto.getPlantName());
    }

}
