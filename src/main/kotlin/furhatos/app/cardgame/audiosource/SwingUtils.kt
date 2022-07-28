package furhatos.app.cardgame.audiosource

import java.awt.BorderLayout
import java.awt.Container
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel

fun panel(builder: JPanel.()->Unit) = JPanel(BorderLayout()).apply {builder()}

fun Container.addCenter(component: JComponent) {
    add(component, BorderLayout.CENTER)
}

fun Container.addWest(component: JComponent) {
    add(component, BorderLayout.WEST)
}

fun Container.addSouth(component: JComponent) {
    add(component, BorderLayout.SOUTH)
}

fun Container.addNorth(component: JComponent) {
    add(component, BorderLayout.NORTH)
}

fun Container.addEast(component: JComponent) {
    add(component, BorderLayout.EAST)
}

fun button(label: String, action: JButton.() -> Unit) = JButton(label).apply {
    addActionListener {
        action()
    }
}

fun comboBox(items: List<String>, action: JComboBox<String>.()->Unit) = JComboBox<String>(items.toTypedArray()).apply {
    addActionListener {
        if (it.actionCommand == "comboBoxChanged") {
            action()
        }
    }
}
