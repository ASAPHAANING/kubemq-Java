/*
 * MIT License
 *
 * Copyright (c) 2018 KubeMQ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kubemq.sdk.Queue;

import io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse;
import io.kubemq.sdk.tools.Converter;

public class ReceiveMessagesResponse{

	private ReceiveQueueMessagesResponse receiveQueueMessagesResponse;

    public ReceiveMessagesResponse(ReceiveQueueMessagesResponse rec) {
        receiveQueueMessagesResponse =rec;
    }

    public String getRequestID() {
        return this.receiveQueueMessagesResponse.getRequestID();
    }
  
    public Boolean getIsError(){
        return this.receiveQueueMessagesResponse.getIsError();    
      }
    public String getError(){
        return this.receiveQueueMessagesResponse.getError();    
    }
    public Boolean getIsPeak(){
        return this.receiveQueueMessagesResponse.getIsPeak();    
    }

    public Iterable<? extends Message> getMessages(){
        return Converter.FromQueueMessages(this.receiveQueueMessagesResponse.getMessagesList());
    }

    public int getMessagesExpired(){
        return this.receiveQueueMessagesResponse.getMessagesExpired();
    }

    public int getMessagesReceived(){
        return this.receiveQueueMessagesResponse.getMessagesReceived();
    }


    
    
}