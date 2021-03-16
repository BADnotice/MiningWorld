package badnotice.miningworld.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import badnotice.miningworld.MiningWorldPlugin;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data(staticConstructor = "of")
public class PluginModule extends AbstractModule {

    private final MiningWorldPlugin miningWorldPlugin;

    @Override
    protected void configure() {
        bind(MiningWorldPlugin.class).toInstance(miningWorldPlugin);
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

}
