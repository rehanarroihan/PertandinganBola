package com.multazamgsd.pertandinganbola.match

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.detail.DetailActivity
import com.multazamgsd.pertandinganbola.model.Events
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.sdk25.listeners.textChangedListener
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MatchFragment : Fragment(), AnkoComponent<Context>, MatchView {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerViewMatch: RecyclerView
    private var mList: MutableList<Events> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var previousAdapter: PreviousAdapter
    private lateinit var nextAdapter: NextAdapter
    private lateinit var etSearchMatch: EditText
    private lateinit var spinnerMatch: Spinner
    private lateinit var leagueName: String
    private lateinit var leagueID: String

    private var isNext: Boolean = false

    private lateinit var btPrevious: Button
    private lateinit var btNext: Button

    private val TAG: String = "MatchFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        leagueName = "English Premier League"
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerMatch.adapter = spinnerAdapter

        isNext = false
        checkNextMatchOrPrev()
        etSearchMatch.clearFocus()

        val request = ApiRepository()
        val gson = Gson()
        previousAdapter = PreviousAdapter(ctx, mList) {
            startActivity<DetailActivity>(
                    "event_id" to it.idEvent
            )
        }
        nextAdapter = NextAdapter(ctx, mList){
            startActivity<DetailActivity>(
                    "event_id" to it.idEvent
            )
        }
        recyclerViewMatch.adapter = previousAdapter

        presenter = MatchPresenter(this, request, gson)
        swipeRefresh.onRefresh {
            presenter.getPreviousMatchList("4328")
        }

        spinnerMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                swipeRefresh.isRefreshing = true
                leagueName = spinnerMatch.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> leagueID = "4328"
                    "English League Championship" -> leagueID = "4329"
                    "German Bundesliga" -> leagueID = "4331"
                    "Italian Serie A" -> leagueID = "4332"
                    "French Ligue 1" -> leagueID = "4334"
                    "Spanish La Liga" -> leagueID = "4335"
                }
                if(isNext){
                    presenter.getNextMatchList(leagueID)
                }else{
                    presenter.getPreviousMatchList(leagueID)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        etSearchMatch.textChangedListener {
            afterTextChanged {
                swipeRefresh.isRefreshing = true
                presenter.getMatchSearch(it.toString())
            }
        }

        btPrevious.onClick {
            swipeRefresh.isRefreshing = true
            isNext = false
            checkNextMatchOrPrev()
            presenter.getPreviousMatchList(leagueID)
        }

        btNext.onClick {
            swipeRefresh.isRefreshing = true
            isNext = true
            checkNextMatchOrPrev()
            presenter.getNextMatchList(leagueID)
        }

        swipeRefresh.isRefreshing = true
        presenter.getPreviousMatchList("4328")
    }

    private fun checkNextMatchOrPrev() {
        Log.d(TAG, "checkNextMatchOrPrev: isNext: $isNext")
        if(isNext){
            btNext.setBackgroundColor(resources.getColor(R.color.colorAccent))
            btNext.setTextColor(resources.getColor(android.R.color.white))
            btPrevious.setBackgroundColor(android.R.drawable.btn_default)
            btPrevious.setTextColor(resources.getColor(android.R.color.black))
        }else{
            btNext.setBackgroundColor(android.R.drawable.btn_default)
            btNext.setTextColor(resources.getColor(android.R.color.black))
            btPrevious.setBackgroundColor(resources.getColor(R.color.colorAccent))
            btPrevious.setTextColor(resources.getColor(android.R.color.white))
        }
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            linearLayout{
                orientation = LinearLayout.VERTICAL
                padding = dip(16)

                etSearchMatch = editText{
                    hint = "Search match"
                    background = resources.getDrawable(R.drawable.searchfield)
                }.lparams(width = matchParent, height = wrapContent)
                spinnerMatch = spinner{
                    id = R.id.spinnerMatch
                }

                linearLayout{
                    lparams(width = matchParent, height = wrapContent)
                    weightSum = 10f
                    orientation = LinearLayout.HORIZONTAL

                    btPrevious = button{
                        text = "Past Match"
                    }.lparams{
                        weight = 5f
                    }

                    btNext = button{
                        id = R.id.btNext
                        text = "Next Match"
                        isNext = true
                        onCheckIsTextEditor()
                    }.lparams{
                        weight = 5f
                    }
                }
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recyclerViewMatch = recyclerView {
                        id = R.id.recyclerViewMatch
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

    override fun showPreviousMatchList(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        mList.clear()
        mList.addAll(data)
        recyclerViewMatch.adapter = previousAdapter
        previousAdapter.notifyDataSetChanged()
    }

    override fun showNextMatchList(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        mList.clear()
        mList.addAll(data)
        recyclerViewMatch.adapter = nextAdapter
        nextAdapter.notifyDataSetChanged()
    }

    override fun showSearchMatchList(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        mList.clear()
        mList.addAll(data)
        recyclerViewMatch.adapter = nextAdapter
        nextAdapter.notifyDataSetChanged()
    }
}

