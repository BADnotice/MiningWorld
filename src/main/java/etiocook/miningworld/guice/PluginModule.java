package etiocook.miningworld.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import etiocook.miningworld.MiningWorld;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data(staticConstructor = "of")
public class PluginModule extends AbstractModule {

    private final MiningWorld miningWorld;

    @Override
    protected void configure() {
        bind(MiningWorld.class).toInstance(miningWorld);
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

}
