/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ServerThreadBus {
    private List<ServerThread> listServerThreads;

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }

    public void add(ServerThread serverThread){
        listServerThreads.add(serverThread);
    }
    
    // Gửi một thông điệp (message) đến tất cả các kết nối máy khách trong danh sách
    public void mutilCastSend(String message){ //like sockets.emit in socket.io
        for(ServerThread serverThread : Server.serverThreadBus.getListServerThreads()){
            try {
                serverThread.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //Gửi một thông điệp đến tất cả các kết nối máy khách ngoại trừ máy khách có ID (client number) là id
    public void boardCast(int id, String message){
        for(ServerThread serverThread : Server.serverThreadBus.getListServerThreads()){
            if (serverThread.getClientNumber() == id) {
                continue;
            } else {
                try {
                    serverThread.write(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    //Trả về số lượng kết nối máy khách hiện tại trong danh sách.
    public int getLength(){
        return listServerThreads.size();
    }
    
    //Gửi một thông điệp đến máy khách có ID id.
    public void sendMessageToUserID(int id, String message){
        for(ServerThread serverThread : Server.serverThreadBus.getListServerThreads()){
            if(serverThread.getUser().getID()==id){
                try {
                    serverThread.write(message);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    //Lấy ra đối tượng ServerThread liên quan đến máy khách có ID ID.
    public ServerThread getServerThreadByUserID(int ID){
        for(int i=0; i<Server.serverThreadBus.getLength(); i++){
            if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getID()==ID){
                return Server.serverThreadBus.listServerThreads.get(i);
            }
        }
        return null;
    }
    //remove(int id): Xóa đối tượng ServerThread có ID id khỏi danh sách listServerThreads.
    public void remove(int id){
        for(int i=0; i<Server.serverThreadBus.getLength(); i++){
            if(Server.serverThreadBus.getListServerThreads().get(i).getClientNumber()==id){
                Server.serverThreadBus.listServerThreads.remove(i);
            }
        }
    }
}
