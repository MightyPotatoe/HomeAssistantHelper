package pl.potato.hahelper.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a plant's basic information.
 * <p>
 * This class is used to transfer plant data between the client and server,
 * particularly when creating templates or managing plant states.
 * </p>
 */
@Data
public class PlantDto {

    /**
     * The display name of the plant (used in dashboards/cards).
     */
    private String displayedName;

    /**
     * The unique name of the plant (used in entity IDs and YAML templates).
     */
    private String plantName;

    /**
     * The frequency, in days, at which the plant should be watered.
     */
    private int wateringFrequencyDays;

    /**
     * The minimum temperature (in degrees Celsius) the plant can tolerate.
     */
    private int minimumTemperature;

    /**
     * URL to the plant's image (used in dashboard cards).
     */
    private String imageUrl;
}
