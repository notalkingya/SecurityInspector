package org.skgroup.securityinspector.rules.rce

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiMethodCallExpression
import org.skgroup.securityinspector.inspectors.BaseLocalInspectionTool
import org.skgroup.securityinspector.utils.InspectionBundle
import org.skgroup.securityinspector.utils.SecExpressionUtils

class SnakeYamlUnserialize : BaseLocalInspectionTool() {

    companion object {
        private val MESSAGE = InspectionBundle.message("vuln.massage.SnakeYamlUnserialize")

        private val SNAKEYAML_METHOD_SINKS = mapOf(
            "org.yaml.snakeyaml.Yaml" to listOf("load", "loadAll", "parse", "loadAs")
        )
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {
            override fun visitMethodCallExpression(expression: PsiMethodCallExpression) {
                if (SecExpressionUtils.isMethodSink(expression, SNAKEYAML_METHOD_SINKS)) {


                    holder.registerProblem(
                        expression,
                        MESSAGE,
                        ProblemHighlightType.GENERIC_ERROR_OR_WARNING
                    )
                }
            }
        }
    }
}
