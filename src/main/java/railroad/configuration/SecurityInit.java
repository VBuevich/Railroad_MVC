package railroad.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Registers the DelegatingFilterProxy to use the springSecurityFilterChain before any other registered Filter.
 * When used with AbstractSecurityWebApplicationInitializer(Class...), it will also register a ContextLoaderListener.
 * When used with AbstractSecurityWebApplicationInitializer(), this class is typically used in addition to a subclass
 * of AbstractContextLoaderInitializer.
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}