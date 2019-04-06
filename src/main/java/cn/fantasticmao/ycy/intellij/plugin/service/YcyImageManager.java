package cn.fantasticmao.ycy.intellij.plugin.service;

import javax.annotation.Nonnull;
import java.net.URL;

/**
 * 杨超越图片管理器
 *
 * @author maomao
 * @since 2019-04-05
 */
public interface YcyImageManager {

    /**
     * 单例模式
     *
     * @return {@link YcyImageManager}
     */
    static YcyImageManager getInstance() {
        return YcyImageManagerImpl.getInstance();
    }

    /**
     * 获取即将用于展示的杨超越图片
     *
     * <p>URL 仅支持 "file" 和 "jar" 两种协议</p>
     *
     * @return {@link java.net.URL}
     */
    @Nonnull
    URL getImageUrl();
}
