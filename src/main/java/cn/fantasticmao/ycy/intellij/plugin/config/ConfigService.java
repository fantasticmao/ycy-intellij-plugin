package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;

/**
 * 配置参数持久化服务
 *
 * <p>依赖于 Intellij Platform 的 {@link PersistentStateComponent} 组件实现</p>
 *
 * @author fantasticmao
 * @version 1.2
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-14
 */
public interface ConfigService extends PersistentStateComponent<ConfigState> {

    /**
     * 由 Intellij Platform 保证的单例模式
     */
    static ConfigService getInstance() {
        return ApplicationManager.getApplication().getService(ConfigService.class);
    }

    /**
     * 获取插件配置
     *
     * @return {@link ConfigState}
     */
    @NotNull
    ConfigState getState();

    /**
     * 修改插件配置
     *
     * @param state 将被保存的新配置对象
     */
    void setState(@NotNull ConfigState state);
}
