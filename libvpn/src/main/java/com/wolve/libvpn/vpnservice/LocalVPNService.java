package com.wolve.libvpn.vpnservice;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.VpnService;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;

import com.wolve.libvpn.R;
import com.wolve.libvpn.vpnconfig.LocalVPNConfig;

public class LocalVPNService extends VpnService {

    private ParcelFileDescriptor vpnDescriptor = null;

    private PendingIntent pendingIntent;

    public LocalVPNService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new VpnBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private void initVpn(){
        if (vpnDescriptor == null){
            vpnDescriptor = new Builder().addAddress(LocalVPNConfig.VPN_ADDRESS, 32)
                    .addRoute(LocalVPNConfig.VPN_ROUTE, 0)
                    .addDnsServer(LocalVPNConfig.VPN_DNS)
                    .setSession(getString(R.string.app_name))
                    .setConfigureIntent(pendingIntent)
                    .establish();
        }


    }

    public class VpnBinder extends Binder{
        public  LocalVPNService getService(){
            return LocalVPNService.this;
        }
    }
}
