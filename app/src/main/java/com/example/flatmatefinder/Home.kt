package com.example.flatmatefinder

import android.R
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.flatmatefinder.Utils.Constants.TAG
import com.example.flatmatefinder.Utils.NetworkResult
import com.example.flatmatefinder.adaptors.FlatCardAdaptor
import com.example.flatmatefinder.api.MainAPI
import com.example.flatmatefinder.databinding.FragmentHomeBinding
import com.example.flatmatefinder.models.Address
import com.example.flatmatefinder.models.FlatCardInfo
import com.example.flatmatefinder.Utils.TokenManager
import com.example.flatmatefinder.models.FlatInfo
import com.example.flatmatefinder.models.Like_Dislike
import com.example.flatmatefinder.models.ProfileImage
import com.example.flatmatefinder.models.Rent
import com.example.flatmatefinder.viewModels.MainViewModel
import com.google.ads.interactivemedia.pal.utils.Duration
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var mainAPI: MainAPI

    @Inject
    lateinit var tokenManager: TokenManager

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var manager: CardStackLayoutManager
    private var flatCardInfo: FlatCardInfo? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        Log.d(TAG, "onCreateView: ${tokenManager.getToken().toString()}")
        return binding.root
    }

    private fun like_dislike(s: String, id: String) {
        if (s.equals("l")) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = mainAPI.dislikeFlats(Like_Dislike(id))
                Log.d("Cutie", "like_dislike:" + response.body())
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val response = mainAPI.addLike(Like_Dislike(id))
                Log.d("Cutie", "like_dislike:" + response.body())
            }
        }
    }

    private fun init() {
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {
                if (manager!!.topPosition == flatCardInfo!!.flats.size) {
                    binding.endListText.isVisible = true
                    var topPosition = manager.topPosition
                    var currentFlatInfoId = flatCardInfo!!.flats[topPosition - 1]._id
                    when (direction) {
                        Direction.Left -> {
                            // Handle left swipe
                            // You can perform specific actions for left swipes
                            like_dislike("l", currentFlatInfoId)
                        }

                        Direction.Right -> {
                            // Handle right swipe
                            // You can perform specific actions for right swipes
                            like_dislike("r", currentFlatInfoId)
                        }

                        else -> {
                            // Handle other directions if needed
                        }
                    }
                    return
                }
                var topPosition = manager.topPosition
                var currentFlatInfoId = flatCardInfo!!.flats[topPosition - 1]._id
                when (direction) {
                    Direction.Left -> {
                        // Handle left swipe
                        // You can perform specific actions for left swipes
                        like_dislike("l", currentFlatInfoId)
                    }

                    Direction.Right -> {
                        // Handle right swipe
                        // You can perform specific actions for right swipes
                        like_dislike("r", currentFlatInfoId)
                    }

                    else -> {
                        // Handle other directions if needed
                    }
                }
            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }


        })
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollVertical(false)
        manager.setCanScrollHorizontal(true)
        manager.setStackFrom(StackFrom.Top)
        manager.setStackFrom(StackFrom.Top)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setStackFrom(StackFrom.None)

    }

    private fun swipeCard(direction: Direction) {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(direction)
            .setDuration(300) // You can adjust the duration as needed
            .build()

        manager.setSwipeAnimationSetting(setting)
        binding.cardStackView.swipe()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getFlats()

        binding.dislike.setOnClickListener {
            swipeCard(Direction.Left)
        }

        binding.like.setOnClickListener {
            swipeCard(Direction.Right)
        }

        bindObservers()
    }

    private fun bindObservers() {
        mainViewModel.getFlatsLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            binding.transparentBg.visibility = View.GONE
            when (it) {
                is NetworkResult.Error -> {
                    if (it.msg == "User not found") {
                        Toast.makeText(requireContext(), it.msg + " Login Again", Toast.LENGTH_SHORT)
                            .show()
                        tokenManager.saveToken("")
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        (activity as MainActivity).finish()
                    }
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.transparentBg.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.VISIBLE

                }

                is NetworkResult.Success -> {
                    flatCardInfo = it.data

                    val flatCardInfoList = flatCardInfo?.flats
                    if (flatCardInfoList!!.isEmpty()) {
                        binding.listSizeZero.isVisible = true
                    }
                    init()
                    val adapter = FlatCardAdaptor(requireContext(), flatCardInfoList!!)
                    binding.cardStackView.layoutManager = manager
                    binding.cardStackView.itemAnimator.apply {
                        if (this is DefaultItemAnimator) {
                            supportsChangeAnimations = false
                        }
                    }
                    Log.e(TAG, "bindObservers: " + flatCardInfoList)
                    binding.cardStackView.adapter = adapter
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}