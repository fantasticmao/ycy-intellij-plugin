package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.notification.Notification;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.net.URL;

/**
 * 打开图片的动作事件
 *
 * @author maomao
 * @since 2019-04-04
 */
class OpenImageAction extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenImageAction.class);

    private final Notification notification;

    OpenImageAction(String text, Notification notification) {
        super(text);
        this.notification = notification;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // FIXME 2017.1 版本无法打开图片问题 https://github.com/FantasticMao/ycy-intellij-plugin/issues/9
        // 1. 获取 IDEA 正在使用的 Project
        Project currentProject = event.getData(PlatformDataKeys.PROJECT);
        if (currentProject == null) {
            LOG.warn("no Project is active");
            return;
        }

        // 2. 获取即将用于展示的图片
        URL imageUrl = ImageManager.getInstance().getImageUrl();
        VirtualFile image = VfsUtil.findFileByURL(imageUrl);
        if (image == null) {
            LOG.error("image cannot be null, URL: " + imageUrl.toString());
            return;
        }

        // 3. 获取当前 Project 中，正在使用的 EditorWindow
        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(currentProject);
        EditorWindow currentWindow = fileEditorManager.getCurrentWindow();
        if (currentWindow == null || currentWindow.getTabCount() == 0) {
            // 3.1 如果没有打开 EditorWindow 或者 EditorWindow 打开的 tab 为零，那就直接打开图片
            fileEditorManager.openFile(image, true);
        } else {
            // 4 获取下一个 EditorWindow
            EditorWindow nextWindow = fileEditorManager.getNextWindow(currentWindow);
            if (nextWindow == currentWindow) {
                // 4.1 如果下一个 EditorWindow 还是它自己，表示 IDEA 只打开了一个 EditorWindow
                // 4.1 那则需要创建一个垂直分屏，再打开图片
                currentWindow.split(SwingConstants.VERTICAL, true, image, true);
            } else {
                // 4.2 在下一个 EditorWindow 打开图片
                fileEditorManager.openFileWithProviders(image, true, nextWindow);
            }
        }
        LOG.info("image has been opened");

        // 5. 使打开图片按钮失效，避免重复点击的情况
        notification.expire();
        LOG.info("image has been expired");
    }

}
