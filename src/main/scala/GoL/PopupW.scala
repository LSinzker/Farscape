package GoL

import Rules.NewSetup

import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.GridPane


class PopupW {

  case class Result(minimum: String, maximum: String)

  val dialog = new Dialog[Result] {
    initOwner(View.stage)
    title = "Your rule"
    headerText = "Set limits:"
    contentText = "Enter minimum: "
    contentText = "Enter maximum: "
  }

  val finishButtonType = new ButtonType("Finish", ButtonData.OKDone)
  dialog.dialogPane().getButtonTypes.addAll(finishButtonType, ButtonType.Cancel)

  val minimum = new TextField(){promptText = "Minimum"}

  val maximum = new TextField(){promptText = "Maximum"}

  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 100, 10, 10)

    add(new Label("Minimum:"), 0, 0)
    add(minimum, 1, 0)
    add(new Label("Maximum:"), 0, 1)
    add(maximum, 1, 1)
  }

  dialog.dialogPane().setContent(grid)

  Platform.runLater(minimum.requestFocus())

  dialog.resultConverter = dialogButton =>
    if (dialogButton == finishButtonType) Result(minimum.text(), maximum.text())
    else null

  val result = dialog.showAndWait()

  result match  {
    case Some(Result(min, max))       => {
      println("minimum: " + min + " maximum: " + max)
      GameEngine.rule = new NewSetup(min.toInt, max.toInt)
    }
    case _             => println("Cancel or closed")
  }

}
