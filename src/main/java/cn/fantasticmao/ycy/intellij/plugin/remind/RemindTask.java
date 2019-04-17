package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.config.ConfigService;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigState;
import com.intellij.concurrency.JobScheduler;
import com.intellij.openapi.application.ApplicationManager;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时提醒任务
 *
 * @author maomao
 * @version 1.2
 * @since 2019-04-16
 */
public class RemindTask {
    private static final ThreadLocal<ScheduledFuture> SCHEDULED_FUTURE_CONTEXT = new ThreadLocal<>();

    /**
     * 开启定时提醒任务
     */
    public static void init() {
        restart();
    }

    /**
     * 重新开启定时提醒任务
     */
    public static void restart() {
        destroy();

        ConfigState configState = ConfigService.getInstance().getState();
        ScheduledFuture scheduledFuture = JobScheduler.getScheduler().scheduleWithFixedDelay(new Reminder(),
                configState.getPeriodMinutes(), configState.getPeriodMinutes(), TimeUnit.MINUTES);
        // 保存 ScheduledFuture 引用至 ThreadLocal 上下文中，用于后续注销定时任务
        SCHEDULED_FUTURE_CONTEXT.set(scheduledFuture);

    }

    /**
     * 注销定时提醒任务
     */
    private static void destroy() {
        ScheduledFuture existScheduledFuture = SCHEDULED_FUTURE_CONTEXT.get();
        if (existScheduledFuture != null) {
            existScheduledFuture.cancel(true);
            SCHEDULED_FUTURE_CONTEXT.remove();
        } else {
            // 还未开启定时任务
        }
    }

    /**
     * 实现定时提醒任务逻辑的线程
     *
     * @version 1.0
     */
    static class Reminder implements Runnable {

        @Override
        public void run() {
            ConfigState configState = ConfigService.getInstance().getState();
            ConfigState.RemindTypeEnum remindType = ConfigState.RemindTypeEnum.valueOf(configState.getRemindType());

            RemindStrategy remindStrategy = RemindStrategy.getRemindStrategy(remindType);
            /*
             * 2019-04-18 fix a bug: Access is allowed from event dispatch thread only. according to:
             * https://intellij-support.jetbrains.com/hc/en-us/community/posts/206124399-Error-Write-access-is-allowed-from-event-dispatch-thread-only
             */
            ApplicationManager.getApplication().invokeLater(remindStrategy::remind);
        }
    }
}
