/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2010-2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.richfaces.demo.push;

import org.joda.time.DateTime;
import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;
import org.richfaces.tests.metamer.Message;
import org.richfaces.tests.metamer.bean.a4j.A4JPushBean;

/**
 * Sends message to topic using TopicsContext.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 */
public class TopicsContextMessageProducer implements MessageProducer {

    public static final String PUSH_TOPICS_CONTEXT_ADDRESS = "xxx@pushTopicsContext";
    public static final TopicKey PUSH_APPLICATION_TOPICKEY = new TopicKey(PUSH_TOPICS_CONTEXT_ADDRESS);

    private String text = "Hello, still alive!";
    private String author = "Push Bot";

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.demo.push.MessageProducer#sendMessage()
     */
    public void sendMessage() throws Exception {
        try {
            TopicsContext topicsContext = TopicsContext.lookup();
            topicsContext.publish(PUSH_APPLICATION_TOPICKEY, new Message(text, author, new DateTime().toString(A4JPushBean.DATE_PATTERN)));
        } catch (MessageException e) {
            if (!e.getMessage().matches("^Topic .* not found$")) {
                throw e;
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.demo.push.MessageProducer#getInterval()
     */
    public int getInterval() {
        return 5000;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.demo.push.MessageProducer#finalizeProducer()
     */
    @Override
    public void finalizeProducer() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
