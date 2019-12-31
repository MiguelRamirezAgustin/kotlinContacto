package com.example.contacto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class Nuevo : AppCompatActivity() {

    var fotoIndex:Int = 0
    var fotos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_03, R.drawable.foto_04, R.drawable.foto_05, R.drawable.foto_06)
    var foto:ImageView? = null
    var banderaIndex:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)


        foto = findViewById<ImageView>(R.id.id_Foto_D)

        //Mostrar ToolBar
        val  toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //foto de la imagen seleccionar
        foto?.setOnClickListener{
            selecionarFoto()
        }

        //reconocer accion  para agregar nuevo o editar
        if(intent.hasExtra("ID")){
            banderaIndex = intent.getStringExtra("ID").toInt()
            //funcion para rellenar datos
            rellenarDatos(banderaIndex)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            android.R.id.home ->{
                finish()
                return true
            }

            R.id.Crear_Nuevo->{
                //aca se va a crear el nuevo elemento
                var nombre = findViewById<EditText>(R.id.tv_Nombre_D)
                var apellidos = findViewById<EditText>(R.id.tv_Apellidos_D)
                var empresa = findViewById<EditText>(R.id.tv_Empresa_D)
                var peso = findViewById<EditText>(R.id.tv_Peso_D)
                var edad = findViewById<EditText>(R.id.tv_Edad_D)
                var telefono = findViewById<EditText>(R.id.tv_Telefono_D)
                var correo = findViewById<EditText>(R.id.tv_Correo_D)
                var direccion = findViewById<EditText>(R.id.tv_Direccion_D)

                //validar campos se agregan a un array para validarlos en conjunto
                var campos=ArrayList<String>()

                campos.add(nombre.text.toString())
                campos.add(apellidos.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(correo.text.toString())

                //Uso de vandea para validar campos
                var bandera = 0
                for (campo in campos){
                    if (campo.isNullOrEmpty()){
                        bandera++
                    }
                }

                if(bandera > 0){
                    Toast.makeText(this, "Los campos estan vacios ", Toast.LENGTH_SHORT).show()
                }else{
                    if(banderaIndex > -1){
                        //Para agregar datos como no se almacena en local solo se coloca el objeto statico desde le mainActivity
                        MainActivity.actualizarContacto(banderaIndex, Contacto( campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerfotos(fotoIndex)))
                    } else{
                        //Para agregar datos como no se almacena en local solo se coloca el objeto statico desde le mainActivity
                        MainActivity.agregarcontacto(Contacto( campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerfotos(fotoIndex)))
                    }
                    //agregar usuairo sin validar con array
                    /*MainActivity.agregarcontacto(Contacto( nombre.text.toString(), apellidos.text.toString(), empresa.text.toString(), edad.text.toString().toInt(), peso.text.toString().toFloat(), direccion.text.toString(), telefono.text.toString(), correo.text.toString(), R.drawable.foto_02))*/
                    finish()
                    Log.d("Elemenots", MainActivity.contactos?.count().toString())

                }
                return true
            }

            else->{return super.onOptionsItemSelected(item)}
        }
    }



    fun selecionarFoto(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Sellecionar foto")

        var adapadorDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adapadorDialogo.add("foto_01")
        adapadorDialogo.add("foto_02")
        adapadorDialogo.add("foto_03")
        adapadorDialogo.add("foto_04")
        adapadorDialogo.add("foto_05")
        adapadorDialogo.add("foto_06")

        //se crea array para la foto son seleccionar en id desde un array
        builder.setAdapter(adapadorDialogo){
            dialog, which ->
            fotoIndex = which
            //esto cambia la foto
            foto?.setImageResource(obtenerfotos(fotoIndex))
        }

        builder.setNegativeButton("Cancelar"){
            dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    //funcion para obtener id de foto
    fun  obtenerfotos(index:Int):Int{
        return fotos.get(index)
    }


    //funcion para mostrar y rellenar datos
    fun rellenarDatos(index:Int){
        val contacto = MainActivity.obtenercontacto(index)

        val tvNombre = findViewById<EditText>(R.id.tv_Nombre_D)
        val tvApellidos = findViewById<EditText>(R.id.tv_Apellidos_D)
        val tvEmpresa = findViewById<EditText>(R.id.tv_Empresa_D)
        val tvEdad = findViewById<EditText>(R.id.tv_Edad_D)
        val tvPeso = findViewById<EditText>(R.id.tv_Peso_D)
        val tvTelefono = findViewById<EditText>(R.id.tv_Telefono_D)
        val tvEmail = findViewById<EditText>(R.id.tv_Correo_D)
        val tvDireccion = findViewById<EditText>(R.id.tv_Direccion_D)
        val tvFoto = findViewById<ImageView>(R.id.id_Foto_D)

        tvNombre.setText(contacto.nombre, TextView.BufferType.EDITABLE )
        tvApellidos.setText(contacto.apellidos, TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa, TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email, TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)
        tvFoto.setImageResource(contacto.foto)

        var posicion = 0
        for(foto in fotos){
            if(contacto.foto == foto){
                fotoIndex = posicion
            }
            posicion ++
        }

    }


}
