package com.example.socialmediaapplication.datamodel

data class User (
    val userEmail:String=" ",
    val userProfileImg:String=" ",
    val lisOfFollowing:List<String> = listOf(),
    val listOfTweets:List<String> = listOf(),
    val uid:String=" "
)
