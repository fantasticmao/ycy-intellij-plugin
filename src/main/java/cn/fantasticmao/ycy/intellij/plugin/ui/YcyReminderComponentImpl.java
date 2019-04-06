package cn.fantasticmao.ycy.intellij.plugin.ui;

import cn.fantasticmao.ycy.intellij.plugin.common.Config;
import cn.fantasticmao.ycy.intellij.plugin.common.NotifyConfig;
import com.intellij.concurrency.JobScheduler;
import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;

import java.util.concurrent.TimeUnit;

/**
 * {@link YcyReminderComponent} 实现类
 *
 * @author maomao
 * @since 2019-04-03
 */
public class YcyReminderComponentImpl implements YcyReminderComponent {

    public YcyReminderComponentImpl() {
        ApplicationManager.getApplication().invokeLater(() ->
                JobScheduler.getScheduler().scheduleWithFixedDelay(new Reminder(),
                        50, 60, TimeUnit.MINUTES));
    }

    /**
     * 实现定时提醒逻辑的线程
     */
    class Reminder implements Runnable {
        private NotificationGroup notificationGroup;

        private Reminder() {
            this.notificationGroup = new NotificationGroup("Plugins " + Config.PLUGIN_NAME,
                    NotificationDisplayType.STICKY_BALLOON, true);
        }

        @Override
        public void run() {
            NotifyConfig notifyConfig = NotifyConfig.getInstance();
            Notification notification = notificationGroup.createNotification(notifyConfig.getTitle(),
                    notifyConfig.getContent(), NotificationType.INFORMATION, null);
            OpenYcyImageAction openYcyImageAction = new OpenYcyImageAction(notification);
            notification.addAction(openYcyImageAction);
            Notifications.Bus.notify(notification);
        }
    }
}
