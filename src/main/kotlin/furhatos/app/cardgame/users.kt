package furhatos.app.cardgame

import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.flow.kotlin.UserDataDelegate
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.users
import furhatos.records.User
import furhatos.skills.UserManager
import org.apache.commons.csv.CSVFormat
import java.io.InputStreamReader
import java.net.URL

val User.name : String?
    get() {
        val index = UserManager.leftToRight.indexOf(this)
        if (index == -1 || index >= userNames.size) {
            return null
        } else {
            return userNames[index]
        }
    }

var User.responses : Int by NullSafeUserDataDelegate({0})

var userNames = listOf<String>()

fun fetchNames() {
    val formUrl =
        "https://docs.google.com/spreadsheets/d/1hoxpe4Ib_OD33xJPs4NLXq4NJrtNeu73OK5J7ahpQlk/gviz/tq?tqx=out:csv"
    val records =
        CSVFormat.EXCEL.parse(InputStreamReader(URL(formUrl).openStream()))
    userNames = records.mapNotNull { column ->
        if (column.size() > 0) {
            column[0].trim()
        } else null
    }
}

/**
 * Get a list of users, left-to-right, from the users' perspective
 */
val UserManager.leftToRight
    get() = list.sortedBy { it.head.location.x }

/**
 * Return the non-dominant user, if any
 */
val UserManager.nonDominant: User?
    get() {
        val sorted = list.sortedBy { it.responses }
        if (sorted.size > 1) {
            if (sorted[1].responses - sorted[0].responses > 1) {
                return sorted[0]
            } else {
                return null
            }
        } else {
            return null
        }
    }
