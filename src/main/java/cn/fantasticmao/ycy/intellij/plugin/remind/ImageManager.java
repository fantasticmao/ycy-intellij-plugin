package cn.fantasticmao.ycy.intellij.plugin.remind;

import javax.annotation.Nonnull;
import java.net.URL;

/**
 * 图片管理器
 *
 * @author maomao
 * @since 2019-04-05
 */
public interface ImageManager {

    /**
     * 单例模式
     *
     * @return {@link ImageManager}
     */
    static ImageManager getInstance() {
        return ImageManagerImpl.getInstance();
    }

    /**
     * 获取即将用于展示的图片
     *
     * <p>URL 仅支持 "file" 和 "jar" 两种协议</p>
     *
     * @return {@link java.net.URL}
     */
    @Nonnull
    URL getImageUrl();
}
