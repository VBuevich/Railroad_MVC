import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "railroad.persistence", "railroad.service" })
public class SpringTestSupportWebConfig {
}