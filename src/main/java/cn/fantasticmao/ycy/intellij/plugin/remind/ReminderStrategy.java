package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.config.ConfigService;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigState;
import com.intellij.ide.DataManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * 打开图片的策略模式
 *
 * @author fantasticmao
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
        return switch (mode) {
            case DIRECT -> INSTANCE_REMINDER_DIRECT;
            case INDIRECT -> INSTANCE_REMINDER_INDIRECT;
        };
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
            DataManager.getInstance().getDataContextFromFocusAsync()
                .onSuccess(dataContext -> new OpenPictureConsumer().accept(dataContext))
                .onError(LOG::error);
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
         * {@inheritDoc}
         */
        @Override
        public void remind() {
            ConfigState configState = ConfigService.getInstance().getState();

            /*
             * at version 1.7 fix a bug: 2020.3 版本 NotificationGroup.<init> 过时问题
             * see https://github.com/fantasticmao/ycy-intellij-plugin/issues/32
             * see https://plugins.jetbrains.com/docs/intellij/notifications.html#top-level-notifications-balloons
             */
            final String groupId = "Plugins Programmer Motivator: Chaoyue Yang";
            Notification notification = NotificationGroupManager.getInstance().getNotificationGroup(groupId)
                .createNotification(configState.getNotifyTitle(), configState.getNotifyBody(),
                    NotificationType.INFORMATION);
            if (!configState.getRemindPictures().isEmpty()) {
                OpenPictureAction openPictureAction = new OpenPictureAction(configState.getNotifyAction(), notification);
                notification.addAction(openPictureAction);
            }
            Notifications.Bus.notify(notification);
            LOG.info("notify an info message");
        }
    }
}
