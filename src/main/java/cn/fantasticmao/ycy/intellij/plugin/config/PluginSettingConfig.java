package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

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
    FileChooserDescriptor PICTURE_FILE_CHOOSER_DESCRIPTOR = new PictureChooserDescriptor();

    class PictureChooserDescriptor extends FileChooserDescriptor {

        public PictureChooserDescriptor() {
            super(true, false, false, false, false, true);
            super.withFileFilter(file ->
                file.getExtension() != null && PICTURE_EXTENSION_LIST.contains(file.getExtension().toLowerCase())
            ).withTitle(I18nBundle.message(I18nBundle.Key.PICTURE_CHOOSER_TITLE));
        }

        @Override
        public void validateSelectedFiles(@NotNull VirtualFile[] files) throws Exception {
            super.validateSelectedFiles(files);
            for (VirtualFile file : files) {
                if (file.getExtension() == null || !PICTURE_EXTENSION_LIST.contains(file.getExtension().toLowerCase())) {
                    String delimiter = I18nBundle.message(I18nBundle.Key.SYMBOL_DELIMITER);
                    String message = I18nBundle.message(I18nBundle.Key.PICTURE_CHOOSER_ERROR_FORMAT,
                        String.join(delimiter, PICTURE_EXTENSION_LIST));
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

}
