package scommons.react.showcase.hooks

import scommons.react.test._
import scommons.react.test.dom._

class UseReducerDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "increase/decrease counters when onClick" in {
    //given
    domRender(<(UseReducerDemo())()())
    
    val p = domContainer.querySelector("p")
    p.textContent shouldBe "counter1: 0, counter2: 10"
    val button1 = domContainer.querySelector(".counter1")
    val button2 = domContainer.querySelector(".counter2")

    //when & then
    fireDomEvent(Simulate.click(button1))
    domContainer.querySelector("p").textContent shouldBe "counter1: 1, counter2: 10"
    
    //when & then
    fireDomEvent(Simulate.click(button2))
    fireDomEvent(Simulate.click(button1))
    domContainer.querySelector("p").textContent shouldBe "counter1: 2, counter2: 9"
  }
  
  it should "shallow render component" in {
    //given
    val comp = <(UseReducerDemo())()()

    //when
    val result = shallowRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.p()("counter1: 0, counter2: 10"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Decrease counter2")
      )
    )
  }
  
  it should "test render component" in {
    //given
    val comp = <(UseReducerDemo())()()

    //when
    val result = testRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.p()("counter1: 0, counter2: 10"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Decrease counter2")
      )
    )
  }
}
