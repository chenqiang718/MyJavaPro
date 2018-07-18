package cq.monitor;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
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

public class SnmpTrapUtil extends SnmpUtil{
    private Snmp snmp=null;
    private Address listenAddress=null;
    private ThreadPool threadPool=null;
    private MultiThreadedMessageDispatcher dispatcher=null;
    private static String agentIP="127.0.0.1";

    public void init() throws IOException {
        threadPool = ThreadPool.create("Trap", 2);
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,
                new MessageDispatcherImpl());
        listenAddress = GenericAddress.parse(System.getProperty(
                "snmp4j.listenAddress", "udp:"+agentIP+"/162")); // 本地IP与监听端口
        TransportMapping transport=new DefaultUdpTransportMapping(new UdpAddress("127.0.0.1/162"));
        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3
                .createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        snmp.listen();
        snmp.addCommandResponder(new CommandResponder() {
            @Override
            public void processPdu(CommandResponderEvent commandResponderEvent) {
                PDU pdu=commandResponderEvent.getPDU();
                if(pdu!=null){
                    System.out.println(pdu);
                }
            }
        });
        System.out.println("开始监听trap...");
    }

    public void trapPDU(String oid,String value) throws IOException {
        CommunityTarget communityTarget=createTarget();
        PDU pdu=createPDU(oid, PDU.TRAP);
        pdu.get(0).setVariable(new OctetString(value));
        ResponseEvent responseEvent=send(pdu);
        readResponse(responseEvent);
    }

    public static void main(String[] args) {
        String oid=".1.3.6.1.2.1.1.4.0";
        //String oid=".1.3.6.1.2.1.2.2.1.2";
        SnmpTrapUtil snmpTrapUtil=new SnmpTrapUtil();
        try {
            snmpTrapUtil.init();
            snmpTrapUtil.trapPDU(oid,"lala");
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            snmpTrapUtil.close();
        }
    }
}
