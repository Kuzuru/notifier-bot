package urfu.core.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigInitializer {
    public static void load(){
        Dotenv
                .configure()
                .systemProperties()
                .load();
    }
}
