package com.lenovo.bda.ca.casearchpios.factories;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ClientFactory {
	
    private static Client client = null;
    private ClientFactory() {
    }

    public static Client getInstance() {
    	if(client == null){
    		Settings settings = Settings.settingsBuilder().put("cluster.name","ca").put("client.transport.sniff", true).build();
    		
    		 try {
				client = TransportClient.builder().settings(settings).build()
							//.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.3.5"), 9300));
								.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.100.217.231"), 9300));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}			
    	}
    	
        return client;
    }
    
    public static void CloseClient(){
    	if(client != null){
    		client.close();
    		client = null;
    	}
    	
    }
}
