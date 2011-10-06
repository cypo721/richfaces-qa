/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2011, Red Hat, Inc. and individual contributors
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
package org.jboss.test.selenium.support.ui;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class ElementPresent implements ExpectedCondition<Boolean> {

    private WebElement element;

    public static ElementPresent getInstance() {
        return new ElementPresent();
    }
    
    public Boolean apply(WebDriver driver) {
        try {
            element.isDisplayed();
            return true;
        } catch(NoSuchElementException ignored) {
            return false;
        }        
    }
    
    public ElementPresent element(WebElement element) {
        ElementPresent copy = copy();
        copy.element = element;
        return copy;
    }
    
    private ElementPresent copy() {
        ElementPresent copy = new ElementPresent();
        copy.element = this.element;
        return copy;
    }
}
