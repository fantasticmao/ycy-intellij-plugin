package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.config.ConfigService;
import cn.fantasticmao.ycy.intellij.plugin.config.ConfigState;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import javax.swing.*;
import java.util.function.Consumer;

/**
 * 打开图片的具体实现
 *
 * @author maomao
 * @version 1.2
 * @since 2019-04-17
 */
public class OpenPictureConsumer implements Consumer<DataContext> {
    private static final Logger LOG = Logger.getInstance(OpenPictureConsumer.class);

    public OpenPictureConsumer() {
    }

    @Override
    public void accept(DataContext dataContext) {
        // 1. 获取 IDEA 正在使用的 Project
        Project currentProject = dataContext.getData(PlatformDataKeys.PROJECT);
        if (currentProject == null) {
            LOG.warn("currentProject cannot be null");
            return;
        }

        // 2. 获取即将用于展示的图片
        ConfigState configState = ConfigService.getInstance().getState();
        String pictureUrl = configState.getRandomRemindPicture();
        VirtualFile picture = VirtualFileManager.getInstance().findFileByUrl(pictureUrl);
        if (picture == null) {
            LOG.error("find picture by URL error: " + pictureUrl);
            return;
        }

        // 3. 获取当前 Project 中，正在使用的 EditorWindow
        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(currentProject);
        EditorWindow currentWindow = fileEditorManager.getCurrentWindow();
        if (currentWindow == null || currentWindow.getTabCount() == 0) {
            // 3.1 如果没有打开 EditorWindow 或者 EditorWindow 打开的 tab 为零，那就直接打开图片
            fileEditorManager.openFile(picture, true);
        } else {
            // 4 获取下一个 EditorWindow
            EditorWindow nextWindow = fileEditorManager.getNextWindow(currentWindow);
            if (nextWindow == currentWindow) {
                // 4.1 如果下一个 EditorWindow 还是它自己，表示 IDEA 只打开了一个 EditorWindow
                // 4.1 那则需要创建一个垂直分屏，再打开图片
                currentWindow.split(SwingConstants.VERTICAL, true, picture, true);
            } else {
                // 4.2 在下一个 EditorWindow 打开图片
                fileEditorManager.openFileWithProviders(picture, true, nextWindow);
            }
        }
        LOG.info("the picture has been shown");
    }
}
