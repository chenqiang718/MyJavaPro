package cq.monitor;

import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Snmp协议get,set,walk命令工具类
 *
 * @author chenqiang
 * @date 2018/7/19
 */
public class SnmpUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SnmpUtil.class);
    private static Map<String, Snmp> snmpMap = new HashMap<>();
    private Snmp snmp = null;
    private Address listenAddress = null;
    private ThreadPool threadPool = null;
    private MultiThreadedMessageDispatcher dispatcher = null;
    private String agentIP;//代理地址

    public SnmpUtil() {
        this("127.0.0.1");
    }

    ;

    public SnmpUtil(String agentIP) {
        this.agentIP = agentIP;
        listenAddress = GenericAddress.parse(System.getProperty(
                "snmp4j.listenAddress", "udp:" + agentIP + "/161"));
    }

    public void init() throws IOException {
        threadPool = ThreadPool.create("get", 2);
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,
                new MessageDispatcherImpl());
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3
                .createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        snmpMap.put("127.0.0.1/162", snmp);
        snmp.listen();
        logger.info("Snmp开始监听...");
    }

    private Snmp getSnmp(String ip) throws IOException {
        String address = ip + "/161";
        snmp = snmpMap.get(address);
        if (snmp == null) {
            snmp = new Snmp(new DefaultUdpTransportMapping());
            snmpMap.put(address, snmp);
        }
        return snmp;
    }

    //同步发送报文
    private ResponseEvent send(PDU pdu) throws IOException {
        CommunityTarget communityTarget = createTarget();
        return snmp.send(pdu, communityTarget);
    }

    /**
     * 创建一个代理目标
     *
     * @param type    目标类型(通常为public)
     * @param address 目标地址
     * @param version 协议版本
     * @return 代理目标(被管理者)
     */
    protected CommunityTarget createTarget(String type, Address address, int version) {
        CommunityTarget communityTarget = new CommunityTarget();
        communityTarget.setCommunity(new OctetString(type));
        communityTarget.setAddress(address);
        communityTarget.setRetries(2);//重发次数为2次
        communityTarget.setTimeout(2000);//超时时间2秒
        communityTarget.setVersion(version);
        return communityTarget;
    }

    private CommunityTarget createTarget() {
        return createTarget("public", listenAddress, SnmpConstants.version2c);
    }

    /**
     * 创建报文
     *
     * @param oid  oid
     * @param type 报文类型
     * @return
     */
    protected PDU createPDU(String oid, int type) {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(type);
        return pdu;
    }

    //异步发送报文
    private void asySend(PDU pdu) throws IOException {
        CommunityTarget communityTarget = new CommunityTarget();
        communityTarget.setCommunity(new OctetString("public"));
        communityTarget.setAddress(listenAddress);
        communityTarget.setRetries(2);
        communityTarget.setTimeout(2000);
        communityTarget.setVersion(SnmpConstants.version2c);
        ResponseListener responseListener = new ResponseListener() {
            @Override
            public void onResponse(ResponseEvent responseEvent) {
                logger.info("--------异步解析----------");
                readResponse(responseEvent);
            }
        };
        snmp.send(pdu, communityTarget, null, responseListener);
        logger.info("-----------异步发送---------");
        try {
            Thread.sleep(500);//异步发送要延迟，不然收到信息之前程序结束
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        return;
    }

    public void getPDU(String oid) throws IOException {
        getPDU(oid, false);
    }

    /**
     * get命令
     *
     * @param oid oid
     * @param asy 是否异步发送
     * @throws IOException
     */
    public void getPDU(String oid, boolean asy) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        if (asy) {
            asySend(pdu);
        } else {
            ResponseEvent responseEvent = send(pdu);
            readResponse(responseEvent);
        }
    }

    public void setPDU(String oid, String value) throws IOException {
        setPDU(oid, value, false);
    }

    /**
     * set命令
     *
     * @param oid   oid
     * @param value oid对应的值
     * @param asy   是否异步发送
     * @throws IOException
     */
    public void setPDU(String oid, String value, boolean asy) throws IOException {
        PDU pdu = createPDU(oid, PDU.SET);
        String currentoid = pdu.get(0).getOid().toString();
        System.out.println(currentoid);
        pdu.get(0).setVariable(new OctetString(value));
        logger.info(pdu.get(0).getVariable().toString());
        if (asy) {
            asySend(pdu);
        } else {
            ResponseEvent responseEvent = send(pdu);
            readResponse(responseEvent);
        }
    }

    /**
     * walk命令
     *
     * @param oid oid
     * @throws IOException
     */
    public void snamWalk(String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);
        boolean match = true;
        while (match) {
            ResponseEvent responseEvent = send(pdu);
            String nextoid = null;
            if (responseEvent != null && responseEvent.getResponse() != null) {
                List<? extends VariableBinding> list = responseEvent.getResponse().getVariableBindings();
                for (VariableBinding variableBinding : list) {
                    nextoid = variableBinding.getOid().toString();
                    if (!nextoid.startsWith(oid)) {
                        match = false;
                        break;
                    }
                }
            }
            if (!match) {
                break;
            }
            pdu.clear();
            pdu.add(new VariableBinding(new OID(nextoid)));
            logger.info("返回结果：");
            readResponse(responseEvent);
        }
    }

    public static String convertUTF8ToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        try {
            s = s.toUpperCase();
            int total = s.length() / 2;
            int pos = 0;
            byte[] buffer = new byte[total];
            for (int i = 0; i < total; i++) {
                int start = i * 2;
                buffer[i] = (byte) Integer.parseInt(
                        s.substring(start, start + 2), 16);
                pos++;
            }
            return new String(buffer, 0, pos, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    protected void readResponse(ResponseEvent responseEvent) {
        if (responseEvent != null && responseEvent.getResponse() != null) {
            List<? extends VariableBinding> list = responseEvent.getResponse().getVariableBindings();
            for (VariableBinding variableBinding : list) {
                String value = variableBinding.getVariable().toString();
                logger.info("原值:"+value);
                if (value.indexOf(":") > 0) {//判断内容是否为中文
                    value = convertUTF8ToString(value.replace(":", ""));
                }
                logger.info(variableBinding.getOid() + ":" + "转码值【"+value+"】");
            }
        }
    }

    public void close() {
        try {
            if (snmp != null) {
                snmp.close();
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String oid = ".1.3.6.1.2.1.1.5.0";//系统基本信息
        String oid2 = "1.3.6.1.2.1.25.4.2.1.2";//获取内存大小
        SnmpUtil snmpUtil = new SnmpUtil("10.2.1.47");
        try {
            snmpUtil.init();
            snmpUtil.getPDU(oid);
            snmpUtil.snamWalk(oid2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            snmpUtil.close();
        }
    }
}