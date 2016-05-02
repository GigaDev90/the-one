/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import core.ConnectionListener;
import core.DTNHost;
import core.SimScenario;
import routing.util.RoutingInfo;

/**
 *
 * @author gabriele
 */
public class SpyDeliveryPredictabilitiesReport extends Report
        implements ConnectionListener {

    private DTNHost spy;
    /*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
     */

    /**
     * Constructor.
     */
    public SpyDeliveryPredictabilitiesReport() {
        init();
    }

    @Override
    public void init() {
        super.init();
        for ( DTNHost tmpSpy: SimScenario.getInstance().getHosts()) {
            if( tmpSpy.toString().length() >= 3 && tmpSpy.toString().substring(0, 3).equals("spy") ) {
                this.spy = tmpSpy;
                return;
            }
        }
        
        write("No spy Node found");
    }

    public void hostsConnected(DTNHost spy, DTNHost victim) {
    
        if (spy.equals(this.spy)) {
            if (isWarmup()) {
                System.out.println("it's warm");
                addWarmupID(deliveryPredString(victim));
                return;
            }

            newEvent();
            write(createTimeStamp() + " " + deliveryPredString(victim));
        }
    }
    
    

    //nothing to do
    public void hostsDisconnected(DTNHost h1, DTNHost h2) {}

    /**
     * Creates and returns a "@" prefixed time stamp of the current simulation
     * time
     *
     * @return time stamp of the current simulation time
     */
    private String createTimeStamp() {
        return String.format("@%.2f", getSimTime());
    }

    /**
     * Creates and returns a String presentation of the connection where the
     * node with the lower network address is first
     *
     * @param h1 The other node of the connection
     * @param h2 The other node of the connection
     * @return String presentation of the connection
     */
    private String deliveryPredString(DTNHost victim) {
        String dps = "";

        RoutingInfo routingInfo = victim.getRoutingInfo().getMoreInfo().get(victim.getRoutingInfo().getMoreInfo().size() - 1);
        dps += routingInfo.toString() + " of "+victim+"\n";

        for (RoutingInfo routingInfo1 : routingInfo.getMoreInfo()) {
            dps += routingInfo1.toString() + "\n";
        }

        return dps;
    }

}
