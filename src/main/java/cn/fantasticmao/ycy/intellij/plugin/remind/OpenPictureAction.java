package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.ide.DataManager;
import com.intellij.notification.Notification;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * 打开图片的动作事件
 *
 * @author fantasticmao
 * @version 1.0
 * @since 2019-04-04
 */
class OpenPictureAction extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenPictureAction.class);

    private final Notification notification;

    /**
     * 构造方法
     *
     * @param text         通知中可点击的按钮的文案
     * @param notification 通知对象，在点击按钮之后需要调用 {@link Notification#expire()}
     */
    OpenPictureAction(String text, Notification notification) {
        super(text);
        this.notification = notification;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        /*
         * at version 1.2 fix a bug: 2017.1 版本无法打开图片问题
         * see https://github.com/fantasticmao/ycy-intellij-plugin/issues/9
         */
        DataManager.getInstance().getDataContextFromFocusAsync()
            .onSuccess(dataContext -> new OpenPictureConsumer().accept(dataContext))
            .onError(LOG::error);

        // 使打开图片按钮失效，避免重复点击
        notification.expire();
        LOG.info("notification action has been expired");
    }

}
