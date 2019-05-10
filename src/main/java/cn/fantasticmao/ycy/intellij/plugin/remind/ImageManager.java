package cn.fantasticmao.ycy.intellij.plugin.remind;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片管理器
 *
 * @author maomao
 * @version 1.0
 * @since 2019-04-05
 */
public interface ImageManager {

    /**
     * 单例模式
     *
     * @return {@link ImageManagerImpl}
     */
    static ImageManager getInstance() {
        return ImageManagerImpl.getInstance();
    }

    /**
     * 获取默认图片列表
     *
     * @return {@link java.net.URL}
     */
    List<URL> getDefaultImageUrlList();

    /**
     * 获取默认图片列表
     *
     * @return {@link java.lang.String}
     */
    default List<String> getDefaultImageList() {
        return this.getDefaultImageUrlList().stream()
                .map(URL::toString)
                .collect(Collectors.toList());
    }
}
