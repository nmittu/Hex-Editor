package com.nickapps.hexeditor.hexcommand;

import com.mittudev.ipc.ConnectionCallback;
import com.mittudev.ipc.Message;
import com.nickapps.hexeditor.HexEditor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nick on 8/8/17.
 */
public class MessageListener implements ConnectionCallback{
    private static final char OPEN = 1;
    private static final char CLOSE = 2;

    @Override
    public void onMessage(Message msg) {
        byte[] data = msg.getData();
        switch (data[0]){
            case OPEN: {
                ArrayList<String> fileNames = new ArrayList<>();

                int start = 1;
                int next = nextZero(data, start);
                while (next > 0) {
                    fileNames.add(new String(Arrays.copyOfRange(data, start, next)));
                    start = next + 1;
                    next = nextZero(data, start);
                }

                for (String file : fileNames) {
                    HexEditor.frame.tabbedPane.open(file);
                }

                break;
            }
            case CLOSE: {
                ArrayList<String> fileNames = new ArrayList<>();

                int start = 1;
                int next = nextZero(data, start);
                while (next > 0) {
                    fileNames.add(new String(Arrays.copyOfRange(data, start, next)));
                    start = next + 1;
                    next = nextZero(data, start);
                }

                for (String file : fileNames) {
                    HexEditor.frame.tabbedPane.close(file);
                }

                break;
            }
            default:
                System.out.println("Unrecognised command: " + data[0]);
        }
    }

    private static int nextZero(byte[] bytes, int start){
        for(int i = start; i < bytes.length; i++){
            if (bytes[i] == 0){
                return i;
            }
        }
        return -1;
    }
}
