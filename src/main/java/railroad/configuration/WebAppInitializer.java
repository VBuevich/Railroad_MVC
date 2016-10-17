package railroad.configuration;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Base class for WebApplicationInitializer implementations that register a DispatcherServlet configured with annotated classes,
 * e.g. Spring's @Configuration classes.
 * Concrete implementations are required to implement getRootConfigClasses(), getServletConfigClasses(), as well as
 * AbstractDispatcherServletInitializer.getServletMappings(). Further template and customization methods are provided
 * by AbstractDispatcherServletInitializer.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {WebConfig.class}; // We dont need any special servlet config yet.
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
