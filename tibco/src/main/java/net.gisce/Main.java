package net.gisce;
import org.apache.logging.log4j.Logger;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

import net.gisce.MyLogger;

public class Main {
    private static Dotenv dotenv = Dotenv.configure().load();
    public static void main(String[] args) {
        String queueName = dotenv.get("TIBCO_QUEUE");
        Logger logger = MyLogger.getLogger(queueName);
        logger.debug("....");

    }
}