package cn.fantasticmao.ycy.intellij.plugin;

import cn.fantasticmao.ycy.intellij.plugin.config.I18nBundle;

/**
 * 全局配置接口
 *
 * @author fantasticmao
 * @since 2019-04-05
 */
public interface GlobalConfig {

    /**
     * 插件标识
     *
     * <p>与 {@code /META-INF/plugin.xml} 中 {@code <id>} 内容一致</p>
     */
    String PLUGIN_ID = "cn.fantasticmao.ycy.intellij.plugin";

    /**
     * 插件名称
     */
    String PLUGIN_NAME = I18nBundle.message(I18nBundle.Key.PLUGIN_NAME);
}
