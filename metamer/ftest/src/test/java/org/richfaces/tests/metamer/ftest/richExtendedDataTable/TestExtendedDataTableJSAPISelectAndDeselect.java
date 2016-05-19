///*
// * JBoss, Home of Professional Open Source
// * Copyright 2010-2016, Red Hat, Inc. and individual contributors
// * by the @authors tag. See the copyright.txt in the distribution for a
// * full listing of individual contributors.
// *
// * This is free software; you can redistribute it and/or modify it
// * under the terms of the GNU Lesser General Public License as
// * published by the Free Software Foundation; either version 2.1 of
// * the License, or (at your option) any later version.
// *
// * This software is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// * Lesser General Public License for more details.
// *
// * You should have received a copy of the GNU Lesser General Public
// * License along with this software; if not, write to the Free
// * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
// * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
// */
//package org.richfaces.tests.metamer.ftest.richExtendedDataTable;
//
//import static org.testng.Assert.assertEquals;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.richfaces.tests.metamer.ftest.abstractions.DataTableSortingTest;
//import org.richfaces.tests.metamer.ftest.richExtendedDataTable.fragment.SortingEDT;
//import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
//import org.testng.annotations.Test;
//
///**
// * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
// */
//public class TestExtendedDataTableJSAPISelectAndDeselect extends DataTableSortingTest {
//
//    private final Attributes<ExtendedDataTableAttributes> attributes = getAttributes();
//
//    @FindBy(css = "[id$=selectRowsIndex]")
//    private WebElement selectRowsIndexButton;
//    @FindBy(css = "[id$=selectRowsRange]")
//    private WebElement selectRowsRangeButton;
//    @FindBy(css = "[id$=deselectRow]")
//    private WebElement deselectRowButton;
//    @FindBy(css = "div.rf-edt[id$=richEDT]")
//    private SortingEDT table;
//
//    @Override
//    public String getComponentTestPagePath() {
//        return "richExtendedDataTable/selection.xhtml";
//    }
//
//    @Override
//    protected SortingEDT getTable() {
//        return table;
//    }
//
//    @Test
//    public void testSelectRowsAndDeselectRow() {
//        assertEquals(table.getAllRows().get(0).)
//    }
//}
