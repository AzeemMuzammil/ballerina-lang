/*
 *  Copyright (c) 2020, WSO2 LLC. (http://www.wso2.com).
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.ballerina.compiler.internal.parser.tree;

import io.ballerina.compiler.syntax.tree.BuiltinSimpleNameReferenceNode;
import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;

import java.util.Collection;
import java.util.Collections;

/**
 * This is a generated internal syntax tree node.
 *
 * @since 2.0.0
 */
public class STBuiltinSimpleNameReferenceNode extends STNameReferenceNode {
    public final STNode name;

    STBuiltinSimpleNameReferenceNode(
            SyntaxKind kind,
            STNode name) {
        this(
                kind,
                name,
                Collections.emptyList());
    }

    STBuiltinSimpleNameReferenceNode(
            SyntaxKind kind,
            STNode name,
            Collection<STNodeDiagnostic> diagnostics) {
        super(kind, diagnostics);
        this.name = name;

        addChildren(
                name);
    }

    @Override
    public STNode modifyWith(Collection<STNodeDiagnostic> diagnostics) {
        return new STBuiltinSimpleNameReferenceNode(
                this.kind,
                this.name,
                diagnostics);
    }

    public STBuiltinSimpleNameReferenceNode modify(
            SyntaxKind kind,
            STNode name) {
        if (checkForReferenceEquality(
                name)) {
            return this;
        }

        return new STBuiltinSimpleNameReferenceNode(
                kind,
                name,
                diagnostics);
    }

    @Override
    public Node createFacade(int position, NonTerminalNode parent) {
        return new BuiltinSimpleNameReferenceNode(this, position, parent);
    }

    @Override
    public void accept(STNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T apply(STNodeTransformer<T> transformer) {
        return transformer.transform(this);
    }
}
