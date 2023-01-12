package com.candra.lib

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

@Suppress("UnstableApiUsage")
class MyIssueRegistry : IssueRegistry(){
    override val issues: List<Issue>
        get() = listOf(NamingPatternDetector.ISSUE_NAMING_PATTERN)

    override val api: Int = CURRENT_API
}
/*
Nah, kelas ini berfungsi untuk mendefinisikan issue apa saja yang akan diperiksa dengan
library ini dan memperkenalkannya ke mesin Android Lint.

 Di sini juga Anda menggunakan anotasi @Surpress. Mengapa? Hal ini karena Lint API masih belum stabil, sehingga Anda perlu menandainya supaya tidak muncul warning di kelas ini.

 Lalu untuk apa variabel API, ini digunakan untuk menghindari message “Obsolete custom lint check” pada report hasil lintDebug.

 Namun kelas Registry saja tidak cukup, Anda masih harus meng-expose-nya. Karena itulah terdapat kode berikut pada build.gradle (module: lint).

 Sehingga Anda dapat memakainya di module manapun dengan hanya menambahkan kode berikut pada masing-masing module.

lintChecks project(":lint")
 */