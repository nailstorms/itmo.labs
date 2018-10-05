package lab6;

import com.company.Main;
import com.google.gson.Gson;
import lab3.NPC;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingDeque;

public class DatagramReceiver {
    private Gson gson = new Gson();
    private InetSocketAddress address;

    private DatagramChannel channel;
    private SocketAddress from;

    public DatagramReceiver() {
        try {
            this.address = new InetSocketAddress(Main.IPAddress, Main.PortNumber);


            channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            socket.bind(address);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.out.println("An I/O exception has occurred. Please try again.");
        }
    }


    public void transmitCollection(LinkedBlockingDeque<NPC> npcs) {
        try {
            ByteBuffer transmit = ByteBuffer.allocate(32768);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteStream);


            String serialized = "";
            for (NPC readNPCs : npcs) {
                serialized = serialized + readNPCs.toString() + "/";
            }


            stream.writeObject(serialized);
            stream.close();


            transmit.clear();
            transmit.put(byteStream.toByteArray());

            transmit.flip();
            channel.send(transmit, from);

            transmit.clear();

        } catch (IOException exc) {
            System.err.println("An I/O exception has occurred. Please try again.");
        }
    }



    public void transmitMessage(String data){
        try {
            ByteBuffer transmit = ByteBuffer.allocate(32768);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteStream);

            stream.writeObject(data);
            stream.close();

            byte[] npcBytes = byteStream.toByteArray();


            transmit.clear();
            transmit.put(npcBytes);

            transmit.flip();
            channel.send(transmit, from);

            transmit.clear();

        } catch (IOException exc) {
            System.err.println("An I/O exception has occurred. Please try again.");
        }
    }

    public String receiveData() {
        try {
            ByteBuffer receive = ByteBuffer.allocate(32768);

            receive.clear();
            from = channel.receive(receive);

            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(receive.array()));
            String deserialized = ((String) stream.readObject()).trim();

            receive.clear();
            receive.flip();


            return deserialized;

        } catch (IOException exc) {
            return "An I/O exception has occurred. Please try again.";
        } catch (ClassNotFoundException exc) {
            return "Such class has not been found. Please check for errors and try again.";
        }
    }

}

