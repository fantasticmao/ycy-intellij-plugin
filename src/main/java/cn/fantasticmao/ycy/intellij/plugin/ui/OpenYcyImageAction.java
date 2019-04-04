package cn.fantasticmao.ycy.intellij.plugin.ui;

import cn.fantasticmao.ycy.intellij.plugin.common.NotifyConfig;
import cn.fantasticmao.ycy.intellij.plugin.service.YcyImageManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.net.URL;

/**
 * OpenYcyImageAction
 *
 * @author maomao
 * @since 2019-04-04
 */
class OpenYcyImageAction extends AnAction {

    OpenYcyImageAction() {
        super(NotifyConfig.getInstance().getActionText());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getData(PlatformDataKeys.PROJECT);
        if (currentProject == null) {
            return;
        }

        URL ycyImageUrl = YcyImageManager.getInstance().getImageUrl();
        VirtualFile ycyImage = VfsUtil.findFileByURL(ycyImageUrl);
        if (ycyImage == null) {
            return;
        }

        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(currentProject);
        EditorWindow currentWindow = fileEditorManager.getCurrentWindow();
        if (currentWindow == null || currentWindow.getTabCount() == 0) {
            fileEditorManager.openFile(ycyImage, true);
            return;
        }

        EditorWindow nextWindow = fileEditorManager.getNextWindow(currentWindow);
        if (nextWindow == currentWindow) { // 下一个 EditorWindow 还是它自己，表示 IDEA 只打开了一个 EditorWindow
            currentWindow.split(SwingConstants.VERTICAL, true, ycyImage, true);
        } else { // 从下一个 EditorWindow 打开 ycyImage
            fileEditorManager.openFileWithProviders(ycyImage, true, nextWindow);
        }
    }

}
