package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.ide.DataManager;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

/**
 * OpenImageConsumerTest
 *
 * @author maomao
 * @since 2019-04-17
 */
public class OpenImageConsumerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testAccept() {
        DataManager.getInstance().getDataContextFromFocusAsync()
                .onSuccess(dataContext -> new OpenImageConsumer().accept(dataContext))
                .onError(Throwable::printStackTrace);
    }
}