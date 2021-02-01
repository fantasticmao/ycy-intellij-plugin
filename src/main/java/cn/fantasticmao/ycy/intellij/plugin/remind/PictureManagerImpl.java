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
 * {@link PictureManager} 实现类
 *
 * @author maomao
 * @version 1.0
 * @since 2019-04-05
 */
public class PictureManagerImpl implements PictureManager {
    private static final Logger LOG = Logger.getInstance(PictureManagerImpl.class);

    /**
     * 默认图片列表
     */
    private final List<URL> defaultPictureUrlList;

    /**
     * 单例模式
     */
    private static PictureManagerImpl instance;

    private PictureManagerImpl() {
        // 避免修改原始的默认值
        this.defaultPictureUrlList = Collections.unmodifiableList(this.init());
    }

    /**
     * 单例模式
     *
     * @return {@link PictureManagerImpl}
     */
    static PictureManagerImpl getInstance() {
        return instance != null ? instance : (instance = new PictureManagerImpl());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<URL> getDefaultPictureUrlList() {
        return this.defaultPictureUrlList;
    }

    /**
     * 从插件 jar 中获取默认图片列表
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/ycy-intellij-plugin.jar!/pictures/1.jpg"</p>
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
            List<URL> defaultPictureUrlList = new ArrayList<>(10);
            for (int i = 1; i <= 10; i++) {
                final String pictureUrlPath = "jar:" + pluginPath.toURI().toURL().toString() + "!/pictures/" + i + ".jpg";
                URL pictureUrl = new URL(pictureUrlPath);
                defaultPictureUrlList.add(pictureUrl);
            }
            return defaultPictureUrlList;
        } catch (MalformedURLException e) {
            LOG.error("fail to get default picture url list", e);
            throw new RuntimeException("fail to get default pictures", e);
        }
    }
}
