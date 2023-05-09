# Java Packet Inspector (JPI)

A simple packet inspector written in Java utilising the [Pcap4J](https://kaitoy.github.io/pcap4j/javadoc/latest/en/) library. It helps understand the traffic on your network, and provide a simpler way of digesting information.

## Features

- Packet Sniffing
- Filter by:
  - IP Source and Destination
  - Port Source and Destination
  - Protocol
- Hex Dump
- Packet Buffering

## Usage

To run the command, you need OpenJDK 18 or above. You can download it [here](https://jdk.java.net/18/).

In the terminal containing the .jar file, run the following command:

```bash
java -jar PacketInspector.jar
```

Within the program, there will be a number of options before commencing packet sniffing.

### Network Device

To begin, there will be a list of valid network interfaces to capture traffic on. Select the number of the interface and press `enter`.

### Filtering

There will then be five options to filter by. If you do not wish to filter by an item, press `enter` to leave it open.

#### IP Source / Destination

Accepts input in CIDR block format. For example `192.168.4.0/24` will filter by all IP addresses in the range `192.168.4.0` to `192.168.4.255`.

#### Port Source / Destination

Accepts any number to match for input and output ports. Protocols that do not employ ports such as ICMP will not appear.

### Protocol

Accepts any protocol name. For example `TCP` or `UDP`. Matches layer three and and layer four protocols.

## Installation and Building

To build the project, you need Gradle installed. You can download it [here](https://gradle.org/install/). Once installed, run the following command in the root directory of the project:

```bash
gradle build
```

We reccommend using IntelliJ IDEA to run the project. You can download it [here](https://www.jetbrains.com/idea/download/). Once installed, open the project in IntelliJ and run the `main` method in `Main.java`. To build the project, navigate the Gradle menu and find Tasks > Build >  build. Running this will produce a .jar in `build/libs` which can then be executed.

## Technical Design

The program is design around the abstract `Packet` object. This object is the parent of all other packet types, which are then passed through functions to either gain more information about themselves, or to make decisions deciding output.

Packets are created in a packet factory, which takes in a decoded Ethernet frame header and directs the raw data to the appropriate constructor. This allows us to extensibly add new packet types in the future without interrupting the main flow of the program. Layer three and four packets inherit abstract methods from their respective classes, which handle common interactions those packets may have with the program. This forces consistent and predictable behaviour across all packet types, allowing us to treat them as abstract objects regardless of the specifics in the header design.

The program is designed to be as modular as possible, with each class having a single responsibility. This allows for easy extensibility and maintainability in the future.

### Limitations

Many new protocols, a good example being the QUIC protocol, are constructed within existing packet types, and are identified by the program as traditional TCP packets in this case. This will require specific workarounds and breaks in the clear modularity of the packet factor in future.
