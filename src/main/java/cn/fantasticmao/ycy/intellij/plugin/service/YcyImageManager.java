package cn.fantasticmao.ycy.intellij.plugin.service;

import javax.annotation.Nonnull;
import java.net.URL;

/**
 * YcyImageManager
 *
 * @author maomao
 * @since 2019-04-05
 */
public interface YcyImageManager {

    static YcyImageManager getInstance() {
        return YcyImageManagerImpl.getInstance();
    }

    @Nonnull
    URL getImageUrl();
}
