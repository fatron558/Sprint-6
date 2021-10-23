import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param socket Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {

            while (true) {
                socket.accept().use {
                    handle(it, fs)
                }
            }
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {
        socket.getInputStream().use { inputStream ->
            inputStream.bufferedReader().use { reader ->
                val vPath = VPath(reader.readLine().trim().split(" ")[1])
                val file = fs.readFile(vPath)
                val response = if (file == null) {
                    "HTTP/1.0 404 Not Found\r\n" +
                            "Server: FileServer"
                } else {
                    "HTTP/1.0 200 OK\r\n" +
                            "Server: FileServer\r\n" +
                            "\r\n$file"
                }
                PrintWriter(socket.getOutputStream()).use { writer ->
                    writer.println(response)
                    writer.flush()
                }
            }
        }
    }
}