package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.xmlb.annotations.OptionTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

/**
 * 配置状态类
 *
 * @author fantasticmao
 * @version 1.2
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-14
 */
public class ConfigState {
    @OptionTag
    private Integer remindMode;
    @OptionTag
    private List<String> remindPictures;
    @OptionTag
    private Integer durationInMinutes;
    @OptionTag
    private String notifyTitle;
    @OptionTag
    private String notifyBody;
    @OptionTag
    private String notifyAction;
    @OptionTag
    private Boolean disabled;

    /**
     * 提醒方式枚举类
     *
     * @see cn.fantasticmao.ycy.intellij.plugin.remind.ReminderStrategy
     */
    public enum RemindModeEnum {
        /**
         * 直接打开图片
         */
        DIRECT(0, I18nBundle.message(I18nBundle.Key.CONFIG_VALUE_REMIND_MODE_DIRECT)),
        /**
         * 间接打开图片
         */
        INDIRECT(1, I18nBundle.message(I18nBundle.Key.CONFIG_VALUE_REMIND_MODE_INDIRECT));

        public final int index;
        public final String description;

        RemindModeEnum(int index, String description) {
            this.index = index;
            this.description = description;
        }

        public static RemindModeEnum valueOf(int index) {
            return Stream.of(RemindModeEnum.values())
                .filter(remindMode -> index == remindMode.index)
                .findFirst()
                .orElseThrow(NullPointerException::new);
        }
    }

    /**
     * 默认配置对象
     *
     * <p>在 {@link ConfigService#setState(ConfigState)} 时，新配置对象会与默认配置对象作比较，
     * IDEA 会保存有差异的字段至 {@link ConfigServiceImpl} 指定的 {@code ycyReminder.xml} 配置文件中</p>
     *
     * @see ConfigService#getState()
     * @see ConfigService#setState(ConfigState)
     */
    public ConfigState() {
        // 第一次开启插件时，应该使用默认配置
        this.remindMode = DefaultConfig.REMIND_MODE;
        this.remindPictures = new ArrayList<>(DefaultConfig.REMIND_PICTURE_LIST);
        this.durationInMinutes = DefaultConfig.DURATION_IN_MINUTES;
        this.notifyTitle = DefaultConfig.NOTIFY_TITLE;
        this.notifyBody = DefaultConfig.NOTIFY_BODY;
        this.notifyAction = DefaultConfig.NOTIFY_ACTION;
        this.disabled = DefaultConfig.DISABLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigState that = (ConfigState) o;
        return Objects.equals(remindMode, that.remindMode) &&
            Objects.equals(remindPictures, that.remindPictures) &&
            Objects.equals(durationInMinutes, that.durationInMinutes) &&
            Objects.equals(notifyTitle, that.notifyTitle) &&
            Objects.equals(notifyBody, that.notifyBody) &&
            Objects.equals(notifyAction, that.notifyAction) &&
            Objects.equals(disabled, that.disabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remindMode, remindPictures, durationInMinutes,
            notifyTitle, notifyBody, notifyAction, disabled);
    }

    /**
     * 随机获取待展示的图片
     */
    public String getRandomRemindPicture() {
        if (this.remindPictures == null || this.remindPictures.size() == 0) {
            throw new IllegalArgumentException("picture list cannot be empty");
        }

        final String pictureIndexCacheKey = GlobalConfig.PLUGIN_ID + "_showedPictureIndex";
        final int pictureIndex;
        if (this.remindPictures.size() > 1) {
            // 1. 获取上次展示的图片 index
            final String prevPictureIndexStr = PropertiesComponent.getInstance().getValue(pictureIndexCacheKey);

            // 2. 生成下次展示的图片 index
            if (prevPictureIndexStr != null) {
                // 2.1 若上次展示的图片 index 存在，则生成下次展示的图片 index 时，需要避免与上次展示的重复
                final int prevPictureIndex = Integer.parseInt(prevPictureIndexStr);
                for (; ; ) { // 使用 for(; ;) 而不是 while(true)，让代码看起来更酷
                    int nextPictureIndex = new Random().nextInt(this.remindPictures.size());
                    if (nextPictureIndex != prevPictureIndex) {
                        pictureIndex = nextPictureIndex;
                        break;
                    }
                }
            } else {
                // 2.2 若上次展示的图片 index 不存在，则直接随机生成下次展示的图片 index
                pictureIndex = new Random().nextInt(this.remindPictures.size());
            }
        } else {
            pictureIndex = 0;
        }
        // 保存这次展示的图片 index
        PropertiesComponent.getInstance().setValue(pictureIndexCacheKey, String.valueOf(pictureIndex));
        return this.remindPictures.get(pictureIndex);
    }

    // getter and setter

    public Integer getRemindMode() {
        return remindMode;
    }

    public void setRemindMode(Integer remindMode) {
        this.remindMode = remindMode;
    }

    public List<String> getRemindPictures() {
        return remindPictures;
    }

    public void setRemindPictures(List<String> remindPictures) {
        this.remindPictures = remindPictures;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyBody() {
        return notifyBody;
    }

    public void setNotifyBody(String notifyBody) {
        this.notifyBody = notifyBody;
    }

    public String getNotifyAction() {
        return notifyAction;
    }

    public void setNotifyAction(String notifyAction) {
        this.notifyAction = notifyAction;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
