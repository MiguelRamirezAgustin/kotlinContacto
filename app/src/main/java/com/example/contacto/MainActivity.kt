package com.example.contacto

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import android.widget.Switch
import android.widget.ViewSwitcher
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {


    var lista_:ListView? = null

    companion object{
        var contactos:ArrayList<Contacto>? = null
        var adaptador:AdaptadorCustom? = null
        var viewSwitch:ViewSwitcher? = null

        fun agregarcontacto(contacto:Contacto){
            adaptador?.addItem(contacto)
             //contactos?.add(contacto)
        }

        //para pasar parametros de un contacto
        fun obtenercontacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        //funcion para elminiar
        fun eliminarContacto(index: Int){
            adaptador?.removeItem(index)
            //contactos?.removeAt(index)
        }

        //function para actualizar contacto
        fun actualizarContacto(index:Int, nuevoContacto:Contacto){
            //remplaza por un elemento en su posision
            adaptador?.updateItem(index, nuevoContacto)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Mostrar ToolBar
        val  toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos= ArrayList()
        contactos?.add(Contacto ("Miguel", "Ramirez", "Soultech", 20, 640.0F, "coyoacan", "55234232", "miguel@gmail.com", R.drawable.foto_01))
        contactos?.add(Contacto ("Mario", "Juarez", "Soultech", 23, 61.0F, "coyoacan", "55234232", "pruebas@gmail.com", R.drawable.foto_06))
        contactos?.add(Contacto ("Miguel Anguel", "Perez", "Soultech", 34, 53.0F, "coyoacan", "55234232", "pruebas01@gmail.com", R.drawable.foto_04))
        contactos?.add(Contacto ("Carlos", "Lopez", "Soultech", 24, 45.0F, "coyoacan", "55234232", "pruebas03@gmail.com", R.drawable.foto_05))
        contactos?.add(Contacto ("Juan", "Santos", "Soultech", 30, 60.0F, "coyoacan", "55234232", "pruebas05@gmail.com", R.drawable.foto_02))
        contactos?.add(Contacto ("Andres", "Colin", "Soultech", 24, 60.0F, "coyoacan", "55234232", "pruebas06@gmail.com", R.drawable.foto_03))

         lista_ = findViewById<ListView>(R.id.lista)
         adaptador = AdaptadorCustom(this, contactos!!)
         lista_?.adapter= adaptador
        viewSwitch = findViewById(R.id.viewSwitcher)


        //Evento para pasar al detalle de cada elemento de la lista
        lista_?.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    //Mostrar el meunu 
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        //Button busqueda
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwich = menu?.findItem(R.id.switchView)
        itemSwich?.setActionView(R.layout.switch_item)
        val swichView = itemSwich?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint ="Buscar contacto...."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            //para preparar los datos
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //filtrar datos
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //filtrar datos
                adaptador?.filtrar(newText!!)
                return true
            }

        })

        //swich para cambio de vista
        swichView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitch?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            R.id.iNuevo->{
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else->{ return super.onOptionsItemSelected(item)}
        }
    }


    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }

}
