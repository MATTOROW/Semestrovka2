package ru.itis.semesterwork.second.logger.config.property;

import ch.qos.logback.classic.Level;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.spring.logging")
public class MyLoggingProps {
    private Level level = Level.INFO;
    private boolean consoleEnabled;
    private boolean fileEnabled;
    private String pathToFile;
}
