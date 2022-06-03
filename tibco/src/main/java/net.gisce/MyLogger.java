package net.gisce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MyLogger{

    public static Logger  getLogger(String queueName) {
        Logger logger = LogManager.getLogger(queueName);
        return logger;
    }
}