package com.tradency.kubemq.sdk.basic;

import com.tradency.kubemq.sdk.grpc.kubemqGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class GrpcClient {

    protected String _kubemqAddress;
    protected Metadata _metadata = null;
    private ManagedChannel channel = null;
    private kubemqGrpc.kubemqBlockingStub blockingStub = null;
    private kubemqGrpc.kubemqStub stub = null;

    protected GrpcClient() {
        InitRegistration();
    }

    public String getServerAddress() throws ServerAddressNotSuppliedException {
        return getKubeMQAddress();
    }

    public void setServerAddress(String value) {
        this._kubemqAddress = value;
    }

    protected kubemqGrpc.kubemqBlockingStub GetKubeMQClient() throws ServerAddressNotSuppliedException, SSLException {
        if (blockingStub == null) {
            // Open connection
            if (channel == null) {
                channel = constructChannel();
            }

            blockingStub = constructBlockingClient(channel);
            if (_metadata != null) {
                blockingStub = MetadataUtils.attachHeaders(blockingStub, _metadata);
            }
        }

        return blockingStub;
    }

    protected kubemqGrpc.kubemqStub GetKubeMQAsyncClient() throws ServerAddressNotSuppliedException, SSLException {
        if (stub == null) {
            // Open connection
            if (channel == null) {
                channel = constructChannel();
            }

            stub = constructAsyncClient(channel);
            if (_metadata != null) {
                stub = MetadataUtils.attachHeaders(stub, _metadata);
            }
        }

        return stub;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Construct blockingStub connecting to KubeMQ server at {@code host} (host:port).
     *
     * @return io.grpc.ManagedChannel object used to access the KubeMQ server
     */
    private ManagedChannel constructChannel() throws ServerAddressNotSuppliedException, SSLException {
        String kubemqAddress = getKubeMQAddress();
        String clientCertFile = ConfigurationLoader.GetCerificateFile();

        Logger logger = getLogger();
        if (logger.isInfoEnabled()) {
            getLogger().info(MessageFormat.format("constructing channel to KubeMQ on {0}", kubemqAddress));
        }

        if (StringUtils.isBlank(clientCertFile)) {
            return NettyChannelBuilder.forTarget(kubemqAddress)
                    .sslContext(
                            GrpcSslContexts
                                    .forClient()
                                    .trustManager(new File(clientCertFile))
                                    .build()
                    )
                    .build();
        } else {
            // Open Insecure connection
            return ManagedChannelBuilder.forTarget(kubemqAddress)
                    .usePlaintext()
                    .build();
        }
    }

    private org.slf4j.Logger getLogger() {
        return LoggerFactory.getLogger(GrpcClient.class);
    }

    /**
     * Construct blocking blockingStub for accessing KubeMQ server using the existing {@code channel}
     *
     * @param channel Client channel connecting to KubeMQ server
     * @return BlockingStub for accessing KubeMQ server
     */
    private kubemqGrpc.kubemqBlockingStub constructBlockingClient(ManagedChannel channel) {
        return kubemqGrpc.newBlockingStub(channel);
    }

    /**
     * Construct async blockingStub for accessing KubeMQ server using the existing {@code channel}
     *
     * @param channel Client channel connecting to KubeMQ server
     * @return Stub for accessing KubeMQ server
     */
    private kubemqGrpc.kubemqStub constructAsyncClient(ManagedChannel channel) {
        return kubemqGrpc.newStub(channel);
    }

    private String getKubeMQAddress() throws ServerAddressNotSuppliedException {
        // _kubemqAddress was supplied in the derived constructor
        if (StringUtils.isNotBlank(_kubemqAddress))
            return _kubemqAddress;

        _kubemqAddress = ConfigurationLoader.GetServerAddress();

        if (StringUtils.isBlank(_kubemqAddress)) {
            throw new ServerAddressNotSuppliedException();
        }

        return _kubemqAddress;
    }

    private void InitRegistration() {
        String registrationKey = ConfigurationLoader.GetRegistrationKey();

        if (StringUtils.isNotBlank(registrationKey)) {
            _metadata = new Metadata();
            Metadata.Key<String> key = Metadata.Key.of("X-Kubemq-Server-Token", ASCII_STRING_MARSHALLER);
            _metadata.put(key, registrationKey);
        }
    }

}