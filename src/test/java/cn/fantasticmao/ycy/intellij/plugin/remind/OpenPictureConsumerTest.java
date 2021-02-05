package cn.fantasticmao.ycy.intellij.plugin.remind;

import com.intellij.ide.DataManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

/**
 * OpenPictureConsumerTest
 *
 * @author maomao
 * @since 2019-04-17
 */
public class OpenPictureConsumerTest extends BasePlatformTestCase {

    public void testAccept() {
        DataManager.getInstance().getDataContextFromFocusAsync()
            .onSuccess((dataContext -> new OpenPictureConsumer().accept(dataContext)));
    }
}