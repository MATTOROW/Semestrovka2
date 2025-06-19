package ru.itis.semesterwork.second.logger.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.itis.semesterwork.second.logger.aspect.LoggingAspect;
import ru.itis.semesterwork.second.logger.config.property.MyLoggingProps;

@Configuration
@ConditionalOnProperty(prefix = "my.spring.logging", name = "enabled", havingValue = "true")
@EnableAspectJAutoProxy
@EnableConfigurationProperties(MyLoggingProps.class)
@RequiredArgsConstructor
public class LoggerAutoConfig {

    private final MyLoggingProps myLoggingProps;
    private static final String MY_CUSTOM_LOGGER = "MY_CUSTOM_ASPECT_LOGGER";
    private static final String APPENDER_PATTERN = "%d{DEFAULT} %p --- [%thread] %logger : %msg %n";

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger myLogger = context.getLogger(MY_CUSTOM_LOGGER);
        myLogger.setAdditive(false);
        MyLoggingProps props = myLoggingProps;

        myLogger.setLevel(props.getLevel());

        if (props.isConsoleEnabled()) {
            addConsoleAppender(context, myLogger);
        }
        if (props.isFileEnabled()) {
            String pathToFile = props.getPathToFile();
            if (pathToFile == null || pathToFile.isEmpty()) {
                pathToFile = "log/main.log";
            }
            addFileAppender(context, myLogger, pathToFile);
        }
    }

    private void addConsoleAppender(LoggerContext context, Logger rootLogger) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setName("MY_CUSTOM_CONSOLE");
        consoleAppender.setContext(context);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(APPENDER_PATTERN);
        encoder.start();

        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        rootLogger.addAppender(consoleAppender);
    }

    private void addFileAppender(LoggerContext context, Logger rootLogger, String filePath) {
        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setName("MY_CUSTOM_FILE");
        fileAppender.setContext(context);
        fileAppender.setFile(filePath);
        fileAppender.setAppend(true);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(APPENDER_PATTERN);
        encoder.start();

        fileAppender.setEncoder(encoder);
        fileAppender.start();

        rootLogger.addAppender(fileAppender);
    }
}
