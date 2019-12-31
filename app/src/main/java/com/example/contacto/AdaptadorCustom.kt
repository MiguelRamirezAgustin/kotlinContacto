package com.example.contacto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCustom (var contex:Context, items:ArrayList<Contacto>):BaseAdapter() {

    //Almacenar los elementos que se van a mostrar
    var items:ArrayList<Contacto>? = null
    //array para realizar busqueda
    var copiaItems:ArrayList<Contacto>? = null

    init {
        this.items= ArrayList(items)
        this.copiaItems= items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //1.- asocia el renderizado ce los elementos con los objetos que se encuentran en la lista
        //2.- se necesita implementar una clase para permitir definir los elementos graficos, esto es para eficientar la memoria ViewHolder
        //3.- configuracion
        var viewHolder:ViewHolder? = null
        var vista:View? = convertView

        //Validar si la vista esta vacia
        if(vista == null){
            vista = LayoutInflater.from(contex).inflate(R.layout.template_contacto, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        }else{
           viewHolder = vista.tag as? ViewHolder
        }
        //Asigancion de valorea a elementos graficos
        val item = getItem(position) as Contacto
        viewHolder?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolder?.empresa?.text = item.empresa
        viewHolder?.foto?.setImageResource(item.foto)

        return  vista!!
    }

    override fun getItem(position: Int): Any {
      //Regresa el objeto entero la posicion
        return this.items?.get(position)!!

    }

    override fun getItemId(position: Int): Long {
       //Regresa el ide del elemento que se seleccione
        return position.toLong()
    }

    override fun getCount(): Int {
      //Regresa el numero de elementos de los items
        return this.items?.count()!!
    }

    //para mostrar los datos al agregar nuevo contacto
    fun addItem(item:Contacto){
        copiaItems?.add(item)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int, newItem:Contacto){
        copiaItems?.set(index, newItem)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }


    //para realizar la busqueda
    fun filtrar(str:String){
      items?.clear()

        if(str.isEmpty()){
            items = ArrayList(copiaItems!!)
            notifyDataSetChanged()
            return
        }

        var busqueda = str
        busqueda = busqueda.toLowerCase()

        for(item in copiaItems!!){
           val nombre = item.nombre.toLowerCase()
            if (nombre.contains(busqueda)){
                items?.add(item)
            }
        }

        notifyDataSetChanged()
    }

    private class ViewHolder(vista:View){
        //inicializar varibles
        var nombre:TextView? = null
        var foto:ImageView? = null
        var empresa:TextView? = null

        init {
            this.nombre = vista.findViewById(R.id.tv_Nombre)
            this.empresa = vista.findViewById(R.id.tv_empresa)
            this.foto = vista.findViewById(R.id.id_foto)
        }
    }


}