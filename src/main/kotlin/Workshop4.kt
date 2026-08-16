package org.example

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val content: String,
    val isDone: Boolean,
)

@Serializable
data class TaskRequest(
    val content: String,
    val isDone: Boolean,
)

object TaskRepository {
    private val tasks = mutableListOf<Task>()

    @Synchronized
    fun getAll(): List<Task> = tasks.toList()

    @Synchronized
    fun getById(id: Int): Task? = tasks.find { task -> task.id == id }

    @Synchronized
    fun add(task: Task): Task {
        tasks.add(task)
        return task
    }

    @Synchronized
    fun update(id: Int, updatedTask: Task): Task? {
        val index = tasks.indexOfFirst { task -> task.id == id }
        if (index == -1) return null

        val taskWithPathId = updatedTask.copy(id = id)
        tasks[index] = taskWithPathId
        return taskWithPathId
    }

    @Synchronized
    fun delete(id: Int): Boolean = tasks.removeAll { task -> task.id == id }
}

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::taskModule)
        .start(wait = true)
}

fun Application.taskModule() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        taskRoutes()
    }
}

fun Route.taskRoutes() {
    route("/tasks") {
        get {
            call.respond(TaskRepository.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val task = id?.let(TaskRepository::getById)

            if (task == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(task)
            }
        }

        post {
            val task = call.receive<Task>()
            val addedTask = TaskRepository.add(task)

            call.respond(HttpStatusCode.Created, addedTask)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.NotFound)
            val updatedTask = call.receive<Task>()
            val savedTask = TaskRepository.update(id, updatedTask)
                ?: return@put call.respond(HttpStatusCode.NotFound)

            call.respond(HttpStatusCode.OK, savedTask)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.NotFound)

            TaskRepository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
