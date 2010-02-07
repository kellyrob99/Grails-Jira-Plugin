import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.codehaus.groovy.grails.commons.ControllerArtefactHandler
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.kar.grails.jira.JiraXmlRpcService
import org.kar.grails.jira.JiraXmlRpcServiceHelper
import org.kar.grails.jira.RpcProxyService

class JiraPluginGrailsPlugin
{
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.0.RC1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
    ]

    def watchedResources = "file:./grails-app/controllers/**/*Controller.groovy"

    def author = "Kelly Robinson"
    def authorEmail = "kellyrob@gmail.com"
    def title = "XML-RPC client for Jira 4."
    def description = '''\\
Implementation of an XML-RPC client for Jira 4.
'''

    // URL to the plugin's documentation
    def documentation = 'no documentation available'//"http://grails.org/plugin/jira-plugin"

    def doWithWebDescriptor = {xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = {ctx ->
        processArtifacts()
    }

    def doWithApplicationContext = {applicationContext ->
    }

    def onChange = {event ->
        processArtifacts()
    }

    def onConfigChange = {event ->
        processArtifacts()
    }

    private processArtifacts()
    {
        def config = ConfigurationHolder.config
        def application = ApplicationHolder.application
        def types = config.grails?.jira?.injectInto ?: ["Controller"]
        types.each {type ->
            application.getArtefacts(type).each {klass ->
                addDynamicMethods(klass)
            }
        }
    }

    /**
     * Adds the 'withJira' method to a controller class.
     */
    def addDynamicMethods = {cClass ->
        println "adding withJira method to $cClass"
        cClass.metaClass.withJira = {String hostname, Closure closure ->
            if (closure)
            {
                closure.delegate = new JiraXmlRpcServiceHelper(hostname: hostname, service: new JiraXmlRpcService(rpcProxyService: new RpcProxyService()))
                closure.resolveStrategy = Closure.DELEGATE_FIRST
                closure()
            }
        }
    }
}