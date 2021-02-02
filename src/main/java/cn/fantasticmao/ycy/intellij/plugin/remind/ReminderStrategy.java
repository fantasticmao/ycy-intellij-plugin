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
public interface ReminderStrategy {
    Logger LOG = Logger.getInstance(ReminderStrategy.class);
    ReminderStrategy INSTANCE_REMINDER_DIRECT = new ReminderDirect();
    ReminderStrategy INSTANCE_REMINDER_INDIRECT = new ReminderIndirect();

    /**
     * 策略模式的工厂方法
     *
     * @see ConfigState.RemindModeEnum
     */
    @NotNull
    static ReminderStrategy getRemindStrategy(ConfigState.RemindModeEnum mode) {
        ReminderStrategy reminderStrategy;
        switch (mode) {
            case DIRECT:
                reminderStrategy = INSTANCE_REMINDER_DIRECT;
                break;
            case INDIRECT:
                reminderStrategy = INSTANCE_REMINDER_INDIRECT;
                break;
            default:
                reminderStrategy = null;
        }
        return reminderStrategy;
    }

    /**
     * 使用不同策略打开图片
     */
    void remind();

    /**
     * 直接打开图片
     *
     * @see ConfigState.RemindModeEnum#DIRECT
     */
    class ReminderDirect implements ReminderStrategy {

        /**
         * {@inheritDoc}
         */
        @Override
        public void remind() {
            DataManager.getInstance().getDataContextFromFocus()
                .doWhenDone((Consumer<DataContext>) (dataContext -> new OpenPictureConsumer().accept(dataContext)))
                .doWhenRejected((Consumer<String>) LOG::error);
        }
    }

    /**
     * 间接打开图片
     *
     * @see ConfigState.RemindModeEnum#INDIRECT
     */
    class ReminderIndirect implements ReminderStrategy {
        private static final Logger LOG = Logger.getInstance(ReminderIndirect.class);
        /**
         * <code>IDEA Preferences -> Appearance & Behavior -> Notifications</code> 中可设置的通知组
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
                configState.getNotifyBody(), NotificationType.INFORMATION, null);
            if (!configState.getRemindPictures().isEmpty()) {
                OpenPictureAction openPictureAction = new OpenPictureAction(configState.getNotifyAction(), notification);
                notification.addAction(openPictureAction);
            }
            Notifications.Bus.notify(notification);
            LOG.info("notify an info message");
        }
    }
}
