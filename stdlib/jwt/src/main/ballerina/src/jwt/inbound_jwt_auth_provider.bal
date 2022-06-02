// Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/auth;
import ballerina/http;
import ballerina/stringutils;

# Represents the inbound JWT auth provider, which authenticates by validating a JWT.
# The `jwt:InboundJwtAuthProvider` is another implementation of the `auth:InboundAuthProvider` interface.
# ```ballerina
# jwt:InboundJwtAuthProvider inboundJwtAuthProvider = new({
#     issuer: "example",
#     audience: "ballerina",
#     trustStoreConfig: {
#         certificateAlias: "ballerina",
#         trustStore: {
#             path: "/path/to/truststore.p12",
#             password: "ballerina"
#         }
#     }
# });
# ```
#
# + jwtValidatorConfig - JWT validator configurations
public type InboundJwtAuthProvider object {

    *auth:InboundAuthProvider;

    public JwtValidatorConfig jwtValidatorConfig;
    http:Client? jwksClient;

    # Provides authentication based on the provided JWT.
    #
    # + jwtValidatorConfig - JWT validator configurations
    public function __init(JwtValidatorConfig jwtValidatorConfig) {
        self.jwtValidatorConfig = jwtValidatorConfig;
        JwksConfig? jwksConfig = self.jwtValidatorConfig?.jwksConfig;
        if (jwksConfig is JwksConfig) {
            self.jwksClient = new(jwksConfig.url, jwksConfig.clientConfig);
        } else {
            self.jwksClient = ();
        }
    }

# Authenticates provided JWT against `jwt:JwtValidatorConfig`.
#```ballerina
# boolean|auth:Error result = inboundJwtAuthProvider.authenticate("<credential>");
# ```
#
# + credential - JWT to be authenticated
# + return - `true` if authentication is successful, `false` otherwise or else an `auth:Error` if JWT validation failed
    public function authenticate(string credential) returns @tainted (boolean|auth:Error) {
        string[] jwtComponents = stringutils:split(credential, "\\.");
        if (jwtComponents.length() != 3) {
            return false;
        }

        JwtPayload|Error validationResult = validate(credential, self.jwtValidatorConfig, self.jwksClient);
        if (validationResult is JwtPayload) {
            auth:setAuthenticationContext("jwt", credential);
            setPrincipal(validationResult);
            return true;
        } else {
            return prepareAuthError("JWT validation failed.", validationResult);
        }
    }
};

function setPrincipal(JwtPayload jwtPayload) {
    string? iss = jwtPayload?.iss;
    string? sub = jwtPayload?.sub;
    string userId = (iss is () ? "" : iss) + ":" + (sub is () ? "" : sub);
    // By default set sub as username.
    string username = (sub is () ? "" : sub);
    auth:setPrincipal(userId, username);
    map<json>? claims = jwtPayload?.customClaims;
    if (claims is map<json>) {
        auth:setPrincipal(claims = claims);
        if (claims.hasKey("scope")) {
            json scopeString = claims["scope"];
            if (scopeString is string && scopeString != "") {
                auth:setPrincipal(scopes = stringutils:split(scopeString, " "));
            }
        } else if (claims.hasKey("scp")) {
            json scopeString = claims["scp"];
            if (scopeString is json[] && scopeString.length() > 0) {
                string[]|error scopes = string[].constructFrom(scopeString);
                if (scopes is string[]) {
                    auth:setPrincipal(scopes = scopes);
                }
            }
        }
    }
}