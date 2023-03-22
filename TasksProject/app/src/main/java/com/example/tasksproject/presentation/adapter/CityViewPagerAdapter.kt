package com.example.tasksproject.presentation.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tasksproject.db.model.CityModel
import com.example.tasksproject.presentation.screen.fragment.ItemPageFragment

class CityViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragmentList: ArrayList<ItemPageFragment> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = fragmentList.size

    fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCities(cities: List<CityModel?>?) {
        if (cities != null) {
            for(i in cities.indices) {
                cities[i].let {
                    fragmentList.add(ItemPageFragment(it))
                }
            }
        }
        notifyDataSetChanged()
    }
}

/*
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CityViewHolder(
                    ItemPageBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         (holder as? CityViewHolder)?.bindItem(dataList[position])
    }

    inner class CityViewHolder(private val binding: ItemPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        suspend fun bindItem(item: WeatherDataModel) {
            bind(binding, item)
        }
    }

    suspend fun bind(binding: ItemPageBinding, item: WeatherDataModel) {
        with(binding) {
            withContext(coroutineContext) {
                runCatching {
                    DataDependency.getWeatherByCityNameUseCase(item.city)
                }.onSuccess {
                    tvCity.text = item.city
                    tvTemp.text = item.temperature.toString()
                    Glide.with(ivIcon.context)
                        .load("https://openweathermap.org/img/wn/" + item.icon + ".png")
                        .into(ivIcon)
                }.onFailure {

                }
            }
        }
    }
*/