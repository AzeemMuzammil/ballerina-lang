// Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
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

public int int\-a = 10;

client class Client {
    resource function get users\-all(string 'order = "asc") returns json {
        return {
            "users": [
                {
                    "name": "Mitchell",
                    "age": 30
                },
                {
                    "name": "Andrew",
                    "age": 35
                }
            ]
        };
    }
}

final Client clientEP = new;

function add(int a, int b) returns int {
    return a + b;
}
