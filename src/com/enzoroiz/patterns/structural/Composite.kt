package com.enzoroiz.patterns.structural

fun main(args: Array<String>) {
    val dieHard1 = Movie("Die Hard", 1.8)
    val dieHard2 = Movie("Die Hard 2", 1.5)
    val dieHard3 = Movie("Die Hard 3", 2.2)
    val dieHard4 = Movie("Die Hard 4", 1.9)
    val dieHard5 = Movie("Die Hard 5", 2.4)
    val actionMovies = MoviePlaylist("Action Movies")
            .add(dieHard1)
            .add(dieHard2)
            .add(dieHard3)
            .add(dieHard4)
            .add(dieHard5)

    actionMovies.play()
    println("This Movie / MoviePlaylist time is ${actionMovies.duration} hours")
    println("############################")

    val spiderMan1 = Movie("Spider Man", 1.5)
    val spiderMan2 = Movie("Spider Man 2", 1.8)
    val spiderMan3 = Movie("Spider Man 3", 2.5)
    val spiderManMovies = MoviePlaylist("Spider Man Movies")
            .add(spiderMan1)
            .add(spiderMan2)
            .add(spiderMan3)

    val batman1 = Movie("Batman", 2.0)
    val batman2 = Movie("Batman 2", 2.4)
    val batmanMovies = MoviePlaylist("Batman Movies")
            .add(batman1)
            .add(batman2)

    val superHeroMovies = MoviePlaylist("Super Hero Movies")
            .add(spiderManMovies)
            .add(batmanMovies)

    superHeroMovies.play()
    println("This Movie / MoviePlaylist time is ${superHeroMovies.duration} hours")
    println("############################")

    superHeroMovies.remove(spiderManMovies)
    superHeroMovies.play()
    println("This Movie / MoviePlaylist time is ${superHeroMovies.duration} hours")
    println("############################")

    val romanticDramaMovies = MoviePlaylist("Romantic Drama Movies")
    val romanticMovies = MoviePlaylist("Romantic Movies").add(romanticDramaMovies)

    romanticMovies.play()
    println("This Movie / MoviePlaylist time is ${romanticMovies.duration} hours")
}

// Pattern useful for when you should be able to treat an object and a composition of objects in the same way
// Movie / Movie Playlist both have a duration and can be played

interface MoviePlayer {
    val duration: Double
    fun play()
}

class Movie(private val name: String, override val duration: Double): MoviePlayer {
    override fun play() {
        println("Playing Movie - $name")
    }
}

class MoviePlaylist(private val name: String): MoviePlayer {
    private val moviePlaylist = mutableListOf<MoviePlayer>()

    override val duration: Double
        get() = moviePlaylist.map { it.duration }.sum()

    override fun play() {
        if (moviePlaylist.isEmpty()) {
            println("The Playlist $name was created, but it's empty. Add some movies to it.")
        } else {
            println("Playing Playlist - $name")
            moviePlaylist.first().play()
        }
    }

    fun add(moviePlayer: MoviePlayer) = apply { moviePlaylist.add(moviePlayer) }

    fun remove(moviePlayer: MoviePlayer) = apply { moviePlaylist.remove(moviePlayer) }
}