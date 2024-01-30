package com.example.tem.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tem.BookmarkFir
import com.example.tem.BookmarkSec
import com.example.tem.R
import com.example.tem.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding
    private fun changeFragment(fragment: Fragment, title: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }
    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        // 화면에 표시할 Fragment 리스트
        val pageList = listOf(
            BookmarkFir(),
            BookmarkSec()
        )

        override fun getItemCount(): Int {
            return pageList.size
        }

        override fun createFragment(position: Int): Fragment {
            return pageList[position]
        }

    }
    private val pagerAdpater by lazy {
        ViewPagerAdapter(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBookmarkBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backBtn.setOnClickListener {
            changeFragment(HomeFragment(),"1")
        }
        binding.bmarkVp.apply {
            adapter = pagerAdpater
            // 사용자의 Swipe 동작에 의해 화면이 이동하지 않도록 설정
            isUserInputEnabled = true
            /*
             * Viewpager2의 메모리 상에 Load시켜주는 화면 숫자 설정
             * 해당 예시는 화면의 숫자만큼 Load하도록 설정했다.
             * 메모리 관리에 유의하여 사용해야 한다.
            */
            offscreenPageLimit = pagerAdpater.itemCount
        }
        return view
    }
}