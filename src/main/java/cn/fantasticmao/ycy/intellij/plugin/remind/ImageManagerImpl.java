package cn.fantasticmao.ycy.intellij.plugin.remind;

import cn.fantasticmao.ycy.intellij.plugin.GlobalConfig;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link ImageManager} 实现类
 *
 * @author maomao
 * @version 1.0
 * @since 2019-04-05
 */
public class ImageManagerImpl implements ImageManager {
    private static final Logger LOG = Logger.getInstance(ImageManagerImpl.class);

    /**
     * 默认图片列表
     */
    private final List<URL> defaultImageUrlList;

    /**
     * 单例模式
     */
    private static ImageManagerImpl instance;

    private ImageManagerImpl() {
        // 避免修改原始的默认值
        this.defaultImageUrlList = Collections.unmodifiableList(this.init());
    }

    /**
     * 单例模式
     *
     * @return {@link ImageManagerImpl}
     */
    static ImageManagerImpl getInstance() {
        return instance != null ? instance : (instance = new ImageManagerImpl());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<URL> getDefaultImageUrlList() {
        return this.defaultImageUrlList;
    }

    /**
     * 从插件 jar 中获取默认图片列表
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/ycy-intellij-plugin.jar!/images/1.jpg"</p>
     */
    private List<URL> init() {
        PluginId pluginId = PluginId.getId(GlobalConfig.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin == null) {
            LOG.error("fail to get plugin \"" + GlobalConfig.PLUGIN_ID + "\"");
            throw new NullPointerException("fail to get plugin \"" + GlobalConfig.PLUGIN_ID + "\"");
        }

        File pluginPath = plugin.getPath();
        try {
            final String imageUrlPath = "jar:" + pluginPath.toURI().toURL().toString() + "!/images/%d.jpg";
            List<URL> defaultImageUrlList = new ArrayList<>(10);
            for (int i = 1; i <= 10; i++) {
                URL imageUrl = new URL(String.format(imageUrlPath, i));
                defaultImageUrlList.add(imageUrl);
            }
            return defaultImageUrlList;
        } catch (MalformedURLException e) {
            LOG.error("fail to get the default image url list", e);
            throw new RuntimeException("fail to get the default imageUrl", e);
        }
    }
}
