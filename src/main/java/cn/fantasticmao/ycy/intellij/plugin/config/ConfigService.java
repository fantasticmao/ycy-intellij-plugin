package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;

/**
 * 配置 Service
 *
 * @author maomao
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-14
 */
public interface ConfigService extends PersistentStateComponent<ConfigState> {

    static ConfigService getInstance() {
        return ServiceManager.getService(ConfigService.class);
    }

    /**
     * 获取插件配置
     *
     * <p>当用户是第一次开启插件时，则使用默认配置对象 {@code new ConfigState()}，
     * 否则是用从 {@link ConfigServiceImpl} 指定的 {@code ycyReminder.xml} 配置文件中解析生成的配置对象</p>
     *
     * @return {@link ConfigState}
     */
    @NotNull
    ConfigState getState();

    /**
     * 修改插件配置
     *
     * <p>调用此方法，会将配置数据持久化至 {@link ConfigServiceImpl} 指定的 {@code ycyReminder.xml} 配置文件</p>
     *
     * @param state 将被保存的新配置对象
     */
    void setState(@NotNull ConfigState state);
}