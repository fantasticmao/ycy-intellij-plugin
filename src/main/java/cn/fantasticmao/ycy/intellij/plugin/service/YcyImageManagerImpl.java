package cn.fantasticmao.ycy.intellij.plugin.service;

import cn.fantasticmao.ycy.intellij.plugin.common.Config;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * YcyImageManagerImpl
 * TODO 支持图片的可配置功能
 *
 * @author maomao
 * @since 2019-04-05
 */
public class YcyImageManagerImpl implements YcyImageManager {
    private URL defaultImageUrl;

    private static YcyImageManagerImpl instance;

    private YcyImageManagerImpl() {
        this.defaultImageUrl = this.getDefaultUrl();
    }

    static YcyImageManagerImpl getInstance() {
        return instance != null ? instance : (instance = new YcyImageManagerImpl());
    }

    @Override
    @Nonnull
    public URL getImageUrl() {
        return defaultImageUrl;
    }

    private URL getDefaultUrl() {
        PluginId pluginId = PluginId.getId(Config.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin == null) {
            throw new NullPointerException("fail to get plugin \"" + Config.PLUGIN_ID + "\"");
        }
        File pluginPath = plugin.getPath();
        Path pluginJarPath = Paths.get(pluginPath.toURI()).resolve("lib").resolve(Config.JAR_NAME);
        try {
            return new URL("jar:" + pluginJarPath.toUri().toString() + "!/images/超越妹妹.jpg");
        } catch (MalformedURLException e) {
            throw new RuntimeException("fail to create default imageUrl", e);
        }
    }
}
