package cq;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author: 陈强
 * @Date: 2018/8/31 14:25
 * @Version 1.0
 */
@Configuration
@ImportResource("classpath*:spring/*.xml")
public class SpringConfigure {
    public SpringConfigure(){
        String startContent="   _____ ____     _____ _______       _____ _______ \n" +
                "  / ____/ __ \\   / ____|__   __|/\\   |  __ \\__   __|\n" +
                " | |   | |  | | | (___    | |  /  \\  | |__) | | |   \n" +
                " | |   | |  | |  \\___ \\   | | / /\\ \\ |  _  /  | |   \n" +
                " | |___| |__| |  ____) |  | |/ ____ \\| | \\ \\  | |   \n" +
                "  \\_____\\___\\_\\ |_____/   |_/_/    \\_\\_|  \\_\\ |_|   \n" +
                "                                                    \n" +
                "                                                    ";
        System.out.println(startContent);
    }
}
