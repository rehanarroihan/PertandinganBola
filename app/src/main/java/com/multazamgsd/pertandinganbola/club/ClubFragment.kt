package com.multazamgsd.pertandinganbola.club

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.R.array.league
import com.multazamgsd.pertandinganbola.R.color.colorAccent
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.clubdetail.ClubDetailActivity
import com.multazamgsd.pertandinganbola.model.Teams
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.listeners.textChangedListener
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class ClubFragment : Fragment(), AnkoComponent<Context>, ClubView {
    private var TAG = "ClubFragment"
    private var teams: MutableList<Teams> = mutableListOf()
    private lateinit var presenter: ClubPresenter
    private lateinit var adapter: ClubAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var etSearch: EditText
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            etSearch = editText{
                hint = "Search team name"
                background = resources.getDrawable(R.drawable.searchfield)
            }.lparams(width = matchParent, height = wrapContent)

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        leagueName = "English Premier League"
        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = ClubAdapter(teams) {
            startActivity<ClubDetailActivity>(
                    "team_id" to it.idTeam
            )
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = ClubPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.isRefreshing = true
        presenter.getTeamList(leagueName)
        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

        etSearch.textChangedListener {
            afterTextChanged {
                swipeRefresh.isRefreshing = true
                if(it.toString() != ""){
                    presenter.searchTeam(it.toString())
                }else{
                    presenter.getTeamList(leagueName)
                }
            }
        }
    }

    override fun loadClubList(data: List<Teams>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
