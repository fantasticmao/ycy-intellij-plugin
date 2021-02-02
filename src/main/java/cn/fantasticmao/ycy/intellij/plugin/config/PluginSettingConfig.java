package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;

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
    List<String> PICTURE_EXTENSION_LIST = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");

    /**
     * 图片选择器的描述
     */
    FileChooserDescriptor PICTURE_FILE_CHOOSER = new FileChooserDescriptor(true, false, false, false, false, true) {
        @Override
        public void validateSelectedFiles(VirtualFile[] files) throws Exception {
            super.validateSelectedFiles(files);
            for (VirtualFile file : files) {
                if (!PICTURE_EXTENSION_LIST.contains(file.getExtension())) {
                    String delimiter = I18nBundle.message(I18nBundle.Key.SYMBOL_DELIMITER);
                    String message = I18nBundle.message(I18nBundle.Key.ERROR_PICTURE_FORMAT,
                        String.join(delimiter, PICTURE_EXTENSION_LIST));
                    throw new IllegalArgumentException(message);
                }
            }
        }
    };

}
