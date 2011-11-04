/**
 * 	This file is part of Kayak.
 *
 *	Kayak is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Kayak is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public License
 *	along with Kayak.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.github.kayak.core;

/**
 * A send job sends a single CAN frame at a given interval time. If the bus
 * is connected to a remote socketcand the send job is fowarded to SocketCAN
 * for higher timestamp precision.
 * If there is no connection the sending is done locally.
 * Each property change does immediately update the send job.
 * @author Jan-Niklas Meier < dschanoeh@googlemail.com >
 */
public class SendJob {

    private int id;
    private byte[] data;
    private long interval;
    private Thread thread;
    private Bus bus;
    private boolean local = false;
    private boolean sending = false;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;

        if(sending && !local) {
             stopRemoteSending();
             startRemoteSending();
        }
    }

    public int getId() {
        return id;
    }

    public boolean isSending() {
        return sending;
    }

    public void setId(int id) {
        int oldId = this.id;
        this.id = id;

        if(sending && !local) {
            /* remove old send job */
            bus.removeSendJob(oldId);

            startRemoteSending();
        }
    }

    /**
     * Get the interval in microseconds
     */
    public long getInterval() {
        return interval;
    }

    /**
     * Set the interval in microseconds
     * @param interval
     */
    public void setInterval(long interval) {
        this.interval = interval;

        if(sending && !local) {
            /* remove old send job */
            bus.removeSendJob(id);

            startRemoteSending();
        }
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            while(true) {
                Frame f = new Frame(id, data);

                bus.sendFrame(f);
                try {
                    Thread.sleep(interval / 1000);
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }
    };

    public SendJob(int id, byte[] data, long usec) {
        this.id = id;
        this.data = data;
        this.interval = usec;
    }

    public void startSending(Bus bus) {
        this.bus = bus;
        sending = true;
        /* Connection present -> remote sending */
        if(bus.getConnection() != null) {
            local = false;
            startRemoteSending();
        } else {
            local = true;
            startLocalSending();
        }
    }

    public void stopSending() throws InterruptedException {
        sending = false;
        if(local) {
            stopLocalSending();
        } else {
            stopRemoteSending();
        }
    }

    private void startLocalSending() {
        thread = new Thread(runnable);
        thread.start();
    }

    private void stopLocalSending() throws InterruptedException {
        if(thread != null && thread.isAlive()) {
            thread.interrupt();
            thread.join();
        }
    }

    private void startRemoteSending() {
        bus.addSendJob(id, data, interval);
    }

    private void stopRemoteSending() {
        bus.removeSendJob(id);
    }

}
