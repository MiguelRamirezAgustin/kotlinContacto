package com.example.contacto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Detalle : AppCompatActivity() {

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        //Mostrar ToolBar
        val  toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Activar boton re regreso y se configura en manifes para la gerarquia
        val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)

        //Recuperar datos de detalle
        index = intent.getStringExtra("ID").toInt()
        Log.d("Detalle " ,  index.toString())

        mapearDatos()
    }


    fun mapearDatos(){
        val contacto = MainActivity.obtenercontacto(index)


        val tvNombre = findViewById<TextView>(R.id.tv_Nombre_D)
        val tvApellidos = findViewById<TextView>(R.id.tv_Apellidos_D)
        val tvEmpresa = findViewById<TextView>(R.id.tv_Empresa_D)
        val tvEdad = findViewById<TextView>(R.id.tv_Edad_D)
        val tvPeso = findViewById<TextView>(R.id.tv_Peso_D)
        val tvTelefono = findViewById<TextView>(R.id.tv_Telefono_D)
        val tvEmail = findViewById<TextView>(R.id.tv_Correo_D)
        val tvDireccion = findViewById<TextView>(R.id.tv_Direccion_D)
        val tvFoto = findViewById<ImageView>(R.id.id_Foto_D)

        tvNombre.text = contacto.nombre
        tvApellidos.text = contacto.apellidos
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString() +" años"
        tvPeso.text = contacto.peso.toString() + " Kg"
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        tvFoto.setImageResource(contacto.foto)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detallle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            R.id.iEditar->{
                //Para editar la informacion se usa la misma actividad de agregar
                //hay manera de crear una nueva activity para editar
                val  inten = Intent(this, Nuevo::class.java)
                inten.putExtra("ID", index.toString())
                startActivity(inten)
                return true
            }
            R.id.iEliminar->{
                MainActivity.eliminarContacto(index)
                finish()
              return true
            }
            else-> { return super.onOptionsItemSelected(item)}
        }

    }


    //para actualizar
    override fun onResume() {
        super.onResume()
        mapearDatos()
    }

}
