package com.xt.message.center.app.api;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xt.data.news.auth.JwtAccess;

import lombok.extern.slf4j.Slf4j;

/**
 * 版本接口
 * @author vivi207
 *
 */
@Slf4j
@RestController
@RequestMapping("/version")
public class VersionController {
	
	@Value("${spring.application.name:}")
	private String applicationName;
	@Value("${server.port:}")
	private String serverPort;
	@Value("${spring.profiles.active:}")
	private String profilesActive;
	@Value("${xt.version:}")
	private String version;
	
	@GetMapping
	public Map info(@AuthenticationPrincipal JwtAccess access) {
		Map data = new HashMap();
    	data.put("spring.application.name", applicationName);
    	data.put("server.port", serverPort);
    	data.put("spring.profiles.active", profilesActive);
    	data.put("version", version);
    	data.put("timestamp", System.currentTimeMillis());
    	data.put("ip", getLocalIPList());
    	data.put("username", access==null ? null : access.getUsername());
    	return data;
	}
	
    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
        	log.error(e.getMessage(), e);
        }
        return ipList;
    }
}
