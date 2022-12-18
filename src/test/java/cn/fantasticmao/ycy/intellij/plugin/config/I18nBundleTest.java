package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

/**
 * I18nBundleTest
 *
 * @author fantasticmao
 * @since 2021-02-06
 */
public class I18nBundleTest extends BasePlatformTestCase {

    public void testMessage() {
        String pluginName = I18nBundle.message(I18nBundle.Key.PLUGIN_NAME);
        assertNotNull(pluginName);
        String expected = "Programmer Motivator: Chaoyue Yang";
        assertEquals(expected, pluginName);
    }
}
