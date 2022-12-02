package gg.dsepractice.chinese.frontend.sample

data class QuestionModel(
    val question: String,
    val option1:String,
    val option2:String,
    val option3:String,
    val option4:String,
    val answer:String)


data class Text(
    val origin: String,
    val translate: String
)

data class UserModel(
    var userName: String,
    var userId: Int,
    var password: String,
)

data class Schedule(
    var userId: Int,
    var date: String,
    var toDo: String,
    var done: String
)


