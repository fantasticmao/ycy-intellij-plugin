package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.ComponentWithBrowseButton.BrowseFolderActionListener;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

/**
 * 插件设置页面的配置参数
 *
 * @author maomao
 * @version 1.2
 * @since 2019-04-18
 */
public interface PluginSettingConfig {

    /**
     * 支持的图片格式
     */
    List<String> IMAGE_EXTENSION_LIST = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");

    /**
     * 支持的图片格式的拼接字符串，用于弹窗提示
     */
    String IMAGE_EXTENSION_LIST_STR = String.join("、", IMAGE_EXTENSION_LIST);

    /**
     * 图片选择器的描述对象
     */
    FileChooserDescriptor IMAGE_FILE_CHOOSER = new FileChooserDescriptor(true, false, false, false, false, false) {
        @Override
        public void validateSelectedFiles(VirtualFile[] files) throws Exception {
            super.validateSelectedFiles(files);
            for (VirtualFile file : files) {
                if (!IMAGE_EXTENSION_LIST.contains(file.getExtension())) {
                    throw new IllegalArgumentException("请确保上传的是 " + IMAGE_EXTENSION_LIST_STR + " 文件，然后重试");
                }
            }
        }
    };

    /**
     * 图片选择器的监听事件
     */
    static BrowseFolderActionListener newBrowseFolderActionListener(TextFieldWithBrowseButton textField) {
        return new BrowseFolderActionListener<JTextField>("图片 URL", null, textField, null,
                PluginSettingConfig.IMAGE_FILE_CHOOSER, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) {
            @NotNull
            @Override
            protected String chosenFileToResultingText(@NotNull VirtualFile chosenFile) {
                try {
                    return VfsUtil.toUri(chosenFile).toURL().toString(); // 选择图片时，返回文件完整的 URL 而不仅仅是 Path
                } catch (MalformedURLException e) {
                    return DefaultConfig.REMIND_IMAGE_URL;
                }
            }
        };
    }
}
