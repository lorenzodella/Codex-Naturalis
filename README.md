# Codex Naturalis — Final Project (Software Engineering, 2023/24)

<h1 align="center">
  <img src="/docs/logo.jpg" height=500 >
</h1>

Implementation of the physical board game "Codex Naturalis" adapted as a Java networked application.

**Final Grade:** **30 / 30**

## Documentation

[📖 Requirements](/docs/requirements.pdf)

[🌐 Official website](https://www.craniocreations.it/prodotto/codex-naturalis)

- **UML diagrams**: Initial and final UML diagrams are available in the Deliverables folder:
	- [UML (Initial)](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables/UML/Initial)
	- [UML (Final)](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables/UML/Final)
- **JavaDoc**: Generated JavaDoc is in the Deliverables/JavaDoc folder: [JavaDoc](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables/Javadoc)
- **Sequence diagrams**: Available here: [Sequence Diagrams](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables/SequenceDiagrams)
- **Peer reviews**: Two peer review reports are in: [PeerReview](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables/PeerReview)

## Features

Implemented features (Basic vs Advanced):

| Feature | Basic | Advanced |
|---|---:|:---:|
| Simplified rules | ✅ | |
| Full rules | ✅ | |
| Socket-based networking | ✅ | |
| RMI-based networking | ✅ | |
| Text UI (TUI) | ✅ | |
| Graphical UI (GUI) | ✅ | |
| Multiple simultaneous games | | ❌ |
| Persistence (save/load) | | ❌ |
| Resilience to disconnections | | ✅ |
| In-game chat | | ✅ |

## Build

The project is built with Maven. An assembled jar (using the Maven Assembly Plugin) can be produced with:

```bash
mvn clean compile package
```

Prebuilt jars are available in Deliverables/JAR.

## Run

You must start the server before launching any clients.

### Server

Run the server jar and provide two arguments: the RMI port and the socket (SKT) port. The project uses `12345` for RMI and `12346` for sockets by default. Example:

```bash
java -jar ./AM20-server.jar 12345 12346
```

### Client

The client accepts four arguments (or you can run it without arguments and use the interactive prompts):

1. UI choice: `1` = TUI, `2` = GUI
2. Connection type: `1` = RMI, `2` = Socket
3. Server IP address
4. Server port (RMI: 12345, Socket: 12346)

Example (GUI + RMI):

```bash
java -jar ./AM20-client.jar 2 1 <server-ip> 12345
```

If you launch the client without parameters, it will prompt you interactively for the above choices.

### Chat usage (TUI)

In the TUI, chat messages must be wrapped in double quotes. Example command:

```text
/chat <destination> "message"
```

## Notes

- Default ports used by the project: RMI = `12345`, Socket = `12346`.
- The assembled jars are placed in `Deliverables/JAR`.

## Team

- Lorenzo Della Matera — https://github.com/lorenzodella
- Mattia Doro — https://github.com/mado002
- Eleonora Ficarelli — https://github.com/EleonoraFicarelli
- Irene Ferrente — https://github.com/Ireneeer

## Disclaimer

Codex Naturalis is a board game developed and published by Cranio Creations Srl. The graphic contents of this project attributable to the editorial board product are used with the prior approval of Cranio Creations Srl for educational purposes only. The distribution, copying or reproduction of the contents and images in any form outside the project is prohibited, as is the redistribution and publication of the contents and images for purposes other than those mentioned above. Commercial use of the aforementioned contents is also prohibited.
