package cn.fantasticmao.ycy.intellij.plugin.common;

/**
 * 全局配置接口
 *
 * @author maomao
 * @since 2019-04-05
 */
public interface Config {

    /**
     * 插件标识
     *
     * <p>与 {@code /META-INF/plugin.xml} 中 {@code <id>} 内容一致</p>
     */
    String PLUGIN_ID = "cn.fantasticmao.ycy.intellij.plugin";

    /**
     * 插件名称
     */
    String PLUGIN_NAME = "超越鼓励师";

    /**
     * 插件打包后的 jar 包名称
     */
    String JAR_NAME = "ycy-intellij-plugin.jar";
}
