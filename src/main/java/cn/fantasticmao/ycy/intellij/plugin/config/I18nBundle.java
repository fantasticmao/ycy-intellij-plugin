package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;

/**
 * 国际化服务
 *
 * <p>依赖于 {@link com.intellij.AbstractBundle} 组件实现</p>
 *
 * @author maomao
 * @version 1.6
 * @since 2021-02-01
 */
public class I18nBundle extends AbstractBundle {
    private static final I18nBundle INSTANCE = new I18nBundle();

    private I18nBundle() {
        super("i18n.templates");
    }

    /**
     * 获取国际化值
     *
     * @param key    {@code template.properties} 中的键
     * @param params {@code template.properties} 中的值占位符
     * @return 国际化值
     * @see Key
     */
    public static String message(@NotNull Key key, @NotNull Object... params) {
        return INSTANCE.getMessage(key.value, params);
    }

    public enum Key {
        // 插件
        PLUGIN_NAME("plugin.name"),

        // 配置界面 - 表单键
        CONFIG_LABEL_REMIND_MODE_OPTIONS("config.label.remind.mode.options"),
        CONFIG_LABEL_PICTURE_URL_LIST("config.label.picture.url.list"),
        CONFIG_LABEL_DURATION_IN_MINUTES("config.label.duration.in.minutes"),
        CONFIG_LABEL_NOTIFY_CONTENT_TITLE("config.label.notify.content.title"),
        CONFIG_LABEL_NOTIFY_CONTENT_BODY("config.label.notify.content.body"),
        CONFIG_LABEL_NOTIFY_CONTENT_ACTION("config.label.notify.content.action"),
        CONFIG_LABEL_DISABLED("config.label.disabled"),

        // 配置界面 - 表单值
        CONFIG_VALUE_REMIND_MODE_DIRECT("config.value.remind.mode.direct"),
        CONFIG_VALUE_REMIND_MODE_INDIRECT("config.value.remind.mode.indirect"),
        CONFIG_VALUE_NOTIFY_CONTENT_TITLE("config.value.notify.content.title"),
        CONFIG_VALUE_NOTIFY_CONTENT_BODY("config.value.notify.content.body"),
        CONFIG_VALUE_NOTIFY_CONTENT_ACTION("config.value.notify.content.action"),

        // 配置界面 - 表格
        CONFIG_TABLE_COLUMN_0("config.table.column.0"),
        CONFIG_TABLE_COLUMN_1("config.table.column.1"),

        // 图片选择器
        PICTURE_CHOOSER_TITLE("picture.chooser.title"),
        PICTURE_CHOOSER_ERROR_FORMAT("picture.chooser.error.format"),

        // 符号
        SYMBOL_DELIMITER("symbol.delimiter");

        final String value;

        Key(String value) {
            this.value = value;
        }
    }
}
