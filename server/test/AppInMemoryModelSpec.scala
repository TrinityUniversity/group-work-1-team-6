import org.scalatestplus.play.PlaySpec
import models.AppInMemoryModel
import edu.trinity.videoquizreact.controllers.Application
import play.api.test.Helpers
import play.api.test.FakeRequest
import play.api.test.Helpers._
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory


class AppInMemoryModel extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "AppInMemoryModel" must {
        "validate user" in {
            AppInMemoryModel.validateUser("web", "apps") mustBe (true)
        }
        "reject invalid username" in {
            AppInMemoryModel.validateUser("wob", "apps") mustBe (false)
        }
        "reject invalid password" in {
            AppInMemoryModel.validateUser("web", "appy") mustBe (false)
        }
    }

    "Application" must { // Non-selenium
        val controller = new Application(Helpers.stubControllerComponents())
        "return expected login" in {
            val result = controller.login.apply(FakeRequest())
            val bdy = contentAsString(result)
            bdy must include ("Post Form")
            bdy must include ("GET Form")
        }

        "login" in {
            go to s"http://localhost:$port/login"
            pageTitle mustBe "Login"
            click on id("PostUser")
            textField(id("PostUser")).value = "web"
            click on id("passID")
            pwdField(id("passID")).value = "apps"
            submit()
            eventually {
                pageTitle mustBe "Logged in"
                find(cssSelector("h2")).foreach( title => title.text mustBe "Welcome web")
            }
            
        }
    }
}