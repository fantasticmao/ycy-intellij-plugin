package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link ConfigService} 实现类
 *
 * @author maomao
 * @since 2019-04-14
 */
@State(name = "ycyReminder", storages = @Storage("ycyReminder.xml"))
public class ConfigServiceImpl implements ConfigService {
    private ConfigState configState;

    @Nullable
    @Override
    public ConfigState getState() {
        return configState;
    }

    @Override
    public void loadState(@NotNull ConfigState state) {
        this.configState = state;
    }
}
