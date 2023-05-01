package com.f33dir.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    class ConnectDialog : DialogFragment() {
        lateinit var btnConnect : Button;
        lateinit var ipInput : EditText;
        lateinit var portInput : EditText;
        fun save(){
            var act = (activity as MainActivity)
            act.ip = ipInput.text.toString();
            act.port = (portInput.text.toString().toInt())
            act.connection = SocketConnection(act.ip,act.port)
            act.connection!!.connect{
                Log.d("socket", act.connection!!.status().toString())
                dismiss()
            };
        }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var view  = inflater.inflate(R.layout.fragment_dialog,container)
            btnConnect = view.findViewById(R.id.btnConnect);
            ipInput = view.findViewById(R.id.editIP);
            portInput = view.findViewById(R.id.editPort);
            btnConnect.setOnClickListener(View.OnClickListener { save() })
            return view
        }
    }
    var connection: SocketConnection? = null;
    var ip = "192.168.0.107";
    var port :Int = 6000;
    lateinit var playButton: Button;
    lateinit var nextButton: Button;
    lateinit var prevButton: Button;

    lateinit var cover : ImageView;


    override fun onDestroy() {
        super.onDestroy()
        connection?.disconnect()
    }
    override fun onPause() {
        super.onPause()
        connection?.disconnect()
    }

    override fun onResume() {
                super.onResume()
                connection?.connect(){
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener{onBtnPlayPress()}
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        cover = findViewById(R.id.imageView);
        var dialog = ConnectDialog()
        dialog.show(supportFragmentManager,"customDialog")
    }

    fun onBtnPlayPress(){
        connection?.send(com.f33dir.lab4.Package("hello",0))
    }
}