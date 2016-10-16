package railroad.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan({ "railroad" })
//@Import({ AppSecurityConfig.class })
public class WebConfig {

    @Bean
    public ResourceBundleViewResolver resBundleViewResolver() {
        ResourceBundleViewResolver resBundleViewResolver = new ResourceBundleViewResolver();
        resBundleViewResolver.setBasename("views");
        resBundleViewResolver.setOrder(1);
        return resBundleViewResolver;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(2);
        return viewResolver;
    }
}