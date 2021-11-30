package mx.tecnm.tepic.alramirezh.ladm_u4_t1_contentproviders

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val siRecibidas = 15
    val siRealizadas = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BotonRealizadas.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG),siRealizadas)
            }else{
                cargarLlamadasRealizadas()
            }
        }
        BotonRecibidas.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG),siRecibidas)
            }else{
                cargarLlamadasRecibidas()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if(requestCode==siRecibidas){
                if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Debes aceptar permiso para ver la lista de llamadas recibidas",Toast.LENGTH_LONG).show()
                }else{
                    cargarLlamadasRecibidas()
                }
            }
        else if(requestCode==siRealizadas) {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Debes aceptar permiso para ver la lista de llamadas realizadas",Toast.LENGTH_LONG).show()
            }else {
                cargarLlamadasRealizadas()
            }
        }


    }


    fun cargarLlamadasRecibidas() {

        var resultado = ArrayList<String>()
        try {
            val cursor =
                contentResolver.query(Uri.parse("content://call_log/calls"), null, null, null, null)
            if (cursor!!.moveToFirst()) {
                do {
                    var columna = cursor.getColumnIndex("NUMBER")
                    var telefono = cursor!!.getString(columna).replace(" ", "").replace("-", "")
                    var columna2 = cursor.getColumnIndex("TYPE")
                    var tipo = cursor.getInt(columna2)
                    var columna3 = cursor.getColumnIndex("DATE")
                    var fecha = Calendar.getInstance()
                    fecha.timeInMillis = cursor.getString(columna3).toLong()

                    if (tipo == 1) {
                        resultado.add(
                            "Telefono: " + telefono + "\n" + "Fecha:  " + fecha.get(
                                Calendar.DAY_OF_MONTH
                            ) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR)
                        )

                    }
                } while (cursor.moveToNext())
                cursor.close()
                Toast.makeText(this, "${resultado.size}", Toast.LENGTH_LONG).show()
                lista.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultado)

            }
        } catch (e: Exception) {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }

    }




    fun cargarLlamadasRealizadas() {

        var resultado = ArrayList<String>()
        try {
            val cursor =
                contentResolver.query(Uri.parse("content://call_log/calls"), null, null, null, null)
            if (cursor!!.moveToFirst()) {
                do {
                    var columna = cursor.getColumnIndex("NUMBER")
                    var telefono = cursor!!.getString(columna).replace(" ", "").replace("-", "")
                    var columna2 = cursor.getColumnIndex("TYPE")
                    var tipo = cursor.getInt(columna2)
                    var columna3 = cursor.getColumnIndex("DATE")
                    var fecha = Calendar.getInstance()
                    fecha.timeInMillis = cursor.getString(columna3).toLong()

                    if (tipo == 2) {
                        resultado.add(
                            "Telefono: " + telefono + "\n" + "Fecha:  " + fecha.get(
                                Calendar.DAY_OF_MONTH
                            ) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR)
                        )

                    }
                } while (cursor.moveToNext())
                cursor.close()
                Toast.makeText(this, "${resultado.size}", Toast.LENGTH_LONG).show()
                lista.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultado)

            }
        } catch (e: Exception) {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }

    }

}