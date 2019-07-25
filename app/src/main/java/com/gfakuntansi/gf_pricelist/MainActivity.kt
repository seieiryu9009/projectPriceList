package com.gfakuntansi.gf_pricelist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

class MainActivity : SplashedActivity() {

    private var productRecyclerView: RecyclerView? = null
    private var resultList : ArrayList<ProductModel> = arrayListOf()
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        productRecyclerView = findViewById(R.id.recycler_view_produk)
        productRecyclerView?.setHasFixedSize(true)
        productRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        productRecyclerView?.adapter = myAdapter
        sendRequest(AppVar.KEY)

        btn_logout.setOnClickListener {
            logoutUser()
            startActivity(Intent(applicationContext, AuthActivity::class.java))
            finish()
        }
    }

    private fun sendRequest(key : String) {

        val oRequest = object : StringRequest(Method.POST, AppVar.GET_DATA_URL,
            Listener { response ->
                try {
                    val productArray = JSONArray(response)
                    resultList = ArrayList(productArray.length())

                    for (i in 0 until productArray.length()) {
                        val product = productArray.get(i) as JSONObject
                        resultList.add(ProductModel(
                            product.getString("id"),
                            product.getString("nama")
                        ))
                    }
                    myAdapter = MyAdapter(applicationContext, resultList)
                    myAdapter?.notifyDataSetChanged()
                    productRecyclerView?.adapter = myAdapter
                    Log.d("Array Of ", arrayListOf(resultList).toString())
                } catch ( e : JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener {
                error -> Toast.makeText(applicationContext, "Error : $error", Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()
                params[AppVar.KEY_USER] = "user"
                params[AppVar.KEY_PASSWORD] = "password"
                params[AppVar.GF_KEY] = key
                return params
            }
        }
        val requestQ = Volley.newRequestQueue(this)
        requestQ.add(oRequest)
    }

}
