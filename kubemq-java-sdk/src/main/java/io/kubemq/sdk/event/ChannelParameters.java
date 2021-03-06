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
package io.kubemq.sdk.event;

public class ChannelParameters {

    /**
     * Represents The channel name to send to using the KubeMQ.
     */
    private String channelName;

    /**
     * Represents the sender ID that the messages will be send under.
     */
    private String clientID;

    /**
     * Represents if the messages should be send to persistence.
     */
    private boolean store;

    /**
     * Represents if the end user does not need the Result.
     */
    private boolean returnResult;

    /**
     * KubeMQ server address.
     */
    private String kubeMQAddress;

    /**
     * KubeMQ JWT Auth token to be used for KubeMQ connection.
     */
    private String authToken;


    public ChannelParameters() {
    }

    public ChannelParameters(String channelName, String clientID, boolean store, String kubeMQAddress, String authToken) {
        this.channelName = channelName;
        this.clientID = clientID;
        this.store = store;
        this.kubeMQAddress = kubeMQAddress;
        this.authToken =authToken;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public boolean isReturnResult() {
        return returnResult;
    }

    public void setReturnResult(boolean returnResult) {
        this.returnResult = returnResult;
    }

    public String getKubeMQAddress() {
        return kubeMQAddress;
    }

    public void setKubeMQAddress(String kubeMQAddress) {
        this.kubeMQAddress = kubeMQAddress;
    }

    public String getAuthToken(){
        return this.authToken;
    }
    
    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

}
