package io.bootique.cayenne;

import com.google.inject.Module;
import io.bootique.jdbc.JdbcModule;
import io.bootique.test.junit.BQTestFactory;
import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SQLSelect;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CayenneModuleIT {

    @Rule
    public BQTestFactory testFactory = new BQTestFactory();

    @Test
    public void testFullConfig() {

        ServerRuntime runtime = testFactory.newRuntime()
                .configurator(bootique -> bootique.modules(JdbcModule.class, CayenneModule.class))
                .build("--config=classpath:fullconfig.yml")
                .getRuntime()
                .getInstance(ServerRuntime.class);

        DataDomain domain = runtime.getDataDomain();
        assertNotNull(domain.getEntityResolver().getDbEntity("db_entity2"));

        // trigger a DB op
        SQLSelect.dataRowQuery("SELECT * FROM db_entity2").select(runtime.newContext());
    }

    @Test
    public void testNoConfig() {

        ServerRuntime runtime = testFactory.newRuntime()
                .configurator(bootique -> bootique.modules(JdbcModule.class, CayenneModule.class))
                .build("--config=classpath:noconfig.yml")
                .getRuntime()
                .getInstance(ServerRuntime.class);

        DataDomain domain = runtime.getDataDomain();
        assertTrue(domain.getEntityResolver().getDbEntities().isEmpty());
    }

    @Test
    public void testContributeModules() {

        Module guiceModule = b -> {
            org.apache.cayenne.di.Module cayenneModule = (cb) -> {
                cb.bind(CayenneModuleIT.class).toInstance(this);
            };
            CayenneModule.contribueModules(b).addBinding().toInstance(cayenneModule);
        };

        ServerRuntime runtime = testFactory.newRuntime()
                .configurator(bootique ->
                        bootique.modules(JdbcModule.class, CayenneModule.class).module(guiceModule))
                .build("--config=classpath:fullconfig.yml")
                .getRuntime()
                .getInstance(ServerRuntime.class);

        assertSame(this, runtime.getInjector().getInstance(CayenneModuleIT.class));
    }
}