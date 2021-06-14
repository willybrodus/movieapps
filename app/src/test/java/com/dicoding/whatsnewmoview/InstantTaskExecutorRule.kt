package com.dicoding.whatsnewmoview

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.spekframework.spek2.dsl.Root

@ExperimentalCoroutinesApi
class InstantTaskExecutorRule(root: Root) {
    init {
        root.beforeGroup {
            ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
                override fun isMainThread(): Boolean = true
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
                override fun postToMainThread(runnable: Runnable) = runnable.run()
            })

        }

        root.afterGroup {
            ArchTaskExecutor.getInstance().setDelegate(null)
        }
    }
}
