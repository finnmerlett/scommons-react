package scommons.react.showcase.dom

import org.scalajs.dom.{Element, document}
import scommons.react._
import scommons.react.test._
import scommons.react.test.dom._

class ReactPortalDemoSpec extends TestSpec with TestDOMUtils {

  it should "render component in dom" in {
    //when & then
    domRender(<(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = true))(
      "Portal Child"
    ))

    assertDOMElement(domContainer, <.div()())
    assertDOMElement(document.body.querySelector(".portal-content"),
      <.div(^("class") := "portal-content")(
        "Portal Child"
      )
    )
    
    //when & then
    domRender(<(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = false))())

    document.body.querySelectorAll(".portal-content").length shouldBe 0
  }
  
  it should "re-render portal content" in {
    //when & then
    domRender(<(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = true))(
      "Portal Child"
    ))
    assertDOMElement(domContainer, <.div()())
    assertDOMElement(document.body.querySelector(".portal-content"),
      <.div(^("class") := "portal-content")(
        "Portal Child"
      )
    )
    
    //when & then
    domRender(<(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = true))(
      "Portal Updated"
    ))
    assertDOMElement(document.body.querySelector(".portal-content"),
      <.div(^("class") := "portal-content")(
        "Portal Updated"
      )
    )
    
    //when & then
    domRender(<(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = false))())
    document.body.querySelectorAll(".portal-content").length shouldBe 0
  }
  
  it should "render several portals" in {
    //given
    val comp = new FunctionComponent[(Boolean, Boolean)] {
      protected def render(props: Props): ReactElement = {
        val (show1, show2) = props.wrapped
        
        <.>()(
          if (show1) Some(
            <(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = show1))(
              "Portal 1"
            )
          ) else None,
          
          if (show2) Some(
            <(ReactPortalDemo())(^.wrapped := ReactPortalDemoProps(show = show2))(
              "Portal 2"
            )
          ) else None
        )
      }
    }

    //when & then
    domRender(<(comp())(^.wrapped := Tuple2(true, false))())
    domRender(<(comp())(^.wrapped := Tuple2(true, true))())

    assertDOMElement(domContainer, <.div()())
    
    val portals = document.body.querySelectorAll(".portal-content")
    assertDOMElement(portals.item(0).asInstanceOf[Element],
      <.div(^("class") := "portal-content")(
        "Portal 1"
      )
    )
    assertDOMElement(portals.item(1).asInstanceOf[Element],
      <.div(^("class") := "portal-content")(
        "Portal 2"
      )
    )
    
    //when & then
    domRender(<(comp())(^.wrapped := Tuple2(false, false))())
    document.body.querySelectorAll(".portal-content").length shouldBe 0
  }
}
