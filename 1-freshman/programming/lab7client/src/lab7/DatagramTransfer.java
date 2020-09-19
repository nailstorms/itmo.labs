package lab7;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

public class DatagramTransfer {
    private Gson gson = new Gson();
    private byte[] buffer = new byte[32768];
    private InetSocketAddress address;
    private DatagramSocket socket;

    public DatagramTransfer (String ip, int newPort) throws SocketException {
        address = new InetSocketAddress(ip, newPort);
        socket = new DatagramSocket();
        socket.setSoTimeout(5000);
    }

    public void transmitData(String data){
        try {
            buffer = null;
            buffer = new byte[32768];

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutput output = new ObjectOutputStream(stream);
            output.writeObject(data);
            output.close();
            buffer = stream.toByteArray();

            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address);
            socket.send(request);

            buffer = null;
            buffer = new byte[32768];
        } catch (SocketTimeoutException exc) {
            System.err.println("No response from server. Try again later.");
        }
        catch (IOException exc) {
            System.err.println("An I/O exception has occurred. Please try again.");
            exc.printStackTrace();
        }
    }

    public String[] receiveData() {
        try {

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            socket.receive(response);

            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(buffer));

            String serialized = ((String)(stream.readObject())).trim();
            String[] deserialized = serialized.split("/");
            String[] dataForGUI = new String[deserialized.length];

//            System.out.println(serialized);

            for (int i = 0; i < deserialized.length; i++) {
                System.out.println(deserialized[i]);
                dataForGUI[i] = deserialized[i];
            }


            buffer = null;
            buffer = new byte[32768];

            return dataForGUI;
        } catch (SocketTimeoutException exc) {
            String[] error = {"No response from server. Try again later."};
            return error;
        }
        catch (ClassNotFoundException exc) {
            String[] error = {"Class has not been found."};
            return null;
        }
        catch (IOException exc) {
            String[] error = {"An I/O exception has occurred. Please try again."};
            return error;
        }
    }





}
