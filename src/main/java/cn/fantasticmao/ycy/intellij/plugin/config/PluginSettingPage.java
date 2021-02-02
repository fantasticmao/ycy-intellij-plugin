package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import cn.fantasticmao.ycy.intellij.plugin.remind.ReminderTask;
import com.intellij.openapi.diagnostic.Logger;
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
     * 插件设置页面的表单
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
    public void apply() {
        if (this.form == null) return;

        ConfigState configState = ConfigService.getInstance().getState();
        configState.setRemindMode(this.form.getRemindModeOption());
        configState.setRemindPictures(this.form.getPictureUrlList());
        configState.setDurationInMinutes(this.form.getDurationInMinutes());
        configState.setNotifyTitle(this.form.getNotifyTitle());
        configState.setNotifyBody(this.form.getNotifyBody());
        configState.setNotifyAction(this.form.getNotifyAction());
        ConfigService.getInstance().setState(configState);
        LOG.info("apply and save user settings");

        ReminderTask.restart();
        LOG.info("restart scheduled reminder task");
    }

    /**
     * IDEA 初始化设置页面或者用户点击 "Reset" 按钮之后，会调用此方法
     */
    @Override
    public void reset() {
        if (form == null) return;

        ConfigState configState = ConfigService.getInstance().getState();
        this.form.setRemindModeOption(configState.getRemindMode());
        this.form.setPictureUrlList(configState.getRemindPictures());
        this.form.setDurationInMinutes(configState.getDurationInMinutes());
        this.form.setNotifyTitle(configState.getNotifyTitle());
        this.form.setNotifyBody(configState.getNotifyBody());
        this.form.setNotifyAction(configState.getNotifyAction());
        LOG.info("reset user settings");
    }

    /**
     * IDEA 销毁设置页面后，会调用此方法
     */
    @Override
    public void disposeUIResources() {
        this.form = null;
    }
}
