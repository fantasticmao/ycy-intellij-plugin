package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

/**
 * {@inheritDoc}
 *
 * <p>{@link ReminderComponent} 实现类</p>
 *
 * @author maomao
 * @since 2019-04-03
 */
public class ReminderComponentImpl implements ReminderComponent {
    private static final Logger LOG = Logger.getInstance(ReminderComponentImpl.class);

    public ReminderComponentImpl() {
        ApplicationManager.getApplication().invokeLater(() -> {
            ImageRemindTask.init();
            LOG.info("=== start scheduled task Programmer Motivator: Chaoyue Yang(超越鼓励师) ===");
        });
    }

}
