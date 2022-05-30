package com.example.cookingsocialnetwork.main.fragment.profile

class PostItem {
    var picture: Int? = 0
    var name: String? = null
    var timetomake: String? = null
    var description: String? = null
    var rating: Int? = 0

    constructor(picture: Int?, name: String?, timetomake: String?, description: String?, rating: Int?) {
        this.name = name
        this.timetomake = timetomake
        this.description = description
        this.rating = rating
        this.picture = picture
    }
}