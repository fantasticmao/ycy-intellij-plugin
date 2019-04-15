package cn.fantasticmao.ycy.intellij.plugin.config;

import cn.fantasticmao.ycy.intellij.plugin.remind.ImageManager;

import java.util.concurrent.TimeUnit;

/**
 * 默认配置接口
 *
 * @author maomao
 * @since 2019-04-14
 */
public interface DefaultConfig {

    /**
     * 默认提醒方式
     */
    Integer REMIND_TYPE = ConfigState.RemindTypeEnum.INDIRECT.index;

    /**
     * 默认提醒图片的绝对路径
     */
    String REMIND_IMAGE_PATH = ImageManager.getInstance().getImageUrl().getPath();

    /**
     * 默认提醒间隔时间
     */
    Integer PERIOD_MINUTES = (int) TimeUnit.HOURS.toMinutes(1);

    /**
     * 默认通知文案的标题
     */
    String NOTIFY_TITLE = "超越妹妹";

    /**
     * 默认通知文案的内容
     */
    String NOTIFY_CONTENT = "小哥哥，代码写好久了，该休息一下啦～";

    /**
     * 默认通知文案的按钮
     */
    String NOTIFY_ACTION = "来看看我吧";
}
