package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

/**
 * {@link ReminderComponent} 实现类
 *
 * @author fantasticmao
 * @version 1.0
 * @since 2019-04-03
 */
public class ReminderComponentImpl implements ReminderComponent {
    private static final Logger LOG = Logger.getInstance(ReminderComponentImpl.class);

    public ReminderComponentImpl() {
        ApplicationManager.getApplication().invokeLater(() -> {
            LOG.info("=== Programmer Motivator: Chaoyue Yang(超越鼓励师) ===");

            ReminderTask.init();
            LOG.info("start scheduled reminder task");
        });
    }

}
