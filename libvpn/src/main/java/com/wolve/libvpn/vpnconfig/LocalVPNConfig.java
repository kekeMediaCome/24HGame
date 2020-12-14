package com.wolve.libvpn.vpnconfig;

public class LocalVPNConfig {

    //虚拟网络端口的最大传输单元，如果发送的包长度超过设定的，则会分包
    public static String VPN_MTU = "";
    //虚拟网络端口的IP地址
    public static String VPN_ADDRESS = "";
    //只有匹配的IP数据包，才会被路由到虚拟端口上
    public static String VPN_ROUTE = "";
    //端口的DNS服务器地址
    public static String VPN_DNS = "114.114.114.114";
    //DNS域名的自动补齐
    public static String VPN_SEARCH_DOMAIN = "";
    //VPN连结的名字
    public static String VPN_NAME = "";

}
