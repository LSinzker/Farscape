package GoL

import Rules.NewSetup

import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.GridPane


class PopupW {

  case class Result(letslive: String, kills: String, revives: String)

  val dialog = new Dialog[Result] {
    initOwner(View.stage)
    title = "Your rule"
    headerText = "Number of Neighbours to:"
    contentText = "Stay Alive: "
    contentText = "Revive: "
  }

  val finishButtonType = new ButtonType("Finish", ButtonData.OKDone)
  dialog.dialogPane().getButtonTypes.addAll(finishButtonType, ButtonType.Cancel)

  val toLive = new TextField(){promptText = "Stay Alive"}
  val toDie = new TextField(){promptText = "Die"}
  val toRevive = new TextField(){promptText = "Revive"}

  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(30, 100, 10, 10)

    add(new Label("Stay Alive:"), 0, 0)
    add(toLive, 1, 0)
    add(new Label("Die:"), 0, 1)
    add(toDie, 1, 1)
    add(new Label("Revive:"), 0, 2)
    add(toRevive, 1, 2)
  }

  dialog.dialogPane().setContent(grid)

  Platform.runLater(toLive.requestFocus())

  dialog.resultConverter = dialogButton =>
    if (dialogButton == finishButtonType) Result(toLive.text(), toDie.text(), toRevive.text())
    else null

  val result = dialog.showAndWait()

  result match  {
    case Some(Result(live, die, revive))       => {
      println("Stay Alive: " + live + " Dies: " + die + " Revive: " + revive)
      GameEngine.rule = new NewSetup(live.toInt, die.toInt, revive.toInt)
    }
    case _                                => println("Cancel or closed")
  }

}
