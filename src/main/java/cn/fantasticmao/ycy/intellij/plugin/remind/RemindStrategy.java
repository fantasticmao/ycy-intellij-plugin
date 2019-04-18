package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigService;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigState;
import com.intellij.ide.DataManager;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * 打开图片的策略模式
 *
 * @author maomao
 * @version 1.2
 * @since 2019-04-17
 */
@FunctionalInterface
public interface RemindStrategy {
    Logger LOG = Logger.getInstance(RemindStrategy.class);
    RemindStrategy INSTANCE_REMIND_DIRECT = new RemindDirect();
    RemindStrategy INSTANCE_REMIND_INDIRECT = new RemindIndirect();

    /**
     * 策略模式的工厂方法
     *
     * @see ConfigState.RemindTypeEnum
     */
    @NotNull
    static RemindStrategy getRemindStrategy(ConfigState.RemindTypeEnum type) {
        RemindStrategy remindStrategy;
        switch (type) {
            case DIRECT:
                remindStrategy = INSTANCE_REMIND_DIRECT;
                break;
            case INDIRECT:
                remindStrategy = INSTANCE_REMIND_INDIRECT;
                break;
            default:
                remindStrategy = null;
        }
        return remindStrategy;
    }

    /**
     * 使用不同策略打开图片
     */
    void remind();

    /**
     * 直接打开图片
     *
     * @see ConfigState.RemindTypeEnum#DIRECT
     */
    class RemindDirect implements RemindStrategy {

        /**
         * {@inheritDoc}
         */
        @Override
        public void remind() {
            DataManager.getInstance().getDataContextFromFocus()
                    .doWhenDone((Consumer<DataContext>) (dataContext -> new OpenImageConsumer().accept(dataContext)))
                    .doWhenRejected((Consumer<String>) LOG::error);
        }
    }

    /**
     * 间接打开图片
     *
     * @see ConfigState.RemindTypeEnum#INDIRECT
     */
    class RemindIndirect implements RemindStrategy {
        private static final Logger LOG = Logger.getInstance(RemindIndirect.class);
        /**
         * IDEA Preferences -> Appearance & Behavior -> Notifications 中可设置的通知组
         */
        private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup("Plugins " + GlobalConfig.PLUGIN_NAME,
                NotificationDisplayType.STICKY_BALLOON, true);

        /**
         * {@inheritDoc}
         */
        @Override
        public void remind() {
            ConfigState configState = ConfigService.getInstance().getState();

            Notification notification = NOTIFICATION_GROUP.createNotification(configState.getNotifyTitle(),
                    configState.getNotifyContent(), NotificationType.INFORMATION, null);
            OpenImageAction openImageAction = new OpenImageAction(configState.getNotifyAction(), notification);
            notification.addAction(openImageAction);
            Notifications.Bus.notify(notification);
            LOG.info("notify an info message");
        }
    }
}
