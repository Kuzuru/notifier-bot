package urfu.core.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigInitializer {
  public static void load() {
    try {
      Dotenv.configure().systemProperties().load();
    } catch (DotenvException e) {
      log.atError().log("Ошибка загрузки .env файла");
      log.atError().log("ERRMSG: " + e.getMessage());
    }
  }
}
