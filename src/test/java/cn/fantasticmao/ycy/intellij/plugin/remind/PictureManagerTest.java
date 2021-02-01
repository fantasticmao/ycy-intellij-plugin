package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * PictureManagerTest
 *
 * @author maomao
 * @since 2019-04-17
 */
public class PictureManagerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testGetInstance() {
        PictureManager pictureManager = PictureManager.getInstance();
        assertNotNull(pictureManager);
    }

    public void testGetDefaultPictureUrlList() throws MalformedURLException {
        List<URL> urlList = PictureManager.getInstance().getDefaultPictureUrlList();
        assertNotEmpty(urlList);
        System.out.println("testGetDefaultPictureUrlList: " + urlList);
    }

    public void testGetDefaultPictureList() throws MalformedURLException {
        List<String> pictureList = PictureManager.getInstance().getDefaultPictureList();
        assertNotEmpty(pictureList);
        System.out.println("testGetDefaultPictureList: " + pictureList);
    }

}