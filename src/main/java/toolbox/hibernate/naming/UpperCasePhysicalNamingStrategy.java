package toolbox.hibernate.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;

public class UpperCasePhysicalNamingStrategy implements PhysicalNamingStrategy, Serializable {
    /**
     * Singleton access
     */
    public static final UpperCasePhysicalNamingStrategy INSTANCE = new UpperCasePhysicalNamingStrategy();

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return toUpperCase(name);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return toUpperCase(name);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return toUpperCase(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return toUpperCase(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return toUpperCase(name);
    }

    private Identifier toUpperCase(Identifier name) {
        if (name == null)
            return null;
        if (name.isQuoted())
            return name;
        String text = name.getText().toUpperCase();
        return Identifier.toIdentifier(text);
    }
}
