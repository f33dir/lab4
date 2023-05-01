package com.f33dir.lab4

import android.util.Log
import kotlinx.serialization.json.Json
import java.net.Socket
import kotlinx.serialization.encodeToString

class SocketConnection(ip: String, port: Int) {
    var serverIp : String = ip;
    var serverPort = port;
    private lateinit var socket :Socket;

    fun status(): Boolean {
        return socket.isConnected
    }

    fun connect(callback : ()->Unit) {
        Thread {
            socket = Socket(serverIp, serverPort);
            Log.d("socket", socket.isConnected.toString());
            callback()
        }.start()
    }

    fun disconnect(){
        if (!socket.isClosed ){
            socket.shutdownOutput()
            socket.shutdownInput()
            socket.close()

        }
    }
    fun flu(data: com.f33dir.lab4.Package){
        val output = socket.getOutputStream()
        output.write(Json.encodeToString(data).toByteArray())
        output.flush()
    }
    fun send(data: Package){
        if (socket.isConnected){
            try {
                Thread{
                    flu(data);
                }.start()
            } catch (e : Exception){
                throw e;
            }
        }
    }
    @Override
    fun finalize(){
        socket.close()
    }
}


