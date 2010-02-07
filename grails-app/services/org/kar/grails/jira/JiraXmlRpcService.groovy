package org.kar.grails.jira

/**
 * Provides a client implementation for the Jira XmlRpcService
 * http://docs.atlassian.com/software/jira/docs/api/rpc-jira-plugin/latest/index.html?com/atlassian/jira/rpc/xmlrpc/XmlRpcService.html
 * @author Kelly Robinson
 */
class JiraXmlRpcService
{
    boolean transactional = false

    def rpcProxyService

    def static final String XMLRPC = 'rpc/xmlrpc'

    String login(String hostname, String username, String password)
    {
        return createProxy(hostname).jira1.login(username, password);
    }

    boolean logout(String hostname, String token)
    {
        return createProxy(hostname).jira1.logout(token);
    }

    Map getServerInfo(String hostname, String token)
    {
        return createProxy(hostname).jira1.getServerInfo(token)
    }

    List getProjectsNoSchemes(String hostname, String token)
    {
        def schemes = createProxy(hostname).jira1.getProjectsNoSchemes(token)
        return schemes instanceof List ? schemes : []
    }

    List getVersions(String hostname, String token, String projectKey)
    {
        def versions = createProxy(hostname).jira1.getVersions(token, projectKey)
        return versions instanceof List ? versions : []
    }

    List getComponents(String hostname, String token, String projectKey)
    {
        def components = createProxy(hostname).jira1.getComponents(token, projectKey)
        return components instanceof List ? components : []
    }

    List getIssueTypesForProject(String hostname, String token, String projectId)
    {
        def issueTypesForProject = createProxy(hostname).jira1.getIssueTypesForProject(token, projectId)
        return issueTypesForProject instanceof List ? issueTypesForProject : []
    }

    List getSubTaskIssueTypesForProject(String hostname, String token, String projectId)
    {
        def subTaskIssuesForProject = createProxy(hostname).jira1.getSubTaskIssueTypesForProject(token, projectId)
        return subTaskIssuesForProject instanceof List ? subTaskIssuesForProject : []
    }

    List getIssueTypes(String hostname, String token)
    {
        def types = createProxy(hostname).jira1.getIssueTypes(token)
        return types instanceof List ? types : []
    }

    List getSubTaskIssueTypes(String hostname, String token)
    {
        def types = createProxy(hostname).jira1.getSubTaskIssueTypes(token)
        return types instanceof List ? types: []
    }

    List getPriorities(String hostname, String token)
    {
        return createProxy(hostname).jira1.getPriorities(token)
    }

    List getStatuses(String hostname, String token)
    {
        return createProxy(hostname).jira1.getStatuses(token)
    }

    List getResolutions(String hostname, String token)
    {
        return createProxy(hostname).jira1.getResolutions(token)
    }

    Map getUser(String hostname, String token, String username)
    {
        def user = createProxy(hostname).jira1.getUser(token, username)
        return user instanceof Map ? user : [:]
    }

    List getSavedFilters(String hostname, String token)
    {
        def filters = createProxy(hostname).jira1.getSavedFilters(token)
        return filters instanceof List ? filters : []
    }

    List getFavouriteFilters(String hostname, String token)
    {
        def filters = createProxy(hostname).jira1.getFavouriteFilters(token)
        return filters instanceof List ? filters : []
    }

    Map getIssue(String hostname, String token, String issueKey)
    {
        def issue = createProxy(hostname).jira1.getIssue(token, issueKey)
        return issue instanceof Map ? issue : []
    }

    Map createIssue(String hostname, String token, Map rIssueStruct)
    {
        def issue = createProxy(hostname).jira1.createIssue(token, rIssueStruct)
        return issue instanceof Map ? issue : []
    }

    Map updateIssue(String hostname, String token, String issueKey, Map fieldValues)
    {
        def issue = createProxy(hostname).jira1.updateIssue(token, issueKey, fieldValues)
        return issue instanceof Map ? issue : []
    }

    boolean addComment(String hostname, String token, String issueKey, String comment)
    {
        return createProxy(hostname).jira1.addComment(token, issueKey, comment)
    }

    List getIssuesFromFilter(String hostname, String token, String filterId)
    {
        def issues = createProxy(hostname).jira1.getIssuesFromFilter(token, filterId)
        return issues instanceof List ? issues : []
    }

    List getIssuesFromTextSearch(String hostname, String token, String searchTerms)
    {
        def search = createProxy(hostname).jira1.getIssuesFromTextSearch(token, searchTerms)
        return search instanceof List ? search : []
    }

    List getIssuesFromTextSearchWithProject(String hostname, String token, List projectKeys, String searchTerms, int maxNumResults)
    {
        def issues = createProxy(hostname).jira1.getIssuesFromTextSearchWithProject(token, projectKeys, searchTerms, maxNumResults)
        return issues instanceof List ? issues : []
    }

    List getComments(String hostname, String token, String issueKey)
    {
        def comments = createProxy(hostname).jira1.getComments(token, issueKey)
        return comments instanceof List ? comments : []
    }

    def createHelper(hostname)
    {
        return new JiraXmlRpcServiceHelper(hostname:hostname, service:this)
    }
    
    private createProxy(hostname)
    {
        return rpcProxyService.createProxy(hostname)
    }
}
