import com.katalon.cdp.CdpUtils
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

def cdts = CdpUtils.getService()

def network = cdts.getNetwork()
network.enable()

def performance = cdts.getPerformance()
performance.enable()

network.onLoadingFinished({ event ->
    def metrics = performance.getMetrics()

    try {
        metrics.each { metric ->
            println("${metric.getName()}: ${metric.getValue()}")
        }
        cdts.close()
        cdts.getPage().close();

        WebUI.closeBrowser()
    } catch (Exception e) {
        e.printStackTrace()
    }
})

WebUI.navigateToUrl('https://www.google.com')
