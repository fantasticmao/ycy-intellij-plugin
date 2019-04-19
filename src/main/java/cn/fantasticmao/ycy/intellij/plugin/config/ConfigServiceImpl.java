package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

/**
 * {@link ConfigService} 实现类
 *
 * @author maomao
 * @version 1.2
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
     *
     * <p>若用户是第一次开启插件时，则使用默认配置对象 {@code new ConfigState()}，否则使用从 {@code ycyReminder.xml} 配置文件中解析生成的配置对象</p>
     */
    @NotNull
    @Override
    public ConfigState getState() {
        return this.configState;
    }

    /**
     * {@inheritDoc}
     *
     * <p>调用此方法，会将配置数据持久化至 {@code ycyReminder.xml} 配置文件中</p>
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
