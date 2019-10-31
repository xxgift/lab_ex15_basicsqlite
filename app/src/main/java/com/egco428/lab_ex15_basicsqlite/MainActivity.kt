package com.egco428.lab_ex15_basicsqlite

import android.app.ListActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : ListActivity() {
    private var dataSource:MovieDataSource? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataSource = MovieDataSource(this)
        dataSource!!.open()
        val values = dataSource!!.allComments
        val adapter = ArrayAdapter<Movie>(this,android.R.layout.simple_list_item_1,values)
        setListAdapter(adapter)
    }

    fun OnClick(view: View){
        val adapter = getListAdapter() as ArrayAdapter<Movie>
        var movie: Movie? = null
        when(view.getId()){
            R.id.add->{
                val addtext = movietitle.text.toString()
                movie = dataSource!!.createComment(addtext)
                adapter.add(movie)
                movietitle.text = null
            }
            R.id.delete -> if(getListAdapter().getCount() > 0){
                val position = movieposition.text.toString().toInt()
                movie = getListAdapter().getItem(position) as Movie
                dataSource!!.deleteComment(movie!!)
                adapter.remove(movie)
                movieposition.text = null
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }
}
