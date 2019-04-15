package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigService;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigState;
import com.intellij.concurrency.JobScheduler;
import com.intellij.notification.*;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时提醒任务
 *
 * @author maomao
 * @since 2019-04-16
 */
public class ImageRemindTask {
    private static final ThreadLocal<ScheduledFuture> SCHEDULED_FUTURE_CONTEXT = new ThreadLocal<>();

    /**
     * 开启定时提醒任务
     */
    public static void init() {
        refresh();
    }

    /**
     * 重新开启定时提醒任务
     */
    public static void refresh() {
        destroy();

        ConfigState configState = ConfigService.getInstance().getState();
        ScheduledFuture scheduledFuture = JobScheduler.getScheduler().scheduleWithFixedDelay(new Reminder(),
                configState.getPeriodMinutes(), configState.getPeriodMinutes(), TimeUnit.MINUTES);
        SCHEDULED_FUTURE_CONTEXT.set(scheduledFuture);

    }

    /**
     * 注销定时提醒任务
     */
    private static void destroy() {
        ScheduledFuture existScheduledFuture = SCHEDULED_FUTURE_CONTEXT.get();
        if (existScheduledFuture != null) {
            existScheduledFuture.cancel(true);
            SCHEDULED_FUTURE_CONTEXT.set(null);
        } else {
            // 还未开启定时任务
        }
    }

    /**
     * 实现定时提醒任务逻辑的线程
     */
    static class Reminder implements Runnable {
        private NotificationGroup notificationGroup;

        private Reminder() {
            this.notificationGroup = new NotificationGroup("Plugins " + GlobalConfig.PLUGIN_NAME,
                    NotificationDisplayType.STICKY_BALLOON, true);
        }

        @Override
        public void run() {
            ConfigState configState = ConfigService.getInstance().getState();

            Notification notification = notificationGroup.createNotification(configState.getNotifyTitle(),
                    configState.getNotifyContent(), NotificationType.INFORMATION, null);
            OpenImageAction openImageAction = new OpenImageAction(configState.getNotifyAction(), notification);
            notification.addAction(openImageAction);

            Notifications.Bus.notify(notification);
        }
    }
}
