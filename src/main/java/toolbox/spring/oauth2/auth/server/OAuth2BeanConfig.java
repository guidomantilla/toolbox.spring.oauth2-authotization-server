package toolbox.spring.oauth2.auth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import toolbox.spring.oauth2.auth.server.security.oauth2.CustomOauth2RequestFactory;
import toolbox.spring.oauth2.auth.server.security.oauth2.CustomTokenEnhancer;
import toolbox.spring.oauth2.jdbc.uppercase.JdbcUpperCaseClientDetailsServiceBuilder;

import javax.sql.DataSource;

@Configuration
public class OAuth2BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
     * Requerido SOLO porque la BD del ejemplo esta en mayusculas. Spring OAUTH2 solo soporta schemas en minusculas.
     */
    @Bean
    public JdbcUpperCaseClientDetailsServiceBuilder jdbcUpperCaseClientDetailsServiceBuilder(DataSource dataSource) throws Exception {
        return new JdbcUpperCaseClientDetailsServiceBuilder();
    }

    @Autowired
    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory(ClientDetailsService clientDetailsService) {
        CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
        requestFactory.setCheckUserScopes(true);
        return requestFactory;
    }

    @Autowired
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomTokenEnhancer();
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "Raven123qweasd+".toCharArray()).getKeyPair("jwt"));
        return converter;
    }

    @Autowired
    @Bean
    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager, OAuth2RequestFactory requestFactory) {
        return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory);
    }
}
