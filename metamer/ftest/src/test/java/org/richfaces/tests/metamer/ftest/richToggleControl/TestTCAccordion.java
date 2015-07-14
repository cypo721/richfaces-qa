/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2015, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.richToggleControl;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/richToggleControl/accordion.xhtml
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision: 21307 $
 */
public class TestTCAccordion extends AbstractTestToggleControl {

    private WebElement[] items1;
    private WebElement[] items2;

    @Override
    public String getComponentTestPagePath() {
        return "richToggleControl/accordion.xhtml";
    }

    private WebElement[] getItems1() {
        if (items1 == null) {
            items1 = new WebElement[] { getPage().getPanel1Item1Content(), getPage().getPanel1Item2Content(), getPage().getPanel1Item3Content() };
        }
        return items1;
    }

    private WebElement[] getItems2() {
        if (items2 == null) {
            items2 = new WebElement[] { getPage().getPanel2Item1Content(), getPage().getPanel2Item2Content(), getPage().getPanel2Item3Content() };
        }
        return items2;
    }

    @Test
    public void testDisableDefault() {
        super.testDisableDefault();
    }

    @Test
    public void testSwitchFirstPanel() {
        testSwitchFirstPanel(getItems1());
    }

    @Test
    public void testSwitchSecondPanel() {
        testSwitchSecondPanel(getItems2());
    }

    @Test
    public void testTargetItem() {
        testTargetItem(getItems1());
    }

    @Test
    public void testTargetPanel() {
        testTargetPanel(getItems2());
    }
}
