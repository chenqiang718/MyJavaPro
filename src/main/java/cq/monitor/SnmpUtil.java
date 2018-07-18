package cq.monitor;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import java.io.IOException;
import java.util.List;

public class SnmpUtil {
    private Snmp snmp=null;
    private Address listenAddress=null;
    private ThreadPool threadPool=null;
    private MultiThreadedMessageDispatcher dispatcher=null;
    private static String agentIP="127.0.0.1";

    public void init() throws IOException {
        listenAddress=GenericAddress.parse(System.getProperty(
                "snmp4j.listenAddress", "udp:"+agentIP+"/161"));
        TransportMapping transportMapping=new DefaultUdpTransportMapping();
        snmp=new Snmp(transportMapping);
        snmp.listen();
        System.out.println("基本信息开始监听...");
    }

    protected ResponseEvent send(PDU pdu) throws IOException {
        CommunityTarget communityTarget=createTarget();
        return  snmp.send(pdu, communityTarget);
    }

    protected CommunityTarget createTarget(String type,Address address,int version){
        CommunityTarget communityTarget=new CommunityTarget();
        communityTarget.setCommunity(new OctetString(type));
        communityTarget.setAddress(address);
        communityTarget.setRetries(2);
        communityTarget.setTimeout(2000);
        communityTarget.setVersion(version);
        return communityTarget;
    }

    protected CommunityTarget createTarget(){
        return  createTarget("public", listenAddress, SnmpConstants.version2c);
    }

    protected PDU createPDU(String oid,int type){
        PDU pdu=new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(type);
        return pdu;
    }

    protected void asySend(PDU pdu,int index) throws IOException {
        CommunityTarget communityTarget=new CommunityTarget();
        communityTarget.setCommunity(new OctetString("public"));
        communityTarget.setAddress(listenAddress);
        communityTarget.setRetries(2);
        communityTarget.setTimeout(2000);
        communityTarget.setVersion(SnmpConstants.version2c);
        ResponseListener responseListener=new ResponseListener() {
            @Override
            public void onResponse(ResponseEvent responseEvent) {
                System.out.println("-------异步解析----------");
                readResponse(responseEvent);
            }
        };
        snmp.send(pdu, communityTarget, null,responseListener);
        System.out.println("-----------异步发送中---------");
        return ;
    }

    public void getPDU(String oid) throws IOException {
        PDU pdu=new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        ResponseEvent responseEvent=send(pdu);
        readResponse(responseEvent);
//        asySend(pdu);
    }

    public void setPDU(String oid,String value) throws IOException {
        PDU pdu=createPDU(oid, PDU.SET);
        String currentoid=pdu.get(0).getOid().toString();
        System.out.println(currentoid);
        pdu.get(0).setVariable(new OctetString(value));
        System.out.println(pdu.get(0).getVariable());
        asySend(pdu,0);
    }

    public void snamWalk(String oid) throws IOException {
        PDU pdu=new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);
        boolean match=true;
        while (match){
            ResponseEvent responseEvent=send(pdu);
            String nextoid=null;
            if(responseEvent!=null && responseEvent.getResponse()!=null){
                List<VariableBinding> list=responseEvent.getResponse().getVariableBindings();
                for(VariableBinding variableBinding:list){
//                    System.out.println(variableBinding.getOid()+":"+variableBinding.getVariable());
                    nextoid=variableBinding.getOid().toString();
                    if(!nextoid.startsWith(oid)){
                        match=false;
                    }
                }
            }
            if(!match){
                break;
            }
            pdu.clear();
            pdu.add(new VariableBinding(new OID(nextoid)));
            System.out.println("返回结果："+responseEvent.getResponse());
        }
    }

    public void trapPDU(String oid,String value) throws IOException {
        CommunityTarget communityTarget=createTarget();
        PDU pdu=createPDU(oid, PDU.TRAP);
        pdu.get(0).setVariable(new OctetString(value));
        ResponseEvent responseEvent=send(pdu);
        readResponse(responseEvent);
    }

    protected void readResponse(ResponseEvent responseEvent){
        if(responseEvent!=null && responseEvent.getResponse()!=null){
            System.out.println("------开始解析Response------");
            List<VariableBinding> list=responseEvent.getResponse().getVariableBindings();
            for(VariableBinding variableBinding:list){
                System.out.println(variableBinding.getOid()+":"+variableBinding.getVariable());
            }
        }
    }

    public void close(){
        try {
            if(snmp!=null){
                snmp.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String oid=".1.3.6.1.2.1.1.4.0";
        //String oid=".1.3.6.1.2.1.2.2.1.2";
        SnmpUtil snmpUtil=new SnmpUtil();
        try {
            snmpUtil.init();
            //snmpUtil.trapPDU(oid, "nihao");
            //snmpUtil.getPDU(".1.3.6.1.2.1.1.1.0");
            /*snmpUtil.setPDU(oid, "chenqiang");
            Thread.sleep(200);*/
            //snmpUtil.getPDU(oid);
            snmpUtil.trapPDU(oid, "nihao");
            Thread.sleep(2000);
            //snmpUtil.snamWalk("1.3.6.1.2.1.25.3.3.1.2");
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
         snmpUtil.close();
        }
    }
}
//Root <root@localhost> (configure /etc/snmp/snmp.local.conf)