/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.ballerinalang.compiler.tree;

import org.ballerinalang.model.tree.IdentifierNode;
import org.ballerinalang.model.tree.NodeKind;
import org.ballerinalang.model.tree.TableKeySpecifierNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.3.0
 */
public class BLangTableKeySpecifier extends BLangNode implements TableKeySpecifierNode {

    // BLangNodes
    public List<IdentifierNode> fieldNameIdentifierList = new ArrayList<>();

    @Override
    public void addFieldNameIdentifier(IdentifierNode fieldNameIdentifier) {
        fieldNameIdentifierList.add(fieldNameIdentifier);
    }

    @Override
    public List<IdentifierNode> getFieldNameIdentifierList() {
        return this.fieldNameIdentifierList;
    }

    @Override
    public void accept(BLangNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> void accept(BLangNodeAnalyzer<T> analyzer, T props) {
        analyzer.visit(this, props);
    }

    @Override
    public <T, R> R apply(BLangNodeTransformer<T, R> modifier, T props) {
        return modifier.transform(this, props);
    }

    @Override
    public NodeKind getKind() {
        return NodeKind.TABLE_KEY_SPECIFIER;
    }

    @Override
    public String toString() {
        StringBuilder keyStringBuilder = new StringBuilder();
        for (IdentifierNode identifier : fieldNameIdentifierList) {
            if (!keyStringBuilder.toString().isEmpty()) {
                keyStringBuilder.append(", ");
            }
            keyStringBuilder.append(((BLangIdentifier) identifier).value);
        }

        return "key(" + keyStringBuilder.toString() + ")";
    }
}
