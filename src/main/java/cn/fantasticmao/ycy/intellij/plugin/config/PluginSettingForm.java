package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;

/**
 * 插件设置页面的表单对象
 *
 * <p>表单对象的实例化由插件 {@code UI Designer} 根据 {@code PluginSettingForm.form} 配置文件生成</p>
 *
 * @author maomao
 * @version 1.2
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
        for (ConfigState.RemindTypeEnum remindType : ConfigState.RemindTypeEnum.values()) {
            this.remindTypeOptions.addItem(remindType.description);
        }
    }

    /**
     * 获取提醒方式
     *
     * @return {@code cn.fantasticmao.ycy.intellij.plugin.config.ConfigState.RemindTypeEnum}
     */
    public int getRemindTypeOption() {
        return this.remindTypeOptions.getSelectedIndex();
    }

    /**
     * 设置提醒方式
     *
     * <p>optionIndex 参数值：</p>
     * <ul>
     * <li>0. 消息通知 -> 打开图片</li>
     * <li>1. 打开图片</li>
     * </ul>
     *
     * @param optionIndex 0 或 1
     */
    public void setRemindTypeOption(int optionIndex) {
        optionIndex = Math.max(optionIndex, 0);
        optionIndex = Math.min(optionIndex, 1);
        this.remindTypeOptions.setSelectedIndex(optionIndex);
    }

    /**
     * 获取提醒图片的绝对路径
     */
    public String getRemindImagePath() {
        return this.remindImagePath.getText();
    }

    /**
     * 设置提醒图片的绝对路径
     */
    public void setRemindImagePath(String imagePath) {
        this.remindImagePath.setText(imagePath);
    }

    /**
     * 获取提醒间隔时间，单位分钟
     */
    public int getPeriodMinutes() {
        try {
            return Integer.parseInt(this.periodMinutes.getText());
        } catch (NumberFormatException e) {
            return DefaultConfig.PERIOD_MINUTES;
        }
    }

    /**
     * 设置提醒间隔时间，单位分钟
     */
    public void setPeriodMinutes(int periodMinutes) {
        this.periodMinutes.setText(String.valueOf(periodMinutes));
    }

    /**
     * 获取通知文案的标题
     */
    public String getNotifyTitle() {
        return this.notifyTitle.getText();
    }

    /**
     * 设置通知文案的标题
     */
    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle.setText(notifyTitle);
    }

    /**
     * 获取通知文案的内容
     */
    public String getNotifyContent() {
        return this.notifyContent.getText();
    }

    /**
     * 设置通知文案的内容
     */
    public void setNotifyContent(String notifyContent) {
        this.notifyContent.setText(notifyContent);
    }

    /**
     * 获取通知文案的按钮
     */
    public String getNotifyAction() {
        return this.notifyAction.getText();
    }

    /**
     * 设置通知文案的按钮
     */
    public void setNotifyAction(String notifyAction) {
        this.notifyAction.setText(notifyAction);
    }
}