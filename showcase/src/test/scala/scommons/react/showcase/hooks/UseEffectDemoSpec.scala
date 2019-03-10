package scommons.react.showcase.hooks

import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils
import scommons.react.test.raw.TestRenderer
import scommons.react.test.util.{ShallowRendererUtils, TestRendererUtils}

class UseEffectDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "render component in dom" in {
    //given
    val comp = new UseEffectDemo(
      afterEveryRender = () => (),
      onlyOnce = () => () => (),
      whenDependenciesChange = () => () => ()
    )
    val props = UseEffectDemoProps(1, "2")
    
    //when
    domRender(<(comp())(^.wrapped := props)())
    
    //then
    assertDOMElement(domContainer, <.div()(
      <.div()(s"a: ${props.a}, b: ${props.b}")
    ))
  }
  
  it should "shallow render component" in {
    //given
    val comp = new UseEffectDemo(
      afterEveryRender = () => (),
      onlyOnce = () => () => (),
      whenDependenciesChange = () => () => ()
    )
    val props = UseEffectDemoProps(1, "2")

    //when
    val result = shallowRender(<(comp())(^.wrapped := props)())

    //then
    assertNativeComponent(result,
      <.div()(s"a: ${props.a}, b: ${props.b}")
    )
  }
  
  it should "test render component" in {
    //given
    val comp = new UseEffectDemo(
      afterEveryRender = () => (),
      onlyOnce = () => () => (),
      whenDependenciesChange = () => () => ()
    )
    val props = UseEffectDemoProps(1, "2")

    //when
    val result = testRender(<(comp())(^.wrapped := props)())

    //then
    assertNativeComponent(result,
      <.div()(s"a: ${props.a}, b: ${props.b}")
    )
  }

  it should "not call effect func if dependencies haven't changed" in {
    //given
    var afterEveryRenderCalled = false
    var onlyOnceCalled = false
    var onlyOnceCleanup = false
    var whenDependenciesChangeCalled = false
    var whenDependenciesChangeCleanup = false
    val comp = new UseEffectDemo(
      afterEveryRender = { () =>
        afterEveryRenderCalled = true
      },
      onlyOnce = { () =>
        onlyOnceCalled = true
        () => {
          onlyOnceCleanup = true
        }
      },
      whenDependenciesChange = { () =>
        whenDependenciesChangeCalled = true
        () => {
          whenDependenciesChangeCleanup = true
        }
      }
    )
    val props = UseEffectDemoProps(1, "2")
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)())
    afterEveryRenderCalled shouldBe true
    onlyOnceCalled shouldBe true
    onlyOnceCleanup shouldBe false
    whenDependenciesChangeCalled shouldBe true
    whenDependenciesChangeCleanup shouldBe false
    afterEveryRenderCalled = false
    onlyOnceCalled = false
    whenDependenciesChangeCalled = false

    //when
    TestRenderer.act { () =>
      renderer.update(<(comp())(^.wrapped := props)())
    }

    //then
    afterEveryRenderCalled shouldBe true
    onlyOnceCalled shouldBe false
    onlyOnceCleanup shouldBe false
    whenDependenciesChangeCalled shouldBe false
    whenDependenciesChangeCleanup shouldBe false

    afterEveryRenderCalled = false
    onlyOnceCalled = false
    onlyOnceCleanup = false
    whenDependenciesChangeCalled = false
    whenDependenciesChangeCleanup = false

    //when
    TestRenderer.act { () =>
      renderer.unmount()
    }

    //then
    afterEveryRenderCalled shouldBe false
    onlyOnceCalled shouldBe false
    onlyOnceCleanup shouldBe true
    whenDependenciesChangeCalled shouldBe false
    whenDependenciesChangeCleanup shouldBe true
  }

  it should "call effect func if one of dependencies has changed" in {
    //given
    var afterEveryRenderCalled = false
    var onlyOnceCalled = false
    var onlyOnceCleanup = false
    var whenDependenciesChangeCalled = false
    var whenDependenciesChangeCleanup = false
    val comp = new UseEffectDemo(
      afterEveryRender = { () =>
        afterEveryRenderCalled = true
      },
      onlyOnce = { () =>
        onlyOnceCalled = true
        () => {
          onlyOnceCleanup = true
        }
      },
      whenDependenciesChange = { () =>
        whenDependenciesChangeCalled = true
        () => {
          whenDependenciesChangeCleanup = true
        }
      }
    )
    val props = UseEffectDemoProps(1, "2")
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)())
    afterEveryRenderCalled shouldBe true
    onlyOnceCalled shouldBe true
    onlyOnceCleanup shouldBe false
    whenDependenciesChangeCalled shouldBe true
    whenDependenciesChangeCleanup shouldBe false
    afterEveryRenderCalled = false
    onlyOnceCalled = false
    whenDependenciesChangeCalled = false
    val newProps = props.copy(b = "3")

    //when
    TestRenderer.act { () =>
      renderer.update(<(comp())(^.wrapped := newProps)())
    }

    //then
    afterEveryRenderCalled shouldBe true
    onlyOnceCalled shouldBe false
    onlyOnceCleanup shouldBe false
    whenDependenciesChangeCalled shouldBe true
    whenDependenciesChangeCleanup shouldBe true

    assertNativeComponent(renderer.root.children(0),
      <.div()(s"a: ${newProps.a}, b: ${newProps.b}")
    )

    afterEveryRenderCalled = false
    onlyOnceCalled = false
    onlyOnceCleanup = false
    whenDependenciesChangeCalled = false
    whenDependenciesChangeCleanup = false
    
    //when
    TestRenderer.act { () =>
      renderer.unmount()
    }

    //then
    afterEveryRenderCalled shouldBe false
    onlyOnceCalled shouldBe false
    onlyOnceCleanup shouldBe true
    whenDependenciesChangeCalled shouldBe false
    whenDependenciesChangeCleanup shouldBe true
  }
}
