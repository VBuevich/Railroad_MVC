package railroad.configuration;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import railroad.controller.LoginController;
import railroad.persistence.entity.User;
import railroad.service.PassengerService;
import railroad.service.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by vbuevich on 15.10.2016.
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = Logger.getLogger(AuthSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); //get logged in users` mail
        User user = PassengerService.getUserByEmail(email);

        if (user != null) {
            HttpSession session = request.getSession();
            UserBean bean = UserBean.get(session);

            LOGGER.info("User " + user.getName() + " " + user.getSurname() + " has successfully logged in");
            bean.setName(user.getName());
            bean.setSurname(user.getSurname());
            bean.setUserId(user.getUserId());

            request.setAttribute("name", user.getName());
            request.setAttribute("surname", user.getSurname());
        }

        if (response.isCommitted()) {
            LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {

        boolean isUser = false;
        boolean isAdmin = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (isUser) {
            return "/user/schedule";
        } else if (isAdmin) {
            return "/admin/adminMenu";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}