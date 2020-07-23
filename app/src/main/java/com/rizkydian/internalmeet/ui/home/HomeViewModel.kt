package com.rizkydian.internalmeet.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.rizkydian.internalmeet.datasource.repository.MeetingRepository

class HomeViewModel @ViewModelInject constructor(private val meetingRepository: MeetingRepository) : ViewModel() {
}