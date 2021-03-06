package com.sitewhere.wso2.identity.ws;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.1
 * Wed Aug 05 14:07:18 EDT 2015
 * Generated source version: 2.3.1
 * 
 */
 
@WebService(targetNamespace = "http://service.ws.um.carbon.wso2.org", name = "RemoteUserStoreManagerServicePortType")
@XmlSeeAlso({ObjectFactory.class})
public interface RemoteUserStoreManagerServicePortType {

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:isExistingRole", output = "urn:isExistingRoleResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:isExistingRoleRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "isExistingRole", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.IsExistingRole")
    @WebMethod(action = "urn:isExistingRole")
    @ResponseWrapper(localName = "isExistingRoleResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.IsExistingRoleResponse")
    public java.lang.Boolean isExistingRole(
        @WebParam(name = "roleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String roleName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:setUserClaimValues")
    @RequestWrapper(localName = "setUserClaimValues", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.SetUserClaimValues")
    @WebMethod(action = "urn:setUserClaimValues")
    public void setUserClaimValues(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claims", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<com.sitewhere.wso2.identity.ws.ClaimValue> claims,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getUserClaimValuesForClaims", output = "urn:getUserClaimValuesForClaimsResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getUserClaimValuesForClaimsRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getUserClaimValuesForClaims", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserClaimValuesForClaims")
    @WebMethod(action = "urn:getUserClaimValuesForClaims")
    @ResponseWrapper(localName = "getUserClaimValuesForClaimsResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserClaimValuesForClaimsResponse")
    public java.util.List<com.sitewhere.wso2.identity.ws.ClaimValue> getUserClaimValuesForClaims(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claims", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> claims,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:deleteUser")
    @RequestWrapper(localName = "deleteUser", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.DeleteUser")
    @WebMethod(action = "urn:deleteUser")
    public void deleteUser(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName
    );

    @Oneway
    @Action(input = "urn:updateRoleListOfUser")
    @RequestWrapper(localName = "updateRoleListOfUser", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.UpdateRoleListOfUser")
    @WebMethod(action = "urn:updateRoleListOfUser")
    public void updateRoleListOfUser(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "deletedRoles", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> deletedRoles,
        @WebParam(name = "newRoles", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> newRoles
    );

    @Oneway
    @Action(input = "urn:addRole")
    @RequestWrapper(localName = "addRole", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.AddRole")
    @WebMethod(action = "urn:addRole")
    public void addRole(
        @WebParam(name = "roleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String roleName,
        @WebParam(name = "userList", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> userList,
        @WebParam(name = "permissions", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<com.sitewhere.wso2.identity.ws.PermissionDTO> permissions
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:isExistingUser", output = "urn:isExistingUserResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:isExistingUserRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "isExistingUser", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.IsExistingUser")
    @WebMethod(action = "urn:isExistingUser")
    @ResponseWrapper(localName = "isExistingUserResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.IsExistingUserResponse")
    public java.lang.Boolean isExistingUser(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:addUser")
    @RequestWrapper(localName = "addUser", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.AddUser")
    @WebMethod(action = "urn:addUser")
    public void addUser(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "credential", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String credential,
        @WebParam(name = "roleList", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> roleList,
        @WebParam(name = "claims", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<com.sitewhere.wso2.identity.ws.ClaimValue> claims,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName,
        @WebParam(name = "requirePasswordChange", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.Boolean requirePasswordChange
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getAllProfileNames", output = "urn:getAllProfileNamesResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getAllProfileNamesRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getAllProfileNames", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetAllProfileNames")
    @WebMethod(action = "urn:getAllProfileNames")
    @ResponseWrapper(localName = "getAllProfileNamesResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetAllProfileNamesResponse")
    public java.util.List<java.lang.String> getAllProfileNames() throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getRoleNames", output = "urn:getRoleNamesResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getRoleNamesRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getRoleNames", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetRoleNames")
    @WebMethod(action = "urn:getRoleNames")
    @ResponseWrapper(localName = "getRoleNamesResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetRoleNamesResponse")
    public java.util.List<java.lang.String> getRoleNames() throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getUserList", output = "urn:getUserListResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getUserListRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getUserList", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserList")
    @WebMethod(action = "urn:getUserList")
    @ResponseWrapper(localName = "getUserListResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserListResponse")
    public java.util.List<java.lang.String> getUserList(
        @WebParam(name = "claimUri", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimUri,
        @WebParam(name = "claimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimValue,
        @WebParam(name = "profile", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profile
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:deleteUserClaimValue")
    @RequestWrapper(localName = "deleteUserClaimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.DeleteUserClaimValue")
    @WebMethod(action = "urn:deleteUserClaimValue")
    public void deleteUserClaimValue(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claimURI", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimURI,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getProfileNames", output = "urn:getProfileNamesResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getProfileNamesRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getProfileNames", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetProfileNames")
    @WebMethod(action = "urn:getProfileNames")
    @ResponseWrapper(localName = "getProfileNamesResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetProfileNamesResponse")
    public java.util.List<java.lang.String> getProfileNames(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:updateRoleName")
    @RequestWrapper(localName = "updateRoleName", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.UpdateRoleName")
    @WebMethod(action = "urn:updateRoleName")
    public void updateRoleName(
        @WebParam(name = "roleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String roleName,
        @WebParam(name = "newRoleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String newRoleName
    );

    @Oneway
    @Action(input = "urn:updateUserListOfRole")
    @RequestWrapper(localName = "updateUserListOfRole", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.UpdateUserListOfRole")
    @WebMethod(action = "urn:updateUserListOfRole")
    public void updateUserListOfRole(
        @WebParam(name = "roleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String roleName,
        @WebParam(name = "deletedUsers", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> deletedUsers,
        @WebParam(name = "newUsers", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> newUsers
    );

    @Oneway
    @Action(input = "urn:addUserClaimValue")
    @RequestWrapper(localName = "addUserClaimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.AddUserClaimValue")
    @WebMethod(action = "urn:addUserClaimValue")
    public void addUserClaimValue(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claimURI", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimURI,
        @WebParam(name = "claimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimValue,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getHybridRoles", output = "urn:getHybridRolesResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getHybridRolesRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getHybridRoles", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetHybridRoles")
    @WebMethod(action = "urn:getHybridRoles")
    @ResponseWrapper(localName = "getHybridRolesResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetHybridRolesResponse")
    public java.util.List<java.lang.String> getHybridRoles() throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:deleteUserClaimValues")
    @RequestWrapper(localName = "deleteUserClaimValues", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.DeleteUserClaimValues")
    @WebMethod(action = "urn:deleteUserClaimValues")
    public void deleteUserClaimValues(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claims", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<java.lang.String> claims,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:authenticate", output = "urn:authenticateResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:authenticateRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "authenticate", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.Authenticate")
    @WebMethod(action = "urn:authenticate")
    @ResponseWrapper(localName = "authenticateResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.AuthenticateResponse")
    public java.lang.Boolean authenticate(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "credential", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String credential
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getUserClaimValues", output = "urn:getUserClaimValuesResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getUserClaimValuesRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getUserClaimValues", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserClaimValues")
    @WebMethod(action = "urn:getUserClaimValues")
    @ResponseWrapper(localName = "getUserClaimValuesResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserClaimValuesResponse")
    public java.util.List<com.sitewhere.wso2.identity.ws.ClaimDTO> getUserClaimValues(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getUserId", output = "urn:getUserIdResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getUserIdRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getUserId", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserId")
    @WebMethod(action = "urn:getUserId")
    @ResponseWrapper(localName = "getUserIdResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserIdResponse")
    public java.lang.Integer getUserId(
        @WebParam(name = "username", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String username
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:deleteRole")
    @RequestWrapper(localName = "deleteRole", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.DeleteRole")
    @WebMethod(action = "urn:deleteRole")
    public void deleteRole(
        @WebParam(name = "roleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String roleName
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getUserListOfRole", output = "urn:getUserListOfRoleResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getUserListOfRoleRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getUserListOfRole", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserListOfRole")
    @WebMethod(action = "urn:getUserListOfRole")
    @ResponseWrapper(localName = "getUserListOfRoleResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserListOfRoleResponse")
    public java.util.List<java.lang.String> getUserListOfRole(
        @WebParam(name = "roleName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String roleName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getRoleListOfUser", output = "urn:getRoleListOfUserResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getRoleListOfUserRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getRoleListOfUser", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetRoleListOfUser")
    @WebMethod(action = "urn:getRoleListOfUser")
    @ResponseWrapper(localName = "getRoleListOfUserResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetRoleListOfUserResponse")
    public java.util.List<java.lang.String> getRoleListOfUser(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:addUserClaimValues")
    @RequestWrapper(localName = "addUserClaimValues", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.AddUserClaimValues")
    @WebMethod(action = "urn:addUserClaimValues")
    public void addUserClaimValues(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claims", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.util.List<com.sitewhere.wso2.identity.ws.ClaimValue> claims,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    );

    @Oneway
    @Action(input = "urn:updateCredential")
    @RequestWrapper(localName = "updateCredential", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.UpdateCredential")
    @WebMethod(action = "urn:updateCredential")
    public void updateCredential(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "newCredential", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String newCredential,
        @WebParam(name = "oldCredential", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String oldCredential
    );

    @Oneway
    @Action(input = "urn:setUserClaimValue")
    @RequestWrapper(localName = "setUserClaimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.SetUserClaimValue")
    @WebMethod(action = "urn:setUserClaimValue")
    public void setUserClaimValue(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claimURI", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimURI,
        @WebParam(name = "claimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claimValue,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getProperties", output = "urn:getPropertiesResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getPropertiesRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getProperties", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetProperties")
    @WebMethod(action = "urn:getProperties")
    @ResponseWrapper(localName = "getPropertiesResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetPropertiesResponse")
    public java.util.List<com.sitewhere.wso2.identity.ws.ArrayOfString> getProperties(
        @WebParam(name = "tenant", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        com.sitewhere.wso2.identity.ws.AX2604Tenant tenant
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:isReadOnly", output = "urn:isReadOnlyResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:isReadOnlyRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "isReadOnly", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.IsReadOnly")
    @WebMethod(action = "urn:isReadOnly")
    @ResponseWrapper(localName = "isReadOnlyResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.IsReadOnlyResponse")
    public java.lang.Boolean isReadOnly() throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getUserClaimValue", output = "urn:getUserClaimValueResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getUserClaimValueRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getUserClaimValue", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserClaimValue")
    @WebMethod(action = "urn:getUserClaimValue")
    @ResponseWrapper(localName = "getUserClaimValueResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetUserClaimValueResponse")
    public java.lang.String getUserClaimValue(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "claim", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String claim,
        @WebParam(name = "profileName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String profileName
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getTenantIdofUser", output = "urn:getTenantIdofUserResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getTenantIdofUserRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getTenantIdofUser", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetTenantIdofUser")
    @WebMethod(action = "urn:getTenantIdofUser")
    @ResponseWrapper(localName = "getTenantIdofUserResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetTenantIdofUserResponse")
    public java.lang.Integer getTenantIdofUser(
        @WebParam(name = "username", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String username
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:listUsers", output = "urn:listUsersResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:listUsersRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "listUsers", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.ListUsers")
    @WebMethod(action = "urn:listUsers")
    @ResponseWrapper(localName = "listUsersResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.ListUsersResponse")
    public java.util.List<java.lang.String> listUsers(
        @WebParam(name = "filter", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String filter,
        @WebParam(name = "maxItemLimit", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.Integer maxItemLimit
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getPasswordExpirationTime", output = "urn:getPasswordExpirationTimeResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getPasswordExpirationTimeRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getPasswordExpirationTime", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetPasswordExpirationTime")
    @WebMethod(action = "urn:getPasswordExpirationTime")
    @ResponseWrapper(localName = "getPasswordExpirationTimeResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetPasswordExpirationTimeResponse")
    public java.lang.Long getPasswordExpirationTime(
        @WebParam(name = "username", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String username
    ) throws RemoteUserStoreManagerServiceUserStoreException_Exception;

    @Oneway
    @Action(input = "urn:updateCredentialByAdmin")
    @RequestWrapper(localName = "updateCredentialByAdmin", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.UpdateCredentialByAdmin")
    @WebMethod(action = "urn:updateCredentialByAdmin")
    public void updateCredentialByAdmin(
        @WebParam(name = "userName", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String userName,
        @WebParam(name = "newCredential", targetNamespace = "http://service.ws.um.carbon.wso2.org")
        java.lang.String newCredential
    );

    @WebResult(name = "return", targetNamespace = "http://service.ws.um.carbon.wso2.org")
    @Action(input = "urn:getTenantId", output = "urn:getTenantIdResponse", fault = {@FaultAction(className = RemoteUserStoreManagerServiceUserStoreException_Exception.class, value = "urn:getTenantIdRemoteUserStoreManagerServiceUserStoreException")})
    @RequestWrapper(localName = "getTenantId", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetTenantId")
    @WebMethod(action = "urn:getTenantId")
    @ResponseWrapper(localName = "getTenantIdResponse", targetNamespace = "http://service.ws.um.carbon.wso2.org", className = "com.sitewhere.wso2.identity.ws.GetTenantIdResponse")
    public java.lang.Integer getTenantId() throws RemoteUserStoreManagerServiceUserStoreException_Exception;
}
