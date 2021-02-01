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
     * 支持的图片格式的拼接字符串，用于弹窗提示
     */
    String PICTURE_EXTENSION_LIST_STR = String.join(",", PICTURE_EXTENSION_LIST);

    /**
     * 图片选择器的描述对象
     */
    FileChooserDescriptor PICTURE_FILE_CHOOSER = new FileChooserDescriptor(true, false, false, false, false, true) {
        @Override
        public void validateSelectedFiles(VirtualFile[] files) throws Exception {
            super.validateSelectedFiles(files);
            for (VirtualFile file : files) {
                if (!PICTURE_EXTENSION_LIST.contains(file.getExtension())) {
                    throw new IllegalArgumentException("请确认选择的是 " + PICTURE_EXTENSION_LIST_STR + " 图片，然后重试");
                }
            }
        }
    };

}
