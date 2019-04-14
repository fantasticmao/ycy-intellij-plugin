package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
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
    private PluginSettingForm form;
    private ConfigService configService = ServiceManager.getService(ConfigService.class);

    @NotNull
    @Override
    public String getId() {
        return GlobalConfig.PLUGIN_ID;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return GlobalConfig.PLUGIN_NAME;
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
        if (this.form == null) {
            this.form = new PluginSettingForm();
            ConfigState configState = configService.getState();
            if (configState == null) {
                reset();
            } else {
                this.form.setRemindTypeOption(configState.getRemindType());
                this.form.setRemindImagePath(configState.getRemindImagePath());
                this.form.setPeriodMinutes(configState.getPeriodMinutes());
                this.form.setNotifyTitle(configState.getNotifyTitle());
                this.form.setNotifyContent(configState.getNotifyContent());
                this.form.setNotifyAction(configState.getNotifyAction());
            }
        }
        return this.form.getPluginSettingPanel();
    }

    @Override
    public boolean isModified() {
        // enable/disable "apply" button in the Setting dialog
        return this.form != null;
    }

    @Override
    public void apply() throws ConfigurationException {
        // called when "apply" or "ok" button is pressed
        if (this.form != null) {
            System.out.println("apply");
            PropertiesComponent.getInstance().setValue("", "");
        }
    }

    @Override
    public void reset() {
        // called when "reset" button is pressed
        if (this.form != null) {
            this.form.setRemindTypeOption(DefaultConfig.REMIND_TYPE);
            this.form.setRemindImagePath(DefaultConfig.REMIND_IMAGE_PATH);
            this.form.setPeriodMinutes(DefaultConfig.PERIOD_MINUTES);
            this.form.setNotifyTitle(DefaultConfig.NOTIFY_TITLE);
            this.form.setNotifyContent(DefaultConfig.NOTIFY_CONTENT);
            this.form.setNotifyAction(DefaultConfig.NOTIFY_ACTION);
        }
    }

    @Override
    public void disposeUIResources() {
        this.form = null;
    }
}
