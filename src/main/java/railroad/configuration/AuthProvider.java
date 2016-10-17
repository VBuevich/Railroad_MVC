package railroad.configuration;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import railroad.persistence.dao.DaoFactory;

import java.util.ArrayList;

/**
 * Custom Authentication Provider
 *
 * @author vbuevich
 */

@Component
public class AuthProvider extends
        AbstractUserDetailsAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {


        Session session = DaoFactory.getSessionFactory().openSession();
        railroad.persistence.entity.UserDetails user = null;
        UserDetails userDetails = null;

        String email = authentication.getPrincipal().toString();
        // Due to the fact that all passwords are encrypted in database,
        // firstly we getting an encrypted value of string field of password
        String sha1password = DigestUtils.sha1Hex(authentication.getCredentials().toString());

        try {
            Query q = session.createQuery("FROM UserDetails WHERE email = :em AND password = :pa");
            q.setParameter("em", email);
            q.setParameter("pa", sha1password);

            user = (railroad.persistence.entity.UserDetails) q.uniqueResult(); // UserDetails has unique email in system so it can be received just unique result
        }
        catch (Exception e) {
            throw new BadCredentialsException("internal reasons. Please try again.");
        }
        finally {
            session.close(); // we always closing Hibernate Session
        }
        if (user == null) {
            throw new BadCredentialsException("bad credentials. Please try again.");
        }
        else {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getUserRole());
            ArrayList<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
            grantedAuth.add(grantedAuthority);
            userDetails = new org.springframework.security.core.userdetails.User(username, sha1password, grantedAuth);
        }
        return userDetails;
    }
}
