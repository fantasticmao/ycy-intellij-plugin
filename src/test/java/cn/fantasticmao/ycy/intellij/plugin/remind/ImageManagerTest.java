package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * ImageManagerTest
 *
 * @author maomao
 * @since 2019-04-17
 */
public class ImageManagerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testGetInstance() {
        ImageManager imageManager = ImageManager.getInstance();
        assertNotNull(imageManager);
    }

    public void testGetDefaultImageUrlList() throws MalformedURLException {
        List<URL> urlList = ImageManager.getInstance().getDefaultImageUrlList();
        assertNotEmpty(urlList);
        System.out.println("testGetDefaultImageUrlList: " + urlList);
    }

    public void testGetDefaultImageList() throws MalformedURLException {
        List<String> imageList = ImageManager.getInstance().getDefaultImageList();
        assertNotEmpty(imageList);
        System.out.println("testGetDefaultImageList: " + imageList);
    }

}