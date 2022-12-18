package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.openapi.vfs.VfsUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片管理器
 *
 * @author fantasticmao
 * @version 1.0
 * @since 2019-04-05
 */
public class PictureManager {
    private static final PictureManager INSTANCE = new PictureManager();

    /**
     * 默认图片列表
     */
    private final List<URL> defaultPictureUrlList;

    private PictureManager() {
        List<URL> pictureUrlList = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            // 从插件 jar 中获取默认图片列表，
            // 默认图片地址是 ${pluginPath}/ycy-intellij-plugin.jar!/pictures/1.jpg
            URL pictureUrl = getClass().getResource("/pictures/" + i + ".jpg");
            pictureUrlList.add(pictureUrl);
        }
        this.defaultPictureUrlList = Collections.unmodifiableList(pictureUrlList);
    }

    /**
     * 获取默认图片列表
     *
     * @return {@link java.lang.String}
     * @see com.intellij.openapi.vfs.VfsUtil#convertFromUrl(URL)
     * @see com.intellij.openapi.vfs.VirtualFileManager#findFileByUrl(String)
     */
    public static List<String> getDefaultPictureList() {
        return INSTANCE.defaultPictureUrlList.stream()
            .map(VfsUtil::convertFromUrl)
            .collect(Collectors.toList());
    }
}
