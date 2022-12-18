package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

/**
 * PictureManagerTest
 *
 * @author fantasticmao
 * @since 2019-04-17
 */
public class PictureManagerTest extends BasePlatformTestCase {

    public void testGetDefaultPictureList() {
        List<String> pictureList = PictureManager.getDefaultPictureList();
        assertNotEmpty(pictureList);
        assertEquals(10, pictureList.size());
    }

}