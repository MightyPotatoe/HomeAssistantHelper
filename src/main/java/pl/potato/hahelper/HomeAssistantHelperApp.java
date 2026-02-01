package pl.potato.hahelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spinner Wheel Spring Boot application.
 * <p>
 * This class bootstraps the application using {@link SpringApplication}.
 */
@SpringBootApplication
public final class HomeAssistantHelperApp {

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * This class is intended to be used only as an application entry point.
     */
    private HomeAssistantHelperApp() {
    }

    /**
     * Main method that launches the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    /* default */ static void main(final String[] args) {
        SpringApplication.run(HomeAssistantHelperApp.class, args);
    }
}
