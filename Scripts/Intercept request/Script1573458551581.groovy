import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.github.kklisura.cdt.protocol.commands.Fetch as Fetch
import com.github.kklisura.cdt.protocol.commands.Page as Page
import com.github.kklisura.cdt.services.ChromeDevToolsService as ChromeDevToolsService
import com.katalon.cdp.CdpUtils as CdpUtils
import com.kms.katalon.core.util.internal.Base64 as Base64
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

ChromeDevToolsService cdts = CdpUtils.getService()

Page page = cdts.getPage()

Fetch fetch = cdts.getFetch()

fetch.onRequestPaused({ def requestIntercepted ->
        String interceptionId = requestIntercepted.getRequestId()

        String url = requestIntercepted.getRequest().getUrl()

        boolean isMocked = url.contains('api.php')

        String response = '["Katalon Studio",["Katalon Studio"],["Katalon Studio is an automation testing solution developed by Katalon LLC."],["https://en.wikipedia.org/wiki/Katalon_Studio"]]'

        String rawResponse = Base64.encode(response)

        System.out.printf('%s - %s%s', isMocked ? 'MOCKED' : 'CONTINUE', url, System.lineSeparator())

        if (isMocked) {
            fetch.fulfillRequest(interceptionId, 200, new ArrayList(), rawResponse, null)
        } else {
            fetch.continueRequest(interceptionId)
        }
    })

fetch.enable()

page.enable()

WebUI.navigateToUrl('https://en.wikipedia.org/wiki/Main_Page')

WebUI.setText(findTestObject('Page_Wikipedia the free encyclopedia/Search input'), 'Intercept request')

WebUI.waitForElementVisible(findTestObject('Page_Wikipedia the free encyclopedia/Select Katalon'), 10)

WebUI.click(findTestObject('Page_Wikipedia the free encyclopedia/Select Katalon'))

WebUI.waitForPageLoad(10)

WebUI.closeBrowser()