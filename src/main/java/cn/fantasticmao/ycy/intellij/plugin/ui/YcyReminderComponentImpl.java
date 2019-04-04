package cn.fantasticmao.ycy.intellij.plugin.ui;

import cn.fantasticmao.ycy.intellij.plugin.common.NotifyConfig;
import com.intellij.concurrency.JobScheduler;
import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;

import java.util.concurrent.TimeUnit;

/**
 * YcyReminderComponentImpl
 *
 * @author maomao
 * @since 2019-04-03
 */
public class YcyReminderComponentImpl implements YcyReminderComponent {

    public YcyReminderComponentImpl() {
        ApplicationManager.getApplication().invokeLater(() ->
                JobScheduler.getScheduler().scheduleWithFixedDelay(new Reminder(),
                        5, 60, TimeUnit.MINUTES));
    }

    private class Reminder implements Runnable {
        private OpenYcyImageAction openYcyImageAction;
        private NotificationGroup notificationGroup;

        private Reminder() {
            this.openYcyImageAction = new OpenYcyImageAction();
            this.notificationGroup = new NotificationGroup("Plugins 超越鼓励师",
                    NotificationDisplayType.STICKY_BALLOON, true);
        }

        @Override
        public void run() {
            NotifyConfig notifyConfig = NotifyConfig.getInstance();
            Notification notification = notificationGroup.createNotification(notifyConfig.getTitle(),
                    notifyConfig.getContent(), NotificationType.INFORMATION, null);
            notification.addAction(openYcyImageAction);
            Notifications.Bus.notify(notification);
        }
    }
}
