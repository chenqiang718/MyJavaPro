package cq.cq.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class HttpProxyInitializer extends ChannelInitializer {
    private Channel clientChannel;
    public HttpProxyInitializer(Channel clientChannel){
        this.clientChannel=clientChannel;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline cp=channel.pipeline();
        cp.addLast(new HttpClientCodec());
        cp.addLast(new HttpObjectAggregator(6553600));
        cp.addLast(new HttpProxyClientHandle(clientChannel));
    }
}
