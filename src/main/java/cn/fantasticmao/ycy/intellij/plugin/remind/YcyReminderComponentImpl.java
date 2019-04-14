package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigService;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigState;
import com.intellij.concurrency.JobScheduler;
import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

import java.util.concurrent.TimeUnit;

/**
 * {@link YcyReminderComponent} 实现类
 *
 * @author maomao
 * @since 2019-04-03
 */
public class YcyReminderComponentImpl implements YcyReminderComponent {
    private static final Logger LOG = Logger.getInstance(YcyReminderComponentImpl.class);

    public YcyReminderComponentImpl() {
        ApplicationManager.getApplication().invokeLater(() -> {
            ConfigState configState = ConfigService.getInstance().getState();

            JobScheduler.getScheduler().scheduleWithFixedDelay(new Reminder(),
                    configState.getPeriodMinutes(), configState.getPeriodMinutes(), TimeUnit.MINUTES);
        });
        LOG.info("=== start scheduled task Programmer Motivator: Chaoyue Yang(超越鼓励师) ===");
    }

    /**
     * 实现定时提醒逻辑的线程
     */
    class Reminder implements Runnable {
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
            OpenYcyImageAction openYcyImageAction = new OpenYcyImageAction(configState.getNotifyAction(), notification);
            notification.addAction(openYcyImageAction);

            Notifications.Bus.notify(notification);
        }
    }
}
