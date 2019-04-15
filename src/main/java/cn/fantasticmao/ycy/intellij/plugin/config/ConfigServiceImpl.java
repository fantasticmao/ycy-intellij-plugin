package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

/**
 * {@inheritDoc}
 *
 * <p>{@link ConfigService} 实现类</p>
 *
 * @author maomao
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-14
 */
@State(name = "ycyReminder", storages = @Storage("ycyReminder.xml"))
public class ConfigServiceImpl implements ConfigService {
    private ConfigState configState;

    public ConfigServiceImpl() {
        this.configState = new ConfigState();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ConfigState getState() {
        return this.configState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(@NotNull ConfigState state) {
        this.configState = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadState(@NotNull ConfigState state) {
        XmlSerializerUtil.copyBean(state, this.configState);
    }
}
