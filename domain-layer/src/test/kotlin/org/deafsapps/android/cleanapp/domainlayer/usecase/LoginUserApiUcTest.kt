package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginUserApiUcTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {

    }

    @Test
    fun run() {
    }


    /*
    private val view: FeatureContract.View<Student> = mock()
    private val repository: FeatureContract.Model<Student> by inject()
    private val presenter: FeatureContract.Presenter<Student> by inject { parametersOf(view) }

    @Before
    fun before() {
        startKoin(listOf(applicationModule))
        declareMock<FeatureContract.Model<Student>>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check that onSave2DbClick invokes a repository callback`() {

        val studentList = listOf(
            Student(0, "Pablo", true, 8),
            Student(1, "Irene", false, 10)
        )
        val dummyCallback = argumentCaptor<(String) -> Unit>()

        presenter.onSave2DbClick(studentList)
        Mockito.verify(repository).add2Db(data = eq(studentList), callback = dummyCallback.capture())
    }
    */

}