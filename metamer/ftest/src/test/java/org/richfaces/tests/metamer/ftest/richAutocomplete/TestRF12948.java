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
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import static org.testng.Assert.assertEquals;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.autocomplete.RichFacesAutocomplete;
import org.richfaces.fragment.autocomplete.SelectOrConfirm;
import org.richfaces.fragment.common.picker.ChoicePickerHelper;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestRF12948 extends AbstractWebDriverTest {

    @FindBy(css = ".rf-au[id$=autocomplete]")
    private RichFacesAutocomplete autocomplete;
    @FindBy(css = "[id$=output]")
    private WebElement output;

    protected void checkOutput(String expected) {
        Graphene.waitAjax().until().element(output).text().equalTo(expected);
    }

    @Override
    public String getComponentTestPagePath() {
        return "richAutocomplete/rf-12948.xhtml";
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12948")
    public void testAutocompleteWithNestedPlaceholderDisplaysVisibleSuggestions() {
        assertEquals(autocomplete.advanced().getInput().getStringValue(), "...type here your favorite state...",
            "Autocomplete should contain placeholder.");

        // focus to hide the placeholder
        autocomplete.advanced().getInput().advanced().getInputElement().click();
        assertEquals(autocomplete.advanced().getInput().getStringValue(), "",
            "Autocomplete should not contain placeholder after the input is focused.");

        SelectOrConfirm typed = Graphene.guardAjax(autocomplete).type("a");
        // select by visible text -- this will fail before RF 4.5.12
        Graphene.guardAjax(typed).select(ChoicePickerHelper.byVisibleText().endsWith("na"));
        checkOutput("Arizona");

        assertEquals(autocomplete.advanced().getInput().getStringValue(), "Arizona");
    }
}
