package org.kar.grails.jira

import grails.test.*
import groovy.net.xmlrpc.XMLRPCServerProxy

/**
 * @author Kelly Robinson
 */
class JiraXmlRpcServiceTests extends GrailsUnitTestCase
{
    def hostname = 'http://192.168.1.3:8080/'

    protected void setUp()
    {
        super.setUp()
    }

    protected void tearDown()
    {
        super.tearDown()
    }

    void testLogin()
    {
        JiraXmlRpcService service = new JiraXmlRpcService()
        service.rpcProxyService = new RpcProxyService()
        def hostname = hostname
        def helper = service.createHelper(hostname)
        helper.with {
            def token = login('charlie', 'charlie')
            println "serverInfo = " + getServerInfo(token).inspect()

            println "priorirites = " + getPriorities(token).inspect()

            def projects = getProjectsNoSchemes(token)
            println "projects = " + projects.inspect()
            projects.each{
                println "project =  ${it.inspect()}"
                def versions = getVersions(token, it.key)
                println "     versions = " + versions.inspect()
                def components = getComponents(token, it.key)
                println "     components = " + components.inspect()

                def issueTypesForProject = getIssueTypesForProject(token, it.id)
                println "     issueTypesForProject = " + issueTypesForProject.inspect()
                def subTaskIssueTypesForProject = getSubTaskIssueTypesForProject(token, it.id)
                println "     subTaskIssueTypes = " + subTaskIssueTypesForProject.inspect()
            }

            def resolutions = getResolutions(token)
            println "resolutions = " + resolutions.inspect()

            def filters = getSavedFilters(token)
            println "savedFilters = " + filters.inspect()

            def statuses = getStatuses(token)
            println "statuses = " + statuses.inspect()

            def issues = getIssuesFromTextSearch(token, 'dragon')
            println "issues = " + issues.inspect()

            println "user = " + getUser(token, 'charlie').inspect()

            println "issueTypes = " + getIssueTypes(token).inspect()

            println "subTaskIssueTypes = " + getSubTaskIssueTypes(token).inspect()
            println logout(token)
        }
    }

    void testSampleData()
    {
        URL url = this.getClass().getResource("SampleData.groovy");
        File sampleData = new File(url.getFile());
        def binding = new Binding()
        def evaluate = new GroovyShell(binding).evaluate(sampleData)
        println evaluate
        println evaluate.getClass()
        evaluate.properties.each {println "   $it"}
        println binding.getVariables().inspect()
        def me = Eval.me(sampleData.text)
        println me
        println me.getClass()
        me.properties.each {println "   $it"}
    }
}
