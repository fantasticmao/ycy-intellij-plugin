package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import cn.fantasticmao.ycy.intellij.plugin.remind.RemindTask;
import com.intellij.openapi.diagnostic.Logger;
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
 * @version 1.2
 * @see <a href="http://corochann.com/intellij-plugin-development-introduction-applicationconfigurable-projectconfigurable-873.html">一篇很不错的关于开发 Intellij Plugin 配置页面的博客</a>
 * @since 2019-04-12
 */
public class PluginSettingPage implements SearchableConfigurable {
    private static final Logger LOG = Logger.getInstance(PluginSettingPage.class);
    /**
     * 插件设置页面的表单对象
     */
    private PluginSettingForm form;

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getId() {
        return GlobalConfig.PLUGIN_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return GlobalConfig.PLUGIN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    /**
     * IDEA 初始化设置页面时，会调用此方法
     *
     * @return 由 {@code UI Designer} 生成的 {@link PluginSettingForm} 页面
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.form == null) {
            this.form = new PluginSettingForm();
        }
        return this.form.getPluginSettingPanel();
    }

    /**
     * IDEA 初始化设置页面时，判断 "Apply" 按钮是否为可用
     *
     * @return true 是；false 否
     */
    @Override
    public boolean isModified() {
        return this.form != null;
    }

    /**
     * 用户点击 "Apply" 按钮或 "OK" 按钮之后，会调用此方法
     */
    @Override
    public void apply() throws ConfigurationException {
        if (this.form == null) return;

        ConfigState configState = ConfigService.getInstance().getState();
        configState.setRemindType(this.form.getRemindTypeOption());
        configState.setRemindImages(this.form.getImageUrlList());
        configState.setPeriodMinutes(this.form.getPeriodMinutes());
        configState.setNotifyTitle(this.form.getNotifyTitle());
        configState.setNotifyContent(this.form.getNotifyContent());
        configState.setNotifyAction(this.form.getNotifyAction());
        ConfigService.getInstance().setState(configState);
        LOG.info("apply and save user setting");

        RemindTask.restart();
        LOG.info("restart scheduled remind task");
    }

    /**
     * IDEA 初始化设置页面或者用户点击 "Reset" 按钮之后，会调用此方法
     */
    @Override
    public void reset() {
        if (form == null) return;

        ConfigState configState = ConfigService.getInstance().getState();
        this.form.setRemindTypeOption(configState.getRemindType());
        this.form.setImageUrlList(configState.getRemindImages());
        this.form.setPeriodMinutes(configState.getPeriodMinutes());
        this.form.setNotifyTitle(configState.getNotifyTitle());
        this.form.setNotifyContent(configState.getNotifyContent());
        this.form.setNotifyAction(configState.getNotifyAction());
        LOG.info("reset user setting");
    }

    /**
     * IDEA 销毁设置页面后，会调用此方法
     */
    @Override
    public void disposeUIResources() {
        this.form = null;
    }
}
