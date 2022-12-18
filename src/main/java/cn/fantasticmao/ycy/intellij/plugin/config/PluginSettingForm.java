package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.ToolbarDecorator;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

/**
 * 插件设置页面的表单
 *
 * <p>表单实例化由插件 {@code UI Designer} 根据 {@code PluginSettingForm.form} 配置文件生成</p>
 *
 * @author fantasticmao
 * @version 1.2
 * @see PluginSettingPage#createComponent()
 * @since 2019-04-13
 */
public class PluginSettingForm {
    private JPanel pluginSettingPanel;

    private JLabel remindModeLabel;
    private JComboBox<String> remindModeOptions;

    private JLabel pictureUrlLabel;
    private PluginSettingPictureUrlTable pictureUrlTable;
    private JPanel pictureUrlPanel;

    private JLabel durationInMinutesLabel;
    private JTextField durationInMinutesField;

    private JLabel notifyTitleLabel;
    private JTextField notifyTitleField;

    private JLabel notifyBodyLabel;
    private JTextField notifyBodyField;

    private JLabel notifyActionLabel;
    private JTextField notifyActionField;

    private JLabel disabledLabel;
    private JCheckBox disabledField;

    public JPanel getPluginSettingPanel() {
        return this.pluginSettingPanel;
    }

    private void createUIComponents() {
        // create custom UI components
        final ConfigState configState = ConfigService.getInstance().getState();

        String remindModeLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_REMIND_MODE_OPTIONS);
        this.remindModeLabel = new JLabel(remindModeLabelText);
        this.remindModeOptions = new ComboBox<>();
        for (ConfigState.RemindModeEnum remindMode : ConfigState.RemindModeEnum.values()) {
            this.remindModeOptions.addItem(remindMode.description);
        }

        String pictureUrlLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_PICTURE_URL_LIST);
        this.pictureUrlLabel = new JLabel(pictureUrlLabelText);
        List<String> remindPictures = configState.getRemindPictures();
        this.pictureUrlTable = new PluginSettingPictureUrlTable(remindPictures);
        this.pictureUrlPanel = ToolbarDecorator.createDecorator(pictureUrlTable)
            /*
             * at version 1.5 fix a bug: 2020.1 版本 AllIcons.Actions.Reset_to_default 过时问题
             * see https://github.com/fantasticmao/ycy-intellij-plugin/issues/27
             */
            .addExtraAction(new AnActionButton("Reset", AllIcons.Actions.Rollback) {
                @Override
                public void actionPerformed(@NotNull AnActionEvent e) {
                    pictureUrlTable.resetTableList();
                }

                @Override
                public boolean isDumbAware() {
                    return true; // 使用「后台更新」模式
                }
            })
            .createPanel();

        String durationInMinutesLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_DURATION_IN_MINUTES);
        this.durationInMinutesLabel = new JLabel(durationInMinutesLabelText);

        String notifyTitleLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_NOTIFY_CONTENT_TITLE);
        this.notifyTitleLabel = new JLabel(notifyTitleLabelText);

        String notifyBodyLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_NOTIFY_CONTENT_BODY);
        this.notifyBodyLabel = new JLabel(notifyBodyLabelText);

        String notifyActionLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_NOTIFY_CONTENT_ACTION);
        this.notifyActionLabel = new JLabel(notifyActionLabelText);

        String disabledLabelText = I18nBundle.message(I18nBundle.Key.CONFIG_LABEL_DISABLED);
        this.disabledLabel = new JLabel(disabledLabelText);
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
        return this.pictureUrlTable.getTableList();
    }

    /**
     * 设置提醒图片列表
     */
    public void setPictureUrlList(List<String> pictureList) {
        this.pictureUrlTable.setTableList(pictureList);
    }

    /**
     * 获取提醒间隔时间，单位分钟
     */
    public int getDurationInMinutes() {
        try {
            return Integer.parseInt(this.durationInMinutesField.getText());
        } catch (NumberFormatException e) {
            return DefaultConfig.DURATION_IN_MINUTES;
        }
    }

    /**
     * 设置提醒间隔时间，单位分钟
     */
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutesField.setText(String.valueOf(durationInMinutes));
    }

    /**
     * 获取通知文案的标题
     */
    public String getNotifyTitle() {
        return this.notifyTitleField.getText();
    }

    /**
     * 设置通知文案的标题
     */
    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitleField.setText(notifyTitle);
    }

    /**
     * 获取通知文案的内容
     */
    public String getNotifyBody() {
        return this.notifyBodyField.getText();
    }

    /**
     * 设置通知文案的内容
     */
    public void setNotifyBody(String notifyContent) {
        this.notifyBodyField.setText(notifyContent);
    }

    /**
     * 获取通知文案的按钮
     */
    public String getNotifyAction() {
        return this.notifyActionField.getText();
    }

    /**
     * 设置通知文案的按钮
     */
    public void setNotifyAction(String notifyAction) {
        this.notifyActionField.setText(notifyAction);
    }

    /**
     * 获取禁用状态
     */
    public Boolean getDisabled() {
        return this.disabledField.isSelected();
    }

    /**
     * 设置禁用状态
     */
    public void setDisabled(Boolean disabled) {
        this.disabledField.setSelected(disabled);
    }
}
