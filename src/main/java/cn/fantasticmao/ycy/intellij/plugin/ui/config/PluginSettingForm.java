package cn.fantasticmao.ycy.intellij.plugin.ui.config;

import javax.swing.*;

/**
 * 插件设置页面的表单对象
 *
 * <p>表单对象的实例化由插件 {@code UI Designer} 根据 {@code PluginSettingForm.form} 配置文件生成。</p>
 *
 * @author maomao
 * @see PluginSettingPage#createComponent()
 * @since 2019-04-13
 */
public class PluginSettingForm {
    private JPanel pluginSettingPanel;

    private JComboBox<String> reminderOptions;
    private JTextField remindImagePath;
    private JSpinner periodMinutes;
    private JTextField notifyTitle;
    private JTextField notifyContent;
    private JTextField notifyAction;

    public JPanel getPluginSettingPanel() {
        return pluginSettingPanel;
    }
}
