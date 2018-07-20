package cq.monitor;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Snmp协议trap命令工具类
 * @author chenqiang
 * @date 2018/7/19
 */
public class SnmpTrapUtil extends SnmpUtil{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SnmpTrapUtil.class);
    public static final Map<String,Snmp> snmpMap=new HashMap<>();
    private Snmp snmp=null;
    private Address listenAddress=null;
    private ThreadPool threadPool=null;
    private MultiThreadedMessageDispatcher dispatcher=null;
    private String agentIP;//代理地址

    public SnmpTrapUtil(String agentIP){
        super();
        this.agentIP=agentIP;
        listenAddress = GenericAddress.parse(System.getProperty(
                "snmp4j.listenAddress", "udp:"+agentIP+"/162")); // 本地IP与监听端口
    }

    public void init() throws IOException {
        threadPool = ThreadPool.create("Trap", 2);
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,
                new MessageDispatcherImpl());
        TransportMapping transport=new DefaultUdpTransportMapping(new UdpAddress("0.0.0.0/162"));
        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3
                .createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        snmpMap.put("10.2.7.23/162", snmp);
        snmp.listen();
        snmp.addCommandResponder(new CommandResponder() {
            @Override
            public void processPdu(CommandResponderEvent commandResponderEvent) {
                PDU pdu=commandResponderEvent.getPDU();
                if(pdu!=null){
                    logger.info("捕捉到trap信息:");
                    logger.info("消息地址："+commandResponderEvent.getPeerAddress());
                    if(pdu.toString().indexOf(":")>0){
                        logger.info("原信息:"+pdu.toString());
                        logger.info("转码后的值为: "+convertUTF8ToString(pdu.get(0).getVariable().toString().replace(":", "")));
                    }
                    else {
                        logger.info(pdu.toString());
                    }
                }
            }
        });
        logger.info("开始监听trap...");
    }

    public Snmp getSnmp() throws IOException {
        String address="0.0.0.0/162";
        snmp=snmpMap.get(address);
        if(snmp==null){
            snmp=new Snmp(new DefaultUdpTransportMapping(new UdpAddress(address)));
            snmpMap.put(address, snmp);
        }
        return snmp;
    }

    /**
     * trap命令
     * @param oid oid
     * @param value oid的值
     * @throws IOException
     */
    public void trapPDU(String oid,String value) throws IOException {
        if(snmp==null){
            snmp=getSnmp();
        }
        CommunityTarget communityTarget=createTarget("public", listenAddress, SnmpConstants.version2c);
        PDU pdu=createPDU(oid, PDU.TRAP);
        pdu.get(0).setVariable(new OctetString(value));
        logger.info("Trap信息准备发送...，目标："+listenAddress.toString());
        ResponseEvent responseEvent=snmp.send(pdu, communityTarget);
        logger.info("Trap信息已发送...");
        readResponse(responseEvent);
    }

    public static void main(String[] args) {
        String oid=".1.3.6.1.2.1.1.4.0";
        SnmpTrapUtil snmpTrapUtil=new SnmpTrapUtil("10.2.1.47");
        try {
            snmpTrapUtil.init();
            snmpTrapUtil.trapPDU(oid,"你好");
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            snmpTrapUtil.close();
        }
    }
}