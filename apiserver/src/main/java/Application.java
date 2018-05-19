import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Varun Kakuste on 5/19/18.
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.api")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
