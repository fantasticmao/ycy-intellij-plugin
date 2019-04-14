package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * {@link YcyImageManager} 实现类
 *
 * @author maomao
 * @since 2019-04-05
 */
public class YcyImageManagerImpl implements YcyImageManager {
    private static final Logger LOG = Logger.getInstance(YcyImageManagerImpl.class);

    /**
     * 默认图片
     */
    private URL defaultImageUrl;

    /**
     * 可配置的图片列表
     *
     * <p>TODO 支持图片的可配置功能</p>
     */
    private List<URL> imageUrlList;

    /**
     * 单例模式
     */
    private static YcyImageManagerImpl instance;

    private YcyImageManagerImpl() {
        this.defaultImageUrl = this.getDefaultUrl();
    }

    /**
     * 单例模式
     *
     * @return {@link YcyImageManagerImpl}
     */
    static YcyImageManagerImpl getInstance() {
        return instance != null ? instance : (instance = new YcyImageManagerImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    public URL getImageUrl() {
        return defaultImageUrl;
    }

    /**
     * 从插件 jar 中获取默认的杨超越图片
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/ycy-intellij-plugin.jar!/images/超越妹妹.jpg"</p>
     */
    private URL getDefaultUrl() {
        PluginId pluginId = PluginId.getId(GlobalConfig.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin == null) {
            LOG.error("fail to get plugin \"" + GlobalConfig.PLUGIN_ID + "\"");
            throw new NullPointerException("fail to get plugin \"" + GlobalConfig.PLUGIN_ID + "\"");
        }
        File pluginPath = plugin.getPath();
        try {
            return new URL("jar:file:" + pluginPath.getPath() + "!/images/超越妹妹.jpg");
        } catch (MalformedURLException e) {
            LOG.error("fail to find the default imageUrl", e);
            throw new RuntimeException("fail to find the default imageUrl", e);
        }
    }
}
