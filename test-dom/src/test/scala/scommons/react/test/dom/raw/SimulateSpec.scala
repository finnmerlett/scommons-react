package scommons.react.test.dom.raw

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils

import scala.scalajs.js

class SimulateSpec extends TestSpec with TestDOMUtils {

  it should "simulate onClick event" in {
    //given
    var clicked = false
    var clientX = 0
    var clientY = 0
    def onClick() = { (e: MouseSyntheticEvent) =>
      clicked = true
      clientX = e.clientX
      clientY = e.clientY
    }
    domRender(<.button(^.onClick := onClick)("Click me"))
    val button = domContainer.querySelector("button")

    //when
    fireDomEvent(Simulate.click(button, js.Dynamic.literal(
      clientX = 1,
      clientY = 2
    )))

    //then
    clicked shouldBe true
    clientX shouldBe 1
    clientY shouldBe 2
  }
}
