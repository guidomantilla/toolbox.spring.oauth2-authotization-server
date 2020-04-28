package toolbox.spring.oauth2.jdbc.uppercase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

public class JdbcUpperCaseClientDetailsServiceBuilder extends JdbcClientDetailsServiceBuilder {

    private Set<ClientDetails> clientDetails = new HashSet<>();

    private DataSource dataSource;

    private PasswordEncoder passwordEncoder;

    @Override
    public JdbcClientDetailsServiceBuilder jdbc() throws Exception {
        return this;
    }

    @Override
    public JdbcClientDetailsServiceBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return super.dataSource(dataSource);
    }

    @Override
    public JdbcClientDetailsServiceBuilder passwordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return super.passwordEncoder(passwordEncoder);
    }

    @Override
    protected void addClient(String clientId, ClientDetails value) {
        clientDetails.add(value);
    }

    @Override
    protected ClientDetailsService performBuild() {
        Assert.state(dataSource != null, "You need to provide a DataSource");
        JdbcClientDetailsService clientDetailsService = new JdbcUpperCaseClientDetailsService(dataSource);
        if (passwordEncoder != null) {
            // This is used to encode secrets as they are added to the database (if it isn't set then the user has top
            // pass in pre-encoded secrets)
            clientDetailsService.setPasswordEncoder(passwordEncoder);
        }
        for (ClientDetails client : clientDetails) {
            clientDetailsService.addClientDetails(client);
        }
        return clientDetailsService;
    }
}
