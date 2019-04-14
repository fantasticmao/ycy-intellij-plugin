package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.util.xmlb.annotations.Attribute;

import java.util.Objects;

/**
 * 配置状态类
 *
 * @author maomao
 * @since 2019-04-14
 */
public class ConfigState {
    @Attribute("remindType")
    private Integer remindType;
    @Attribute("remindImagePath")
    private String remindImagePath;
    @Attribute("periodMinutes")
    private Integer periodMinutes;
    @Attribute("notifyTitle")
    private String notifyTitle;
    @Attribute("notifyContent")
    private String notifyContent;
    @Attribute("notifyAction")
    private String notifyAction;

    public enum RemindTypeEnum {
        DIRECT(1, "打开图片"),
        INDIRECT(2, "消息通知 -> 打开图片");

        public final int index;
        public final String description;

        RemindTypeEnum(int index, String description) {
            this.index = index;
            this.description = description;
        }
    }

    public ConfigState() {
        // 第一次开启插件时，应该使用默认配置
        this.remindType = DefaultConfig.REMIND_TYPE;
        this.remindImagePath = DefaultConfig.REMIND_IMAGE_PATH;
        this.periodMinutes = DefaultConfig.PERIOD_MINUTES;
        this.notifyTitle = DefaultConfig.NOTIFY_TITLE;
        this.notifyContent = DefaultConfig.NOTIFY_CONTENT;
        this.notifyAction = DefaultConfig.NOTIFY_ACTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigState that = (ConfigState) o;
        return Objects.equals(remindType, that.remindType) &&
                Objects.equals(remindImagePath, that.remindImagePath) &&
                Objects.equals(periodMinutes, that.periodMinutes) &&
                Objects.equals(notifyTitle, that.notifyTitle) &&
                Objects.equals(notifyContent, that.notifyContent) &&
                Objects.equals(notifyAction, that.notifyAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remindType, remindImagePath, periodMinutes, notifyTitle, notifyContent, notifyAction);
    }

    // getter and setter

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public String getRemindImagePath() {
        return remindImagePath;
    }

    public void setRemindImagePath(String remindImagePath) {
        this.remindImagePath = remindImagePath;
    }

    public Integer getPeriodMinutes() {
        return periodMinutes;
    }

    public void setPeriodMinutes(Integer periodMinutes) {
        this.periodMinutes = periodMinutes;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getNotifyAction() {
        return notifyAction;
    }

    public void setNotifyAction(String notifyAction) {
        this.notifyAction = notifyAction;
    }
}
