package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.remind.PictureManager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 默认配置接口
 *
 * @author fantasticmao
 * @version 1.0
 * @since 2019-04-14
 */
public interface DefaultConfig {

    /**
     * 默认提醒方式
     */
    Integer REMIND_MODE = ConfigState.RemindModeEnum.INDIRECT.index;

    /**
     * 默认提醒图片列表
     */
    // Immutable
    List<String> REMIND_PICTURE_LIST = Collections.unmodifiableList(PictureManager.getDefaultPictureList());

    /**
     * 默认提醒间隔时间
     */
    Integer DURATION_IN_MINUTES = (int) TimeUnit.HOURS.toMinutes(1);

    /**
     * 默认通知文案的标题
     */
    String NOTIFY_TITLE = I18nBundle.message(I18nBundle.Key.CONFIG_VALUE_NOTIFY_CONTENT_TITLE);

    /**
     * 默认通知文案的内容
     */
    String NOTIFY_BODY = I18nBundle.message(I18nBundle.Key.CONFIG_VALUE_NOTIFY_CONTENT_BODY);

    /**
     * 默认通知文案的按钮
     */
    String NOTIFY_ACTION = I18nBundle.message(I18nBundle.Key.CONFIG_VALUE_NOTIFY_CONTENT_ACTION);

    /**
     * 默认禁用状态
     */
    Boolean DISABLED = Boolean.FALSE;
}
