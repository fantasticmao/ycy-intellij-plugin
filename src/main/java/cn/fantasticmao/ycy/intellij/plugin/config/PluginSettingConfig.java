package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
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
     * 文本组件的填充对象
     */
    TextComponentAccessor<JTextField> TEXT_ACCESSOR_WITH_FILE_PROTOCOL = new TextComponentAccessor<JTextField>() {
        @Override
        public String getText(JTextField textField) {
            return textField.getText();
        }

        @Override
        public void setText(JTextField textField, @NotNull String text) {
            textField.setText("file://" + text);
        }
    };
}
