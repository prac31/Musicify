package com.example.WikiMusic.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.WikiMusic.Adapters.Album
import com.example.WikiMusic.Adapters.Artist
import com.example.WikiMusic.Adapters.Track
import com.example.WikiMusic.Api.Retrofit
import com.example.WikiMusic.R
import com.example.WikiMusic.model.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_genre_detail.*
import retrofit2.Call

class GenreDetail : AppCompatActivity() {


    lateinit var tagName: String

    var albums: MutableList<com.example.WikiMusic.model.Album> = mutableListOf()
    var artists: MutableList<com.example.WikiMusic.model.Artist> = mutableListOf()
    var tracks: MutableList<com.example.WikiMusic.model.Track> = mutableListOf()
    val albumAdapter: Album = Album(albums)
    var artistAdapter: Artist = Artist(artists)
    var trackAdapter: Track = Track(tracks)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_detail)
        Toast.makeText(this,"loading...",Toast.LENGTH_SHORT).show()




        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = albumAdapter
        tagName = intent.getStringExtra("tag")!!


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text!!.equals("ALBUMS")) {
                    recyclerView.adapter = albumAdapter
                } else if (tab.text!!.equals("ARTISTS")) {
                    recyclerView.adapter = artistAdapter
                } else if (tab.text!!.equals("TRACKS")) {
                    recyclerView.adapter = trackAdapter
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })





        getTagInfo()
        getTopAlbums()
        getTopArtists()
        getTopTracks()


    }

    private fun getTopTracks() {
        val response = Retrofit.api.getTagTracks(tagName)
        response.enqueue(
            object : retrofit2.Callback<TopTrackResponse> {
                override fun onResponse(
                    call: retrofit2.Call<TopTrackResponse>,
                    response: retrofit2.Response<TopTrackResponse>
                ) {
                    if (response.body() != null) {

                        val topTrackResponse = (response.body())!!
                        tracks.addAll(topTrackResponse.tracks.track)
                        trackAdapter.notifyDataSetChanged()


                    }
                }


                override fun onFailure(call: Call<TopTrackResponse>, t: Throwable) {

                }

            },
        )
    }

    private fun getTopArtists() {

        val response = Retrofit.api.getTagArtists(tagName)
        response.enqueue(
            object : retrofit2.Callback<TopArtistResponse> {
                override fun onResponse(
                    call: retrofit2.Call<TopArtistResponse>,
                    response: retrofit2.Response<TopArtistResponse>
                ) {
                    if (response.body() != null) {
                        val topArtistResponse = (response.body())!!
                        artists.addAll(topArtistResponse.topartists.artist)
                        artistAdapter.notifyDataSetChanged()


                    }
                }


                override fun onFailure(call: Call<TopArtistResponse>, t: Throwable) {
                    Toast.makeText(this@GenreDetail, "Failed $t", Toast.LENGTH_LONG).show()
                }

            },
        )
    }

    private fun getTopAlbums() {
        val response = Retrofit.api.getTopAlbums(tagName)
        response.enqueue(
            object : retrofit2.Callback<TopAlbumResponse> {
                override fun onResponse(
                    call: retrofit2.Call<TopAlbumResponse>,
                    response: retrofit2.Response<TopAlbumResponse>
                ) {
                    if (response.body() != null) {

                        val topAlbumResponse = (response.body())!!
                        albums.addAll(topAlbumResponse.albums.album)
                        albumAdapter.notifyDataSetChanged()


                    }
                }


                override fun onFailure(call: Call<TopAlbumResponse>, t: Throwable) {
                    Toast.makeText(this@GenreDetail, "Failed $t", Toast.LENGTH_LONG).show()
                }

            },
        )

    }

    private fun getTagInfo() {


        val response = Retrofit.api.getTagInfo(tagName)
        response.enqueue(
            object : retrofit2.Callback<TagInfoResponse> {
                override fun onResponse(
                    call: retrofit2.Call<TagInfoResponse>,
                    response: retrofit2.Response<TagInfoResponse>
                ) {
                    if (response.body() != null) {

                        val taginfo = (response.body())!!
                        tagDescription.text = taginfo.tag.wiki.summary
                        tagTitle.text = taginfo.tag.name


                    }
                }


                override fun onFailure(call: Call<TagInfoResponse>, t: Throwable) {
                    Toast.makeText(this@GenreDetail, "Failed $t", Toast.LENGTH_LONG).show()
                }

            })

    }
}


