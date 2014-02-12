/*******************************************************************************
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
 *******************************************************************************/
package org.richfaces.tests.showcase.outputPanel.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class SimplePage {

    @FindBy(xpath = "//*[@class='example-cnt']//span[contains(text(), 'text1')]")
    public WebElement firstError;
    @FindBy(xpath = "//*[@class='example-cnt']//input[@type='text'][contains(@name, 'text1')]")
    public WebElement firstInput;
    @FindBy(xpath = "//*[@class='example-cnt']//span[contains(@id, 'out1')]")
    public WebElement firstOutput;
    @FindBy(xpath = "//*[@class='example-cnt']//span[contains(text(), 'text2')]")
    public WebElement secondError;
    @FindBy(xpath = "//*[@class='example-cnt']//input[@type='text'][contains(@name, 'text2')]")
    public WebElement secondInput;
    @FindBy(xpath = "//*[@class='example-cnt']//div[contains(@id, 'out2')]")
    public WebElement secondOutput;

}
