/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2014, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.tests.metamer.ftest.a4jCommandLink;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import javax.faces.event.PhaseId;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Action;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.a4jCommandButton.CommandButtonLinkPage;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage.WaitRequestType;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/a4jCommandLink/simple.xhtml
 *
 * @author <a href="https://community.jboss.org/people/ppitonak">Pavol Pitonak</a>
 * @since 4.3.0.M2
 */
public class TestCommandLink extends AbstractWebDriverTest {

    private final Attributes<CommandLinkAttributes> commandLinkAttributes = getAttributes();

    @Page
    private CommandButtonLinkPage page;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jCommandLink/simple.xhtml");
    }

    @Test(groups = "client-side-perf")
    public void testSimpleClick() {
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);

        page.verifyOutput1Text();
        page.verifyOutput2Text();
        page.verifyOutput3Text();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-9665")
    public void testSimpleClickUnicode() {
        page.typeToInput(CommandButtonLinkPage.STRING_UNICODE1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_UNICODE1);

        page.verifyOutput1Text(CommandButtonLinkPage.STRING_UNICODE1);
        page.verifyOutput2Text(CommandButtonLinkPage.STRING_UNICODE2);
        page.verifyOutput3Text(CommandButtonLinkPage.STRING_UNICODE3);
    }

    @Test
    public void testAction() {
        commandLinkAttributes.set(CommandLinkAttributes.action, "doubleStringAction");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);
        page.verifyOutput2Text(CommandButtonLinkPage.STRING_RF1_X2);

        commandLinkAttributes.set(CommandLinkAttributes.action, "first6CharsAction");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1 + "ě");
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1 + "ě");
        page.verifyOutput2Text(CommandButtonLinkPage.STRING_RF2);

        commandLinkAttributes.set(CommandLinkAttributes.action, "toUpperCaseAction");
        page.typeToInput(CommandButtonLinkPage.STRING_RF_UNICODE);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF_UNICODE);
        page.verifyOutput2Text(CommandButtonLinkPage.STRING_RF_UNICODE_UPPERCASE);
    }

    @Test
    public void testActionListener() {
        commandLinkAttributes.set(CommandLinkAttributes.actionListener, "doubleStringActionListener");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);
        page.verifyOutput3Text(CommandButtonLinkPage.STRING_RF1_X2);

        commandLinkAttributes.set(CommandLinkAttributes.actionListener, "first6CharsActionListener");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1 + "ě");
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1 + "ě");
        page.verifyOutput3Text(CommandButtonLinkPage.STRING_RF2);

        commandLinkAttributes.set(CommandLinkAttributes.actionListener, "toUpperCaseActionListener");
        page.typeToInput(CommandButtonLinkPage.STRING_RF_UNICODE);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF_UNICODE);
        page.verifyOutput3Text(CommandButtonLinkPage.STRING_RF_UNICODE_UPPERCASE);
    }

    @Test
    public void testBypassUpdates() {
        commandLinkAttributes.set(CommandLinkAttributes.bypassUpdates, true);
        MetamerPage.waitRequest(page.getLinkElement(), WaitRequestType.XHR).click();
        page.verifyOutput1Text("");
        page.verifyOutput2Text("");
        page.verifyOutput3Text("");
        page.assertPhases(PhaseId.RESTORE_VIEW, PhaseId.APPLY_REQUEST_VALUES, PhaseId.PROCESS_VALIDATIONS, PhaseId.RENDER_RESPONSE);
        page.assertListener(PhaseId.PROCESS_VALIDATIONS, CommandButtonLinkPage.STRING_ACTIONLISTENER_MSG);
        page.assertListener(PhaseId.PROCESS_VALIDATIONS, CommandButtonLinkPage.STRING_ACTION_MSG);
    }

    @Test
    @Templates(value = "plain")
    public void testCharset() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.charset, "utf-8");
    }

    @Test
    @Templates(value = "plain")
    public void testCoords() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.coords, "circle: 150, 60, 60");
    }

    @Test
    public void testData() {
        testData(new Action() {
            @Override
            public void perform() {
                //Does not matter what we type here
                page.typeToInput(CommandButtonLinkPage.STRING_RF1);
                page.submitByLink();
                page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);
            }
        });
    }

    @Test
    public void testDisabled() {
        commandLinkAttributes.set(CommandLinkAttributes.disabled, true);

        Graphene.waitModel().until("Link should not be on page.").element(page.getLinkElement()).is().not().present();
        assertTrue(page.getDisabledLinkElement().isDisplayed(), "Link should be disabled.");
    }

    @Test
    public void testExecute() {
        commandLinkAttributes.set(CommandLinkAttributes.execute, "input executeChecker");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);
        page.assertListener(PhaseId.UPDATE_MODEL_VALUES, MetamerPage.STRING_EXECUTE_CHECKER_MSG);
    }

    @Test
    @Templates(value = "plain")
    public void testHreflang() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.hreflang, "sk");
    }

    @Test
    public void testImmediate() {
        commandLinkAttributes.set(CommandLinkAttributes.immediate, true);

        String reqTime = page.getRequestTimeElement().getText();
        page.submitByLink();
        Graphene.waitModel().until("Page was not updated").element(page.getRequestTimeElement()).text().not().equalTo(reqTime);

        page.verifyOutput1Text("");
        page.verifyOutput2Text("");
        page.verifyOutput3Text("");

        page.assertPhases(PhaseId.RESTORE_VIEW, PhaseId.APPLY_REQUEST_VALUES, PhaseId.RENDER_RESPONSE);
        page.assertListener(PhaseId.APPLY_REQUEST_VALUES, MetamerPage.STRING_ACTIONLISTENER_MSG);
        page.assertListener(PhaseId.APPLY_REQUEST_VALUES, MetamerPage.STRING_ACTION_MSG);
    }

    @Test
    public void testLimitRender() {
        commandLinkAttributes.set(CommandLinkAttributes.limitRender, true);
        commandLinkAttributes.set(CommandLinkAttributes.render, "output1 requestTime");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);
        page.verifyOutput1Text(CommandButtonLinkPage.STRING_RF1);
        page.verifyOutput2Text("");
        page.verifyOutput3Text("");
    }

    @Test
    public void testEvents() {
        commandLinkAttributes.set(CommandLinkAttributes.onbegin, "metamerEvents += \"begin \"");
        commandLinkAttributes.set(CommandLinkAttributes.onbeforedomupdate, "metamerEvents += \"beforedomupdate \"");
        commandLinkAttributes.set(CommandLinkAttributes.oncomplete, "metamerEvents += \"complete \"");

        ((JavascriptExecutor) driver).executeScript("metamerEvents = \"\"");
        String reqTime = page.getRequestTimeElement().getText();
        page.submitByLink();
        Graphene.waitModel().until("Page was not updated").element(page.getRequestTimeElement()).text().not().equalTo(reqTime);

        String[] events = ((JavascriptExecutor) driver).executeScript("return metamerEvents").toString().split(" ");

        assertEquals(events.length, 3, "3 events should be fired.");
        assertEquals(events[0], "begin", "Attribute onbegin doesn't work");
        assertEquals(events[1], "beforedomupdate", "Attribute onbeforedomupdate doesn't work");
        assertEquals(events[2], "complete", "Attribute oncomplete doesn't work");
    }

    @Test
    public void testOnbegin() {
        testFireEvent(commandLinkAttributes, CommandLinkAttributes.onbegin, new Action() {
            @Override
            public void perform() {
                page.submitByLink();
            }
        });
    }

    @Test
    public void testOnbeforedomupdate() {
        testFireEvent(commandLinkAttributes, CommandLinkAttributes.onbeforedomupdate, new Action() {
            @Override
            public void perform() {
                page.submitByLink();
            }
        });
    }

    @Test
    public void testOncomplete() {
        testFireEvent(commandLinkAttributes, CommandLinkAttributes.oncomplete, new Action() {
            @Override
            public void perform() {
                page.submitByLink();
            }
        });
    }

    @Test
    @Templates(value = "plain")
    public void testOnclick() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onclick);
    }

    @Test
    @Templates(value = "plain")
    public void testOndblclick() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.ondblclick);
    }

    @Test
    @Templates(value = "plain")
    public void testOnkeydown() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onkeydown);
    }

    @Test
    @Templates(value = "plain")
    public void testOnkeypress() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onkeypress);
    }

    @Test
    @Templates(value = "plain")
    public void testOneyup() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onkeyup);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmousedown() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onmousedown);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmousemove() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onmousemove);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmouseout() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onmouseout);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmouseover() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onmouseover);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmouseup() {
        testFireEventWithJS(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.onmouseup);
    }

    @Test
    @Templates(value = "plain")
    public void testRel() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.rel, "metamer");
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-10555")
    public void testRender() {
        commandLinkAttributes.set(CommandLinkAttributes.action, "doubleStringAction");
        commandLinkAttributes.set(CommandLinkAttributes.actionListener, "doubleStringActionListener");

        commandLinkAttributes.set(CommandLinkAttributes.render, "output1");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput1Changes(CommandButtonLinkPage.STRING_RF1);
        page.verifyOutput1Text(CommandButtonLinkPage.STRING_RF1);
        page.verifyOutput2Text("");
        page.verifyOutput3Text("");

        page.typeToInput("");
        page.submitByLink();
        page.waitUntilOutput1Changes("");

        commandLinkAttributes.set(CommandLinkAttributes.render, "output2 output3");
        page.typeToInput(CommandButtonLinkPage.STRING_RF1);
        page.submitByLink();
        page.waitUntilOutput2ChangesToText(CommandButtonLinkPage.STRING_RF1_X2);
        page.verifyOutput1Text("");
        page.verifyOutput2Text(CommandButtonLinkPage.STRING_RF1_X2);
        page.verifyOutput3Text(CommandButtonLinkPage.STRING_RF1_X2);
    }

    @Test
    @Templates(value = "plain")
    public void testRendered() {
        commandLinkAttributes.set(CommandLinkAttributes.rendered, false);

        Graphene.waitModel().until("Link should not be on page.").element(page.getLinkElement()).is().not().present();
    }

    @Test
    @Templates(value = "plain")
    public void testRev() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.rev, "metamer");
    }

    @Test
    @Templates(value = "plain")
    public void testShape() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.shape, "default");
    }

    @Test
    @Templates(value = "plain")
    public void testStyle() {
        testStyle(page.getLinkElement());
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-9307")
    @Templates(value = "plain")
    public void testStyleClass() {
        testStyleClass(page.getLinkElement());
    }

    @Test
    @Templates(value = "plain")
    public void testTitle() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.title, "metamer");
    }

    @Test
    @Templates(value = "plain")
    public void testType() {
        testHTMLAttribute(page.getLinkElement(), commandLinkAttributes, CommandLinkAttributes.type, "default");
    }

    @Test
    @Templates("plain")
    public void testValue() {
        commandLinkAttributes.set(CommandLinkAttributes.value, "new label");
        assertEquals(page.getLinkElement().getText(), "new label", "Value of the button did not change");
    }
}
