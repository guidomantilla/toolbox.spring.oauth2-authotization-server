package toolbox.spring.oauth2.jdbc.uppercase;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class JdbcUpperCaseClientDetailsService extends JdbcClientDetailsService {

    private final String selectClientDetailsSql ="select client_id, client_secret, resource_ids, scope, authorized_grant_types, " +
            "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove " +
            "from oauth_client_details where client_id = ?";

    public JdbcUpperCaseClientDetailsService(DataSource dataSource) {
        super(dataSource);
        super.setSelectClientDetailsSql(selectClientDetailsSql.toUpperCase());
    }
}
