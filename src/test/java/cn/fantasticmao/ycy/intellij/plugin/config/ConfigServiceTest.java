package cn.fantasticmao.ycy.intellij.plugin.config;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

/**
 * ConfigServiceTest
 *
 * @author fantasticmao
 * @since 2021-02-06
 */
public class ConfigServiceTest extends BasePlatformTestCase {

    public void testGetState() {
        ConfigState state = ConfigService.getInstance().getState();
        assertNotNull(state);
        Integer expected = DefaultConfig.REMIND_MODE;
        assertEquals(expected, state.getRemindMode());
    }
}
