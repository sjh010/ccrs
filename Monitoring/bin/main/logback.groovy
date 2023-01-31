import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.ERROR

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.core.ConsoleAppender

appender("console", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "[%d] [%thread] %-5level %logger - %msg%n"
    }
}

def profiles = System.getProperty("spring.profiles.active")

def LOG_DIR = "/programs/log/edoc/ppedoc"

if (profiles == 'prd1') {
    LOG_DIR = "/programs/log/edoc/ppedoc1"
} else if (profiles == 'prd2') {
    LOG_DIR = "/programs/log/edoc/ppedoc2"
}

appender("fileAppender", RollingFileAppender) {
    filter(LevelFilter) {
        level = ERROR
        onMatch = ch.qos.logback.core.spi.FilterReply.DENY
        onMismatch = ch.qos.logback.core.spi.FilterReply.ACCEPT
    }

    file = "${LOG_DIR}/application.log"
    encoder(PatternLayoutEncoder) {
        pattern = "[%d] [%thread] %-5level %logger{36} - %replace(%msg){'[\r\n]', ''}%n"
    }
  
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_DIR}/application/application.%d{yyyy-MM-dd}.log"
        maxHistory = 30
    }
}

appender("errorFileAppender", RollingFileAppender) {
    filter(LevelFilter) {
        level = ERROR
        onMatch = ch.qos.logback.core.spi.FilterReply.ACCEPT
        onMismatch = ch.qos.logback.core.spi.FilterReply.DENY
    }

    file = "${LOG_DIR}/application.err.log"
    encoder(PatternLayoutEncoder) {
        pattern = "[%d] [%thread] %-5level %logger{36} - %replace(%msg){'[\r\n]', ''}%n"
    }
  
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_DIR}/application/application.err.%d{yyyy-MM-dd}.log"
        maxHistory = 30
    }
}

logger("com.mobileleader.edoc.monitoring.db", INFO)
root(INFO, ["console", "fileAppender", "errorFileAppender"])