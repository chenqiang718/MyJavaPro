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
import java.io.IOException;
import java.util.List;

public class SnmpUtil {
    private Snmp snmp=null;
    private Address address=null;
    static String url="udp:10.2.7.21/161";

    public void init() throws IOException {
        address=GenericAddress.parse(url);
        TransportMapping transportMapping=new DefaultUdpTransportMapping();
        snmp=new Snmp(transportMapping);
        snmp.listen();
    }

    private ResponseEvent send(PDU pdu) throws IOException {
        CommunityTarget communityTarget=new CommunityTarget();
        communityTarget.setCommunity(new OctetString("public"));
        communityTarget.setAddress(address);
        communityTarget.setRetries(2);
        communityTarget.setTimeout(2000);
        communityTarget.setVersion(SnmpConstants.version2c);
        return  snmp.send(pdu, communityTarget);
    }

    private void asySend(PDU pdu) throws IOException {
        CommunityTarget communityTarget=new CommunityTarget();
        communityTarget.setCommunity(new OctetString("public"));
        communityTarget.setAddress(address);
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
/*        ResponseEvent responseEvent=send(pdu);
        readResponse(responseEvent);*/
        asySend(pdu);
    }

    private void readResponse(ResponseEvent responseEvent){
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
                this.snmp.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SnmpUtil snmpUtil=new SnmpUtil();
        try {
            snmpUtil.init();
            snmpUtil.getPDU(".1.3.6.1.2.1.1.1.0");
            Thread.sleep(2000);
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
         snmpUtil.close();
        }
    }
}
