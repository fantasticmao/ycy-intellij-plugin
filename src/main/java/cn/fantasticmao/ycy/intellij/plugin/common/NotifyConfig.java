package cn.fantasticmao.ycy.intellij.plugin.common;

import javax.annotation.Nullable;

/**
 * NotifyConfig
 *
 * @author maomao
 * @since 2019-04-04
 */
public class NotifyConfig {
    private String title;
    private String content;
    private String actionText;

    private static NotifyConfig instance = new NotifyConfig();

    private NotifyConfig() {
        this("超越妹妹", "小哥哥，代码写好久了，该休息一下啦～", "来看看我吧");
    }

    private NotifyConfig(@Nullable String title, @Nullable String content, @Nullable String actionText) {
        this.title = title;
        this.content = content;
        this.actionText = actionText;
        // TODO read from the config file
    }

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
