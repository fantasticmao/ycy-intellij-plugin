package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.ToolbarDecorator;

import javax.swing.*;
import java.util.List;

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
    private PluginSettingTable pluginSettingTable;

    private JComboBox<String> remindModeOptions;
    private JPanel pictureUrlList;
    private JTextField durationInMinutes;
    private JTextField notifyTitle;
    private JTextField notifyContent;
    private JTextField notifyAction;

    public JPanel getPluginSettingPanel() {
        return this.pluginSettingPanel;
    }

    private void createUIComponents() {
        // place custom component creation code here
        this.remindModeOptions = new ComboBox<>();
        for (ConfigState.RemindModeEnum remindMode : ConfigState.RemindModeEnum.values()) {
            this.remindModeOptions.addItem(remindMode.description);
        }

        ConfigState configState = ConfigService.getInstance().getState();
        List<String> remindPictures = configState.getRemindPictures();
        this.pluginSettingTable = new PluginSettingTable(remindPictures);
        this.pictureUrlList = ToolbarDecorator.createDecorator(pluginSettingTable)
            /*
             * at version 1.5 fix a bug: 2020.1 版本 AllIcons.Actions.Reset_to_default 过时问题
             * see https://github.com/fantasticmao/ycy-intellij-plugin/issues/27
             */
            .addExtraAction(new AnActionButton("Reset", AllIcons.Actions.Rollback) {
                @Override
                public void actionPerformed(AnActionEvent e) {
                    pluginSettingTable.resetTableList();
                }

                @Override
                public boolean isDumbAware() {
                    return true; // 使用「后台更新」模式
                }
            })
            .createPanel();
    }

    /**
     * 获取提醒方式
     *
     * @see cn.fantasticmao.ycy.intellij.plugin.config.ConfigState.RemindModeEnum
     */
    public int getRemindModeOption() {
        return this.remindModeOptions.getSelectedIndex();
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
    public void setRemindModeOption(int optionIndex) {
        optionIndex = Math.max(optionIndex, 0);
        optionIndex = Math.min(optionIndex, 1);
        this.remindModeOptions.setSelectedIndex(optionIndex);
    }

    /**
     * 获取提醒图片列表
     */
    public List<String> getPictureUrlList() {
        return this.pluginSettingTable.getTableList();
    }

    /**
     * 设置提醒图片列表
     */
    public void setPictureUrlList(List<String> pictureList) {
        this.pluginSettingTable.setTableList(pictureList);
    }

    /**
     * 获取提醒间隔时间，单位分钟
     */
    public int getDurationInMinutes() {
        try {
            return Integer.parseInt(this.durationInMinutes.getText());
        } catch (NumberFormatException e) {
            return DefaultConfig.DURATION_IN_MINUTES;
        }
    }

    /**
     * 设置提醒间隔时间，单位分钟
     */
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes.setText(String.valueOf(durationInMinutes));
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
