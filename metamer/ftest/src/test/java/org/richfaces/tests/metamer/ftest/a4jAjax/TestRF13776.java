/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2016, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.a4jAjax;

import static org.testng.Assert.assertEquals;

import java.util.List;

import javax.faces.event.PhaseId;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.common.CheckboxInputComponentImpl;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestRF13776 extends AbstractWebDriverTest {

    @FindBy(css = "[id$='treeNodeSelectBox']")
    private List<CheckboxInputComponentImpl> checkboxes;

    @Override
    public String getComponentTestPagePath() {
        return "a4jAjax/rf-13776.xhtml";
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-13776")
    public void testListenerIsInvoked() {
        assertEquals(checkboxes.size(), 4);
        for (CheckboxInputComponentImpl checkbox : checkboxes) {
            Graphene.guardAjax(checkbox).check();
            getMetamerPage().assertListener(PhaseId.INVOKE_APPLICATION, "* listener invoked");
        }
    }
}
