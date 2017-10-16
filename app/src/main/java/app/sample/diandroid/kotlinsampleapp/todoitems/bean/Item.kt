package app.sample.diandroid.kotlinsampleapp.todoitems.bean


class Item {

    var title: String? = null

    constructor() {}

    constructor(title: String) {
        this.title = title
    }
}
