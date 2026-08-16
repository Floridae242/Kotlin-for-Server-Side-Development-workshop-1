import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.example.Task
import org.example.TaskRepository
import org.example.taskModule

class Workshop4RoutesTest {

    @BeforeTest
    fun clearRepository() {
        TaskRepository.getAll()
            .map(Task::id)
            .forEach(TaskRepository::delete)
    }

    @Test
    fun `GET tasks returns all tasks with status 200`() = testApplication {
        application { taskModule() }
        val jsonClient = jsonClient()
        val expectedTasks = listOf(
            Task(id = 1, content = "Write tests", isDone = false),
            Task(id = 2, content = "Ship workshop", isDone = true),
        )
        expectedTasks.forEach(TaskRepository::add)

        val response = jsonClient.get("/tasks")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedTasks, response.body<List<Task>>())
    }

    @Test
    fun `GET task by id returns the matching task with status 200`() = testApplication {
        application { taskModule() }
        val jsonClient = jsonClient()
        val task = Task(id = 1, content = "Write tests", isDone = false)
        TaskRepository.add(task)

        val response = jsonClient.get("/tasks/1")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(task, response.body<Task>())
    }

    @Test
    fun `GET task by id returns status 404 when task does not exist`() = testApplication {
        application { taskModule() }

        val response = client.get("/tasks/999")

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("", response.bodyAsText())
    }

    @Test
    fun `POST tasks adds a task and returns it with status 201`() = testApplication {
        application { taskModule() }
        val jsonClient = jsonClient()
        val task = Task(id = 1, content = "Write tests", isDone = false)

        val response = jsonClient.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(task)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals(task, response.body<Task>())
        assertEquals(task, TaskRepository.getById(1))
    }

    @Test
    fun `PUT task by id updates the task and returns it with status 200`() = testApplication {
        application { taskModule() }
        val jsonClient = jsonClient()
        TaskRepository.add(Task(id = 1, content = "Draft", isDone = false))
        val updatedTask = Task(id = 1, content = "Tests pass", isDone = true)

        val response = jsonClient.put("/tasks/1") {
            contentType(ContentType.Application.Json)
            setBody(updatedTask)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(updatedTask, response.body<Task>())
        assertEquals(updatedTask, TaskRepository.getById(1))
    }

    @Test
    fun `PUT task by id returns status 404 when task does not exist`() = testApplication {
        application { taskModule() }
        val jsonClient = jsonClient()
        val updatedTask = Task(id = 999, content = "Missing", isDone = true)

        val response = jsonClient.put("/tasks/999") {
            contentType(ContentType.Application.Json)
            setBody(updatedTask)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("", response.bodyAsText())
        assertNull(TaskRepository.getById(999))
    }

    @Test
    fun `PUT task by id keeps the path id when body id is different`() = testApplication {
        application { taskModule() }
        val jsonClient = jsonClient()
        TaskRepository.add(Task(id = 1, content = "Draft", isDone = false))
        val submittedTask = Task(id = 2, content = "Tests pass", isDone = true)
        val expectedTask = submittedTask.copy(id = 1)

        val response = jsonClient.put("/tasks/1") {
            contentType(ContentType.Application.Json)
            setBody(submittedTask)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedTask, response.body<Task>())
        assertEquals(expectedTask, TaskRepository.getById(1))
        assertNull(TaskRepository.getById(2))
    }

    @Test
    fun `DELETE task by id removes the task and returns status 204`() = testApplication {
        application { taskModule() }
        TaskRepository.add(Task(id = 1, content = "Write tests", isDone = false))

        val response = client.delete("/tasks/1")

        assertEquals(HttpStatusCode.NoContent, response.status)
        assertEquals("", response.bodyAsText())
        assertNull(TaskRepository.getById(1))
    }
}

private fun ApplicationTestBuilder.jsonClient(): HttpClient = createClient {
    install(ContentNegotiation) {
        json()
    }
}
