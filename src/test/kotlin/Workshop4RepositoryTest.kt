import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.example.Task
import org.example.TaskRepository
import org.example.TaskRequest

class Workshop4RepositoryTest {

    @BeforeTest
    fun clearRepository() {
        TaskRepository.getAll()
            .map(Task::id)
            .forEach(TaskRepository::delete)
    }

    @Test
    fun `task models store the supplied values`() {
        val task = Task(id = 1, content = "Write tests", isDone = false)
        val request = TaskRequest(content = "Write tests", isDone = false)

        assertEquals(1, task.id)
        assertEquals("Write tests", task.content)
        assertFalse(task.isDone)
        assertEquals("Write tests", request.content)
        assertFalse(request.isDone)
    }

    @Test
    fun `add stores a task and getAll returns it`() {
        val task = Task(id = 1, content = "Write tests", isDone = false)

        val addedTask = TaskRepository.add(task)

        assertEquals(task, addedTask)
        assertEquals(listOf(task), TaskRepository.getAll())
    }

    @Test
    fun `getById returns the matching task or null`() {
        val task = Task(id = 1, content = "Write tests", isDone = false)
        TaskRepository.add(task)

        assertEquals(task, TaskRepository.getById(1))
        assertNull(TaskRepository.getById(999))
    }

    @Test
    fun `update replaces an existing task and returns null for an unknown id`() {
        TaskRepository.add(Task(id = 1, content = "Draft", isDone = false))
        val updatedTask = Task(id = 2, content = "Tests pass", isDone = true)
        val expectedTask = updatedTask.copy(id = 1)

        val result = TaskRepository.update(1, updatedTask)

        assertEquals(expectedTask, result)
        assertEquals(expectedTask, TaskRepository.getById(1))
        assertNull(TaskRepository.getById(2))
        assertNull(TaskRepository.update(999, updatedTask))
    }

    @Test
    fun `delete removes an existing task and reports whether it was found`() {
        TaskRepository.add(Task(id = 1, content = "Write tests", isDone = false))

        assertTrue(TaskRepository.delete(1))
        assertNull(TaskRepository.getById(1))
        assertFalse(TaskRepository.delete(999))
    }

    @Test
    fun `repository handles concurrent additions`() = runBlocking {
        val taskCount = 1_000

        coroutineScope {
            repeat(taskCount) { id ->
                launch(Dispatchers.Default) {
                    TaskRepository.add(Task(id = id, content = "Task $id", isDone = false))
                }
            }
        }

        assertEquals(taskCount, TaskRepository.getAll().size)
    }
}
