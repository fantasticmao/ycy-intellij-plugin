package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.xmlb.annotations.OptionTag;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

/**
 * 配置状态类
 *
 * @author maomao
 * @version 1.2
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-14
 */
public class ConfigState {
    @OptionTag
    private Integer remindType;
    @OptionTag
    private List<String> remindImages;
    @OptionTag
    private Integer periodMinutes;
    @OptionTag
    private String notifyTitle;
    @OptionTag
    private String notifyContent;
    @OptionTag
    private String notifyAction;

    /**
     * 提醒方式枚举类
     *
     * @see cn.fantasticmao.ycy.intellij.plugin.remind.RemindStrategy
     */
    public enum RemindTypeEnum {
        /**
         * 直接打开图片
         */
        DIRECT(0, "打开图片"),
        /**
         * 间接打开图片
         */
        INDIRECT(1, "消息通知 -> 打开图片");

        public final int index;
        public final String description;

        RemindTypeEnum(int index, String description) {
            this.index = index;
            this.description = description;
        }

        public static RemindTypeEnum valueOf(int index) {
            return Stream.of(RemindTypeEnum.values())
                .filter(remindType -> index == remindType.index)
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
        this.remindType = DefaultConfig.REMIND_TYPE;
        this.remindImages = DefaultConfig.REMIND_IMAGE_LIST;
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
            Objects.equals(remindImages, that.remindImages) &&
            Objects.equals(periodMinutes, that.periodMinutes) &&
            Objects.equals(notifyTitle, that.notifyTitle) &&
            Objects.equals(notifyContent, that.notifyContent) &&
            Objects.equals(notifyAction, that.notifyAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remindType, remindImages, periodMinutes, notifyTitle, notifyContent, notifyAction);
    }

    /**
     * 随机获取待展示的图片
     */
    public String getRandomRemindImage() {
        final String imageIndexCacheKey = GlobalConfig.PLUGIN_ID + "_showedImageIndex";
        return Optional
            .ofNullable(this.remindImages)
            .filter(item -> !item.isEmpty()) // 防止没有图片时，发生数组越界异常
            .map(remindImages -> {
                final int imageIndex;
                if (this.remindImages.size() > 1) {
                    // 1. 获取上次展示的图片 index
                    final String prevImageIndexStr = PropertiesComponent.getInstance().getValue(imageIndexCacheKey);

                    // 2. 生成下次展示的图片 index
                    if (prevImageIndexStr != null) {
                        // 2.1 若上次展示的图片 index 存在，则生成下次展示的图片 index 时，需要避免与上次展示的重复
                        final int prevImageIndex = Integer.parseInt(prevImageIndexStr);
                        for (; ; ) { // 使用 for(; ;) 而不是 while(true)，让代码看起来更酷
                            int nextImageIndex = new Random().nextInt(this.remindImages.size());
                            if (nextImageIndex != prevImageIndex) {
                                imageIndex = nextImageIndex;
                                break;
                            }
                        }
                    } else {
                        // 2.2 若上次展示的图片 index 不存在，则直接随机生成下次展示的图片 index
                        imageIndex = new Random().nextInt(this.remindImages.size());
                    }
                } else {
                    imageIndex = 0;
                }
                // 保存这次展示的图片 index
                PropertiesComponent.getInstance().setValue(imageIndexCacheKey, String.valueOf(imageIndex));
                return this.remindImages.get(imageIndex);
            })
            .orElseThrow(() -> new IllegalArgumentException("image list cannot be empty"));
    }

    // getter and setter

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public List<String> getRemindImages() {
        return remindImages;
    }

    public void setRemindImages(List<String> remindImages) {
        this.remindImages = remindImages;
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
