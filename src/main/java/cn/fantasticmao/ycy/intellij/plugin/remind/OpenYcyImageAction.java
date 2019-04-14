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
 * 打开杨超越图片 Action
 *
 * @author maomao
 * @since 2019-04-04
 */
class OpenYcyImageAction extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenYcyImageAction.class);

    private final Notification notification;

    OpenYcyImageAction(String text, Notification notification) {
        super(text);
        this.notification = notification;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // FIXME 2017.1 版本无法打开图片问题 https://github.com/FantasticMao/ycy-intellij-plugin/issues/9
        // 1. 获取 IDEA 正在使用的 Project
        Project currentProject = event.getData(PlatformDataKeys.PROJECT);
        if (currentProject == null) {
            LOG.warn("no Project is active");
            return;
        }

        // 2. 获取即将用于展示的杨超越图片
        URL ycyImageUrl = YcyImageManager.getInstance().getImageUrl();
        VirtualFile ycyImage = VfsUtil.findFileByURL(ycyImageUrl);
        if (ycyImage == null) {
            LOG.error("ycyImage cannot be null, URL: " + ycyImageUrl.toString());
            return;
        }

        // 3. 获取当前 Project 中，正在使用的 EditorWindow
        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(currentProject);
        EditorWindow currentWindow = fileEditorManager.getCurrentWindow();
        if (currentWindow == null || currentWindow.getTabCount() == 0) {
            // 3.1 如果没有打开 EditorWindow 或者 EditorWindow 打开的 tab 为零，那就直接打开杨超越图片
            fileEditorManager.openFile(ycyImage, true);
        } else {
            // 4 获取下一个 EditorWindow
            EditorWindow nextWindow = fileEditorManager.getNextWindow(currentWindow);
            if (nextWindow == currentWindow) {
                // 4.1 如果下一个 EditorWindow 还是它自己，表示 IDEA 只打开了一个 EditorWindow
                // 4.1 那则需要创建一个垂直分屏，再打开杨超越图片
                currentWindow.split(SwingConstants.VERTICAL, true, ycyImage, true);
            } else {
                // 4.2 在下一个 EditorWindow 打开杨超越图片
                fileEditorManager.openFileWithProviders(ycyImage, true, nextWindow);
            }
        }
        LOG.info("ycyImage has been opened");

        // 5. 使「打开杨超越图片 Action」失效，避免重复点击的情况
        notification.expire();
        LOG.info("ycyImage has been expired");
    }

}
