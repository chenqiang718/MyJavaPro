package cq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        try {
            new ClassPathXmlApplicationContext("classpath*:spring/*.xml").registerShutdownHook();
            Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> logger.error("Thread:" + thread.getName(), throwable));
        } catch (Exception e) {
            logger.error("System start failed due to {}", e.toString());
            e.printStackTrace();
        }
    }
}
