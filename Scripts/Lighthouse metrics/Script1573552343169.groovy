import com.katalon.cdp.CdpUtils
import com.katalon.utils.OsUtils
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('https://www.google.com')

String url = WebUI.getUrl()
def (host, port) = CdpUtils.getServiceEndpoint()

OsUtils.runCommand("lighthouse ${url} --hostname ${host} --port ${port} --no-enable-error-reporting", null, null)

WebUI.closeBrowser()
