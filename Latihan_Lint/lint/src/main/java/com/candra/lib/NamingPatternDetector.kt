package com.candra.lib

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

@Suppress("UnstableApiUsage")
class NamingPatternDetector: Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>>? =
        listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object: UElementHandler(){
            override fun visitClass(node: UClass) {
                if (node.name?.isDefinedCamelCase() == false){
                    /*
                    Nah, jika tidak memenuhi syarat maka Anda memanggil fungsi report untuk melaporkan issue tersebut.
                     */
                    context.report(
                        ISSUE_NAMING_PATTERN,node,
                        context.getNameLocation(node),
                        "Penulisan nama class harus menggunakan CamelCase"
                    )
                }
            }
        }
    }

    /*
    UastScanner adalah salah satu bagian dari Detector di dalam lint. UAST merupakan
    kepajangan dari Universal Abstract Syntax Tree yang berfungsi untuk mendeteksi file kotlin dan
    java sekaligus..
    Selain UastScanner, scanner lain yang dapat anda pakai yaitu:
    XmlScanner : Untuk file XML seperti layout dan drawable.
    ClassScanner : Untuk byte code
    BinaryResourceScanner : Untuk binary resources seperti image
    ResourceFolderScanner : Untuk resource folders seperti values, drawable, xml, dll.
    GradleScanner : Untuk file gradle seperti build.gradle atau settings.gradle
    OtherFileScanner : Untuk ProGuard, Text, YML etc.
     */

    /*
    Kemudian UElementhandler bertugas untuk melakukan pemeriksaan.
    Di sini Anda memanggil fungsi dengan nama isDefinedCamelCase yang memeriksa apakah sebuah fungsi memenuhi aturan CamelCase atau tidak.
    Caranya, dengan membandingkan huruf sebelum dan setelahnya. Jika keduanya huruf besar maka ia tidak memenuhi syarat.
     */
    private fun String.isDefinedCamelCase(): Boolean{
        val charArray = toCharArray()
        return charArray
            .mapIndexed { index, current ->
                current to charArray.getOrNull(index + 1)
            }
            .none {
                it.first.isUpperCase() && it.second?.isUpperCase() ?: false
            }
    }

    companion object{
        val ISSUE_NAMING_PATTERN: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "NamingPattern",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Penulisan nama class harus menggunakan CamelCase",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """
                Tulis nama class dengan menggunakan CamelCase
            """.trimIndent(),
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                NamingPatternDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}

/*
id : sebagai id untuk @SuppressLint warnings dsb

briefDescription : penjelasan singkat yang muncul pada dialog warning di IDE.

explanation : Penjelasan lengkap issue beserta penjelasan bagaimana mengatasinya; kamu bisa menggunakan format markdown seperti `monospace`, *italic*, and **bold**.

category : jenis kategori issue, seperti yang disebutkan pada teori.

priority : tingkat prioritas dari suatu issue, mulai dari 0 sampai 10.

severity : level kepentingan dari suatu issue, pilihannya yaitu IGNORE, INFORMATIONAL, WARNING, ERROR dan FATAL.

implementation : berisi nama class yang berperan sebagai detector dan scope file yang diperiksa.
 */