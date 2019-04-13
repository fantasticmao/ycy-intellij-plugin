package cn.fantasticmao.ycy.intellij.plugin.ui.config;

import cn.fantasticmao.ycy.intellij.plugin.common.Config;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 插件设置页面
 *
 * @author maomao
 * @see <a href="http://corochann.com/intellij-plugin-development-introduction-applicationconfigurable-projectconfigurable-873.html">一篇很不错的关于开发 Intellij Plugin 配置页面的博客</a>
 * @since 2019-04-12
 */
public class PluginSettingPage implements SearchableConfigurable {
    private JPanel panel;

    @NotNull
    @Override
    public String getId() {
        return Config.PLUGIN_ID;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return Config.PLUGIN_NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        // called when "show help content" button is pressed
        System.out.println("getHelpTopic");
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.panel == null) {
            panel = new PluginSettingForm().getPluginSettingPanel();
        }
        return this.panel;
    }

    @Override
    public boolean isModified() {
        // enable/disable "apply" button in the Setting dialog
        return this.panel != null;
    }

    @Override
    public void apply() throws ConfigurationException {
        // called when "apply" or "ok" button is pressed
        if (this.panel != null) {
            System.out.println("apply");
        }
    }

    @Override
    public void reset() {
        // called when "apply" or "ok" button is pressed
        System.out.println("reset");
    }

    @Override
    public void disposeUIResources() {
        this.panel = null;
    }
}
