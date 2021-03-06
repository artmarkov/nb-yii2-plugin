/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.nbphpcouncil.modules.php.yii2.validators;

import org.nbphpcouncil.modules.php.yii2.Yii2ProjectType;
import org.netbeans.modules.php.api.util.StringUtils;
import org.netbeans.modules.php.api.validation.ValidationResult;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 *
 * @author junichi11
 */
public final class Yii2CustomizerValidator {

    private final ValidationResult result = new ValidationResult();

    public Yii2CustomizerValidator() {
    }

    @NbBundle.Messages({
        "Yii2CustomizerValidator.invalid.project.type=Please select a project type."
    })
    public Yii2CustomizerValidator validateProjectType(Yii2ProjectType type) {
        if (type == null || type == Yii2ProjectType.YII2_NONE) {
            result.addWarning(new ValidationResult.Message("project.type", Bundle.Yii2CustomizerValidator_invalid_project_type())); // NOI18N
        }
        return this;
    }

    @NbBundle.Messages({
        "# {0} - name",
        "Yii2CustomizerValidator.notFound.directory=Existing relative path must be set from source directory ({0}).",
        "# {0} - name",
        "Yii2CustomizerValidator.invalid.directory=Project may be broken ({0}).",
        "Yii2CustomizerValidator.not.directory=Directory path must be set."

    })
    public Yii2CustomizerValidator validateBaseAppPath(FileObject sourceDirectory, String basePath) {
        if (sourceDirectory == null) {
            result.addWarning(new ValidationResult.Message("base.path", Bundle.Yii2CustomizerValidator_invalid_directory("Base path"))); // NOI18N
            return this;
        }

        if (StringUtils.isEmpty(basePath)) {
            return this;
        }

        FileObject fileObject = sourceDirectory.getFileObject(basePath);
        if (fileObject == null) {
            result.addWarning(new ValidationResult.Message("base.path", Bundle.Yii2CustomizerValidator_notFound_directory("Base path"))); // NOI18N
            return this;
        }

        if (!fileObject.isFolder()) {
            result.addWarning(new ValidationResult.Message("base.path", Bundle.Yii2CustomizerValidator_not_directory()));
            return this;
        }

        return this;
    }

    @NbBundle.Messages({
        "Yii2CustomizerValidator.not.file=File path must be set.",
        "Yii2CustomizerValidator.not.json=JSON file must be set."
    })
    public Yii2CustomizerValidator validatePathAliasesPath(FileObject sourceDirectory, String path) {
        if (StringUtils.isEmpty(path)) {
            return this;
        }

        if (sourceDirectory == null) {
            result.addWarning(new ValidationResult.Message("aliases.path", Bundle.Yii2CustomizerValidator_invalid_directory("Path aliases"))); // NOI18N
            return this;
        }

        FileObject fileObject = sourceDirectory.getFileObject(path);
        if (fileObject == null) {
            result.addWarning(new ValidationResult.Message("aliases.path", Bundle.Yii2CustomizerValidator_notFound_directory("Paht aliases"))); // NOI18N
            return this;
        }

        if (fileObject.isFolder()) {
            result.addWarning(new ValidationResult.Message("aliases.path", Bundle.Yii2CustomizerValidator_not_file()));
            return this;
        }

        String mimeType = fileObject.getMIMEType();
        if (!"text/x-json".equals(mimeType)) { // NOI18N
            result.addWarning(new ValidationResult.Message("aliases.path", Bundle.Yii2CustomizerValidator_not_json()));
            return this;
        }

        return this;
    }

    public ValidationResult getResult() {
        return result;
    }

}
