package toolbox.hibernate.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class CapitalizePhysicalNamingStrategy implements PhysicalNamingStrategy, Serializable {
    /**
     * Singleton access
     */
    public static final CapitalizePhysicalNamingStrategy INSTANCE = new CapitalizePhysicalNamingStrategy();

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return capitalize(name);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return capitalize(name);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return capitalize(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return capitalize(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return capitalize(name);
    }

    private Identifier capitalize(Identifier name) {
        if (name == null)
            return null;
        if (name.isQuoted())
            return name;
        String text = StringUtils.capitalize(name.getText());
        return Identifier.toIdentifier(text);
    }
}
