package cn.fantasticmao.ycy.intellij.plugin.common;

import javax.annotation.Nonnull;

/**
 * 通知相关配置类
 *
 * @author maomao
 * @since 2019-04-04
 */
public class NotifyConfig {
    /**
     * 通知主标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知（可点击的）按钮
     */
    private String actionText;

    /**
     * 单例模式
     */
    private static NotifyConfig instance = new NotifyConfig();

    private NotifyConfig() {
        this("超越妹妹", "小哥哥，代码写好久了，该休息一下啦～", "来看看我吧");
    }

    private NotifyConfig(@Nonnull String title, @Nonnull String content, @Nonnull String actionText) {
        this.title = title;
        this.content = content;
        this.actionText = actionText;
        // TODO read from the config file
    }

    /**
     * 单例模式
     *
     * @return {@link NotifyConfig}
     */
    public static NotifyConfig getInstance() {
        return instance;
    }

    // getter and setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }
}
