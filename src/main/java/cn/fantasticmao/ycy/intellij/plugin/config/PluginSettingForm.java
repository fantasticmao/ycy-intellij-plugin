package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.ui.ComboBox;

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

    private JComboBox<String> remindTypeOptions;
    private JTextField remindImagePath;
    private JTextField periodMinutes;
    private JTextField notifyTitle;
    private JTextField notifyContent;
    private JTextField notifyAction;

    public JPanel getPluginSettingPanel() {
        return this.pluginSettingPanel;
    }

    private void createUIComponents() {
        // place custom component creation code here
        this.remindTypeOptions = new ComboBox<>();
        this.remindTypeOptions.addItem(ConfigState.RemindTypeEnum.DIRECT.description);
        this.remindTypeOptions.addItem(ConfigState.RemindTypeEnum.INDIRECT.description);
    }

    /**
     * 设置提醒方式
     *
     * <p>optionIndex 参数值：</p>
     * <ol>
     * <li>消息通知 -> 打开图片</li>
     * <li>打开图片</li>
     * </ol>
     *
     * @param optionIndex 1 或 2
     */
    public void setRemindTypeOption(int optionIndex) {
        optionIndex = Math.max(optionIndex, 1);
        optionIndex = Math.min(optionIndex, 2);
        this.remindTypeOptions.setSelectedIndex(optionIndex - 1);
    }

    /**
     * 设置提醒图片的绝对路径
     */
    public void setRemindImagePath(String imagePath) {
        this.remindImagePath.setText(imagePath);
    }

    /**
     * 设置提醒间隔时间，单位分钟
     */
    public void setPeriodMinutes(int periodMinutes) {
        periodMinutes = Math.max(periodMinutes, 1);
        periodMinutes = Math.min(periodMinutes, 60);
        this.periodMinutes.setText(String.valueOf(periodMinutes));
    }

    /**
     * 设置通知文案的标题
     */
    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle.setText(notifyTitle);
    }

    /**
     * 设置通知文案的内容
     */
    public void setNotifyContent(String notifyContent) {
        this.notifyContent.setText(notifyContent);
    }

    /**
     * 设置通知文案的按钮
     */
    public void setNotifyAction(String notifyAction) {
        this.notifyAction.setText(notifyAction);
    }
}
