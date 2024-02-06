package com.example.tem.home.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tem.GlobalApplication
import com.example.tem.R
import com.example.tem.databinding.FragmentShopBinding
import com.example.tem.home.CustomSpinnerImageAdapter
import com.example.tem.home.HomeFragment
import com.example.tem.home.model.SearchData


class ShopFragment : Fragment(),AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener  {

    lateinit var binding: FragmentShopBinding
    private lateinit var searchHistoryRVAdapter: ShopSearchHistoryRVAdapter
    private var searchHistoryList =  ArrayList<SearchData>()

    // 쿠팡, 네이버 구분
    private var classNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentShopBinding.inflate(layoutInflater)


        //검색 기록 가져오기
        searchHistoryList = GlobalApplication.prefManager.getSearchHistoryList() as ArrayList<SearchData>

        //검색 기록 Rv 준비
        searchHistoryRecylerViewSetting(searchHistoryList)
        binding.shopSv.setOnQueryTextListener(this@ShopFragment)

        //Spinner 설정
        spinnerSetting()

        //실시간 검색 순위 Rv 준비
        recentSearchRankRecyclerViewSetting()

        //검색 창 UI
        handleSearchViewUi()

        binding.shopBackIv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }


        return binding.root
    }

    private fun goLink(title: String){
        var fragment = ShopInternetFragment()
        var bundle = Bundle()
        bundle.putString("url", title)
        bundle.putInt("number", classNumber)
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }


    private fun spinnerSetting(){
        var shopdata: ArrayList<Int> = ArrayList()
        shopdata.add(R.drawable.coopang)
        shopdata.add(R.drawable.naver)
        var customSpinnerImageAdapter = CustomSpinnerImageAdapter(requireActivity().baseContext, shopdata)
        binding.spinner.onItemSelectedListener = this
        binding.spinner.adapter = customSpinnerImageAdapter

    }

    private fun recentSearchRankRecyclerViewSetting(){
        var items: ArrayList<String> = ArrayList()
        items.add("맛좋은 바나나 떡")
        items.add("고구마 빵")
        items.add("감자빵")
        val shopRVAdapter = ShopSearchRVAdapter(items)
        shopRVAdapter.setMyItemClickListener(object: ShopSearchRVAdapter.MyItemClickListener{
            override fun onClick(title: String) {
                goLink(title)
            }
        })

        binding.shopRv.apply {
            adapter = shopRVAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }


    private fun searchHistoryRecylerViewSetting(searchHistoryList: ArrayList<SearchData>){
        searchHistoryRVAdapter = ShopSearchHistoryRVAdapter()
        searchHistoryRVAdapter.setMyItemClickListener(object: ShopSearchHistoryRVAdapter.MyItemClickListener{
            //검색 기록 삭제 클릭 시
            override fun deleteItem(position: Int) {
                // 해당 요소 삭제
                searchHistoryList.removeAt(position)
                // 데이터 덮어쓰기
                GlobalApplication.prefManager.storeSearchHistoryList(searchHistoryList)
                // 데이터 변경 됬다고 알려줌
                searchHistoryRVAdapter.notifyDataSetChanged()
                handleSearchViewUi()
            }

            //해당 검색 기록 클릭 시
            override fun onClick(position: Int) {
                val query = searchHistoryList[position].searchText
                insertSearchTermHistory(query)
                //해당 링크로 이동
                goLink(query)
            }
        })

        //전체 기록 삭제 클릭 시
        binding.shopSearchDeleteAllTv.setOnClickListener {
            GlobalApplication.prefManager.clearSearchHistoryList()
            searchHistoryList.clear()
            // ui 처리
            handleSearchViewUi()
        }

        searchHistoryRVAdapter.submitList(searchHistoryList)
        var linear = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        linear.stackFromEnd = true

        binding.shopSearchHistoryRv.apply {
            this.scrollToPosition(searchHistoryRVAdapter.itemCount-1)
            this.layoutManager = linear
            adapter = searchHistoryRVAdapter
        }

    }


    //검색 버튼이 클릭되었을때
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("onQueryTextSubmit", "onQueryTextSubmit() called / query: $query")
        if(!query.isNullOrEmpty()){
            insertSearchTermHistory(query)
            goLink(query)
            binding.shopSv.setQuery("", false)
            handleSearchViewUi()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("onQueryTextChange", "onQueryTextSubmit() called / query: $newText")
        return true
    }

    // 검색어 저장
    private fun insertSearchTermHistory(searchTerm: String){
        // 중복 아이템 삭제
        var indexListToRemove = ArrayList<Int>()
        searchHistoryList.forEachIndexed{ index, searchDataItem ->
            if(searchDataItem.searchText == searchTerm){
                indexListToRemove.add(index)
            }
        }

        indexListToRemove.forEach {
            this.searchHistoryList.removeAt(it)
        }

        // 새 아이템 넣기
        val newSearchData = SearchData(searchText = searchTerm)
        searchHistoryList.add(newSearchData)

        // 기존 데이터에 덮어쓰기
        GlobalApplication.prefManager.storeSearchHistoryList(searchHistoryList)

        searchHistoryRVAdapter.notifyDataSetChanged()

    }


    //UI 업데이트
    private fun handleSearchViewUi(){
        Log.d("searchCount", "handleSearchViewUi() called / size : ${this.searchHistoryList.size}")

        if(this.searchHistoryList.size > 0){
            binding.shopSearchHistoryRv.visibility = View.VISIBLE
            binding.shopSearchRecentTv.visibility = View.VISIBLE
            binding.shopSearchDeleteAllTv.visibility = View.VISIBLE
        } else {
            binding.shopSearchHistoryRv.visibility = View.GONE
            binding.shopSearchRecentTv.visibility = View.GONE
            binding.shopSearchDeleteAllTv.visibility = View.GONE
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        classNumber = p2
        Log.d("onItemSelected", p2.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}